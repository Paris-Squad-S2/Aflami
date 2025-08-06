package com.repository.media.repository

import com.paris_2.domain.media.exception.AddMediaToContinueWatchingException
import com.paris_2.domain.media.exception.NoInternetConnectionException
import com.paris_2.domain.media.exception.GetContinueWatchingMediaException
import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.entity.MediaType
import com.google.common.truth.Truth.assertThat
import com.repository.media.datasource.local.ContinueWatchingLocalDataSource
import com.repository.media.datasource.local.HomeMediaLocalDataSource
import com.repository.media.datasource.remote.MediaRemoteDataSource
import com.repository.media.dto.home.MovieDto
import com.repository.media.dto.home.TvDto
import com.repository.media.entity.Category
import com.repository.media.entity.HomeMediaEntity
import com.repository.media.entity.MediaEntity
import com.repository.media.entity.MediaTypeEntity
import com.repository.media.mapper.toEntity
import com.repository.media.util.NetworkConnectionChecker
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
    private val local: ContinueWatchingLocalDataSource = mockk(relaxed = true)

    private val homeLocal: HomeMediaLocalDataSource = mockk(relaxed = true)
    private val networkChecker: NetworkConnectionChecker = mockk()
    private lateinit var repo: MediaRepositoryImpl

    @BeforeEach
    fun setUp() {
        every { networkChecker.isConnected } returns MutableStateFlow(true)
        repo = MediaRepositoryImpl(
            networkChecker, remote,
            continueWatchingLocalDataSource = local,
            homeMediaLocalDataSource = homeLocal
        )
    }

    @Test
    fun `getPopularMedia returns local data if available`() = runTest {
        val localMedia = listOf(
            HomeMediaEntity(
                id = 1,
                title = "Local Popular",
                voteAverage = 7.2,
                posterPath = "poster.jpg",
                releaseDate = "2023-01-01",
                genreIds = listOf(1, 2),
                type = MediaTypeEntity.MOVIE,
                category = Category.POPULAR
            )
        )
        coEvery { homeLocal.getMediaListByCategory(Category.POPULAR) } returns localMedia

        val result = repo.getPopularMedia()

        assertThat(result).hasSize(1)

        coVerify(exactly = 0) { remote.getPopularMovies(any()) }
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
        assertThat(result.map { it.rating }).isEqualTo(listOf(9.0, 8.0, 7.0))
    }

    @Test
    fun `getTopRatingMedia returns local data if available`() = runTest {
        val localMedia = listOf(
            HomeMediaEntity(
                id = 2,
                title = "Top Rated Local",
                voteAverage = 9.0,
                posterPath = "top.jpg",
                releaseDate = "2022-01-01",
                genreIds = listOf(3),
                type = MediaTypeEntity.TV_SHOW,
                category = Category.POPULAR
            )
        )
        coEvery { homeLocal.getMediaListByCategory(Category.TOP_RATED) } returns localMedia

        val result = repo.getTopRatingMedia()

        assertThat(result).hasSize(1)
        coVerify(exactly = 0) { remote.getTopRatedMovies(any()) }
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
        assertThat(result.first().rating).isEqualTo(9.5)
    }

    @Test
    fun `getUpComingMedia returns local data if available`() = runTest {
        val localMedia = listOf(
            HomeMediaEntity(
                id = 3,
                title = "Upcoming Local",
                voteAverage = 8.1,
                posterPath = "upcoming.jpg",
                releaseDate = "2025-01-01",
                genreIds = listOf(5),
                type = MediaTypeEntity.MOVIE,
                category = Category.POPULAR
            )
        )
        coEvery { homeLocal.getMediaListByCategory(Category.UPCOMING) } returns localMedia

        val result = repo.getUpComingMedia()

        assertThat(result).hasSize(1)
        coVerify(exactly = 0) { remote.getUpcomingMovies(any()) }
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
        val media = Media(200, "", "Local", MediaType.MOVIE,  listOf(1), mockk(), 8.0)
        coEvery { local.addMedia(media.toEntity()) } returns Unit
        repo.addMediaToContinueWatching(media)
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
        val result = repo.getContinueWatchingMedia()
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
    fun `addMediaToLocal throws addMediaToLocalException on failure`() = runTest {
        val media = Media(123, "", "Fail", MediaType.TVSHOW, listOf(1), mockk(), 4.0)

        coEvery { local.addMedia(any()) } throws RuntimeException("DB insert failed")

        assertThrows<AddMediaToContinueWatchingException> {
            repo.addMediaToContinueWatching(media)
        }
    }

    @Test
    fun `getMediaFromLocal throws catchMediaFromLocalException on failure`() = runTest {
        coEvery { local.getAllMedia() } throws RuntimeException("DB read failed")

        assertThrows<GetContinueWatchingMediaException> {
            repo.getContinueWatchingMedia()
        }
    }
}