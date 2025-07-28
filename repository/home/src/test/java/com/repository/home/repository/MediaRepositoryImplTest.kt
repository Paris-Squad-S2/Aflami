package com.repository.home.repository

import com.domain.home.exception.NoInternetConnectionException
import com.domain.home.exception.GetContinueWatchingMediaException
import com.domain.home.model.Media
import com.domain.home.model.MediaType
import com.google.common.truth.Truth.assertThat
import com.repository.home.datasource.local.HomeMediaLocalDataSource
import com.repository.home.datasource.remote.MediaRemoteDataSource
import com.repository.home.dto.MovieDto
import com.repository.home.dto.TvDto
import com.repository.home.entity.MediaEntity
import com.repository.home.entity.MediaTypeEntity
import com.repository.home.mapper.toEntity
import com.repository.home.util.NetworkConnectionChecker
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MediaRepositoryImplTest {
    private val remote: MediaRemoteDataSource = mockk()
    private val local: HomeMediaLocalDataSource = mockk(relaxed = true)
    private val networkChecker: NetworkConnectionChecker = mockk()
    private lateinit var repo: MediaRepositoryImpl

    @BeforeEach
    fun setUp() {
        every { networkChecker.isConnected } returns MutableStateFlow(true)
        repo = MediaRepositoryImpl(networkChecker, remote, local)
    }

    @Test
    fun `getPopularMedia aggregates and sorts by voteAverage descending`() = runTest {
        val movie1 = MovieDto(
            id = 1,
            title = "A",
            releaseDate = "2023-01-01",
            voteAverage = 7.0,
            genreIds = listOf(28),
            overview = "",
            posterPath = ""
        )
        val movie2 = MovieDto(
            id = 2,
            title = "B",
            releaseDate = "2023-02-02",
            voteAverage = 9.0,
            genreIds = listOf(35),
            overview = "",
            posterPath = ""
        )
        val tv1 = TvDto(
            id = 3,
            name = "C",
            firstAirDate = "2023-03-03",
            voteAverage = 8.0,
            genreIds = listOf(18),
            overview = "",
            posterPath = ""
        )
        coEvery { remote.getPopularMovies(any()).results } returns listOf(movie1, movie2)
        coEvery { remote.getPopularTvShows(any()).results } returns listOf(tv1)
        val result = repo.getPopularMedia()
        assertThat(result.map { it.voteAverage }).isEqualTo(listOf(9.0, 8.0, 7.0))
    }

    @Test
    fun `getTopRatingMedia aggregates and sorts by voteAverage descending`() = runTest {
        val movie = MovieDto(
            id = 10,
            title = "Top M",
            releaseDate = "2020-04-04",
            voteAverage = 9.5,
            genreIds = listOf(12),
            overview = "",
            posterPath = ""
        )
        val tv = TvDto(
            id = 20,
            name = "Top TV",
            firstAirDate = "2020-05-05",
            voteAverage = 8.5,
            genreIds = listOf(18),
            overview = "",
            posterPath = ""
        )
        coEvery { remote.getTopRatedMovies(any()).results } returns listOf(movie)
        coEvery { remote.getTopRatedTvShows(any()).results } returns listOf(tv)
        val result = repo.getTopRatingMedia()
        assertThat(result.first().voteAverage).isEqualTo(9.5)
        assertThat(result.last().voteAverage).isEqualTo(8.5)
    }

    @Test
    fun `getUpComingMedia returns only movies marked as upcoming`() = runTest {
        val movie = MovieDto(
            id = 100,
            title = "X",
            releaseDate = "2024-06-06",
            voteAverage = 6.7,
            genreIds = listOf(28),
            overview = "",
            posterPath = ""
        )
        coEvery { remote.getUpcomingMovies(any()).results } returns listOf(movie)
        val result = repo.getUpComingMedia()
        assertThat(result.single().id).isEqualTo(100)
    }

    @Test
    fun `getNowPlayingMedia returns only now playing movies`() = runTest {
        val movie = MovieDto(
            id = 105,
            title = "Y",
            releaseDate = "2024-11-12",
            voteAverage = 7.3,
            genreIds = listOf(12),
            overview = "",
            posterPath = ""
        )
        coEvery { remote.getNowPlayingMovies().results } returns listOf(movie)
        val result = repo.getNowPlayingMedia()
        assertThat(result.first().id).isEqualTo(105)
    }

    @Test
    fun `addMediaToLocal delegates to data source`() = runTest {
        val media = Media(200, "Local", 8.0, "", mockk(), listOf(1), MediaType.MOVIE)
        coEvery { local.addMedia(media.toEntity()) } returns Unit
        repo.addMediaToCountineWatch(media)
        coVerify { local.addMedia(media.toEntity()) }
    }

    @Test
    fun `getMediaFromLocal maps entities to domain`() = runTest {
        val entity = MediaEntity(
            id = 300,
            title = "Saved",
            posterPath = "saved.jpg",
            type = MediaTypeEntity.TV_SHOW,
            genreIds = listOf(2),
            voteAverage = 6.6,
            releaseDate = "2023-09-09"
        )
        coEvery { local.getAllMedia() } returns listOf(entity)
        val result = repo.getMediaFromLocal()
        assertThat(result.single().id).isEqualTo(300)
    }

    @Test
    fun `getPopularMedia throws NoInternetConnectionException when offline`() = runTest {
        every { networkChecker.isConnected } returns MutableStateFlow(false)

        assertThrows<NoInternetConnectionException> {
            repo.getPopularMedia()
        }
    }


    @Test
    fun `getMediaFromLocal throws catchMediaFromLocalException on failure`() = runTest {
        coEvery { local.getAllMedia() } throws RuntimeException("DB read failed")

        assertThrows<GetContinueWatchingMediaException> {
            repo.getMediaFromLocal()
        }
    }
}