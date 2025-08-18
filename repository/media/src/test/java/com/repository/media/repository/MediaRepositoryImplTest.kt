package com.repository.media.repository

import com.google.common.truth.Truth.assertThat
import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.entity.MediaType
import com.paris_2.domain.media.exception.FailedException
import com.paris_2.domain.media.exception.NoInternetConnectionException
import com.paris_2.repository.user.dataSource.local.SettingLocalDataSource
import com.repository.media.datasource.local.ContinueWatchingLocalDataSource
import com.repository.media.datasource.local.HomeMediaLocalDataSource
import com.repository.media.datasource.remote.MediaRemoteDataSource
import com.repository.media.dto.category.MovieByCategoryDto
import com.repository.media.dto.category.ResultDto
import com.repository.media.dto.category.TvResultDto
import com.repository.media.dto.category.TvShowByCategoryDto
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
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MediaRepositoryImplTest {
    private val remote: MediaRemoteDataSource = mockk()
    private val local: ContinueWatchingLocalDataSource = mockk(relaxed = true)

    private val homeLocal: HomeMediaLocalDataSource = mockk(relaxed = true)
    private val networkChecker: NetworkConnectionChecker = mockk()
    private val settingLocalDataSource: SettingLocalDataSource = mockk()
    private lateinit var repo: MediaRepositoryImpl

    val language = "en"

    @BeforeEach
    fun setUp() {
        every { networkChecker.isConnected } returns MutableStateFlow(true)
        repo = MediaRepositoryImpl(
            networkChecker, remote,
            continueWatchingLocalDataSource = local,
            homeMediaLocalDataSource = homeLocal,
            settingLocalDataSource = settingLocalDataSource
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
                type = MediaTypeEntity.Movie,
                category = Category.POPULAR,
                language = "en"
            )
        )
        coEvery { homeLocal.getHomeMediaByCategory(Category.POPULAR, language) } returns localMedia
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow(language)
        val result = repo.getPopularMedia()

        assertThat(result).hasSize(1)

        coVerify(exactly = 0) { remote.getPopularMovies(any()) }
    }

    @Test
    fun `getPopularMedia fetches remote data when local is empty`() = runTest {

        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery { homeLocal.getHomeMediaByCategory(Category.POPULAR, "en") } returns emptyList()

        val movie = MovieDto(
            id = 1, title = "Remote Movie", releaseDate = "2023-01-01",
            voteAverage = 8.0, genreIds = listOf(28), overview = "", posterPath = ""
        )
        val tv = TvDto(
            id = 2, name = "Remote TV", firstAirDate = "2023-02-01",
            voteAverage = 7.5, genreIds = listOf(18), overview = "", posterPath = ""
        )

        coEvery { remote.getPopularMovies(any()).results } returns listOf(movie)
        coEvery { remote.getPopularTvShows(any()).results } returns listOf(tv)

        val result = repo.getPopularMedia()

        assertThat(result).hasSize(2)
        assertThat(result.first().rating).isEqualTo(8.0)
        coVerify { homeLocal.addHomeMedia(any()) }
    }

    @Test
    fun `getPopularMedia handles partial remote data`() = runTest {
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery { homeLocal.getHomeMediaByCategory(Category.POPULAR, "en") } returns emptyList()

        val movie = MovieDto(
            id = 1, title = "Movie", releaseDate = "2023-01-01",
            voteAverage = 8.0, genreIds = listOf(28), overview = "", posterPath = ""
        )

        coEvery { remote.getPopularMovies(any()).results } returns listOf(movie)
        coEvery { remote.getPopularTvShows(any()).results } returns null

        val result = repo.getPopularMedia()

        assertThat(result).hasSize(1)
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
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow(language)
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
                type = MediaTypeEntity.TvShow,
                category = Category.POPULAR,
                language = "en"
            )
        )
        coEvery {
            homeLocal.getHomeMediaByCategory(
                Category.TOP_RATED,
                language
            )
        } returns localMedia
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow(language)

        val result = repo.getTopRatingMedia()

        assertThat(result).hasSize(1)
        coVerify(exactly = 0) { remote.getTopRatedMovies(any()) }
    }

    @Test
    fun `getTopRatingMedia fetches remote data when local is empty`() = runTest {
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery { homeLocal.getHomeMediaByCategory(Category.TOP_RATED, "en") } returns emptyList()

        val movie = MovieDto(
            id = 3, title = "Top Movie", releaseDate = "2022-01-01",
            voteAverage = 9.2, genreIds = listOf(18), overview = "", posterPath = ""
        )

        coEvery { remote.getTopRatedMovies(any()).results } returns listOf(movie)
        coEvery { remote.getTopRatedTvShows(any()).results } returns emptyList()

        val result = repo.getTopRatingMedia()

        assertThat(result).hasSize(1)
        assertThat(result.first().rating).isEqualTo(9.2)
        coVerify { homeLocal.addHomeMedia(any()) }
    }

    @Test
    fun `getTopRatingMedia throws NoInternetConnectionException when offline`() = runTest {
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        every { networkChecker.isConnected } returns MutableStateFlow(false)

        assertThrows<NoInternetConnectionException> {
            repo.getTopRatingMedia()
        }
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
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow(language)
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
                type = MediaTypeEntity.Movie,
                category = Category.POPULAR,
                language = "en"
            )
        )
        coEvery { homeLocal.getHomeMediaByCategory(Category.UPCOMING, language) } returns localMedia
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow(language)

        val result = repo.getUpComingMedia()

        assertThat(result).hasSize(1)
        coVerify(exactly = 0) { remote.getUpcomingMovies(any()) }
    }

    @Test
    fun `getUpComingMedia fetches remote data when local is empty`() = runTest {
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery { homeLocal.getHomeMediaByCategory(Category.UPCOMING, "en") } returns emptyList()

        val upcomingMovie = MovieDto(
            id = 4, title = "Upcoming Movie", releaseDate = "2025-06-01",
            voteAverage = 7.8, genreIds = listOf(12), overview = "", posterPath = ""
        )

        coEvery { remote.getUpcomingMovies(any()).results } returns listOf(upcomingMovie)

        val result = repo.getUpComingMedia()

        assertThat(result).hasSize(1)
        coVerify { homeLocal.addHomeMedia(any()) }
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
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow(language)
        val result = repo.getUpComingMedia()
        assertThat(result.single().id).isEqualTo(100)
    }

    @Test
    fun `getUpComingMedia throws NoInternetConnectionException when offline`() = runTest {
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        every { networkChecker.isConnected } returns MutableStateFlow(false)

        assertThrows<NoInternetConnectionException> {
            repo.getUpComingMedia()
        }
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
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        val result = repo.getNowPlayingMedia()
        assertThat(result.first().id).isEqualTo(105)
    }

    @Test
    fun `getNowPlayingMedia returns multiple movies`() = runTest {
        val movie1 = MovieDto(
            id = 5, title = "Now Playing 1", releaseDate = "2024-01-01",
            voteAverage = 6.5, genreIds = listOf(28), overview = "", posterPath = ""
        )
        val movie2 = MovieDto(
            id = 6, title = "Now Playing 2", releaseDate = "2024-01-15",
            voteAverage = 7.2, genreIds = listOf(35), overview = "", posterPath = ""
        )

        coEvery { remote.getNowPlayingMovies().results } returns listOf(movie1, movie2)

        val result = repo.getNowPlayingMedia()

        assertThat(result).hasSize(2)
        assertThat(result.map { it.id }).containsExactly(5, 6)
    }

    @Test
    fun `getNowPlayingMedia throws NoInternetConnectionException when offline`() = runTest {
        every { networkChecker.isConnected } returns MutableStateFlow(false)

        assertThrows<NoInternetConnectionException> {
            repo.getNowPlayingMedia()
        }
    }

    @Test
    fun `addMediaToLocal delegates to data source`() = runTest {
        val media = Media(
            200,
            "",
            "Local",
            MediaType.Movie,
            listOf(com.paris_2.domain.media.entity.Category.Action),
            mockk(),
            8.0
        )
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
            type = MediaTypeEntity.TvShow,
            genreIds = listOf(2),
            voteAverage = 6.6,
            releaseDate = "2023-09-09"
        )
        coEvery { local.getAllMedia() } returns flowOf(listOf(entity))
        val result = repo.getContinueWatchingMedia().single()
        assertThat(result.single().id).isEqualTo(300)
    }

    @Test
    fun `addMediaToContinueWatching throws NoInternetConnectionException when offline`() = runTest {
        every { networkChecker.isConnected } returns MutableStateFlow(false)
        val media = Media(
            1,
            "",
            "Test",
            MediaType.Movie,
            listOf(com.paris_2.domain.media.entity.Category.Action),
            mockk(),
            8.0
        )

        assertThrows<NoInternetConnectionException> {
            repo.addMediaToContinueWatching(media)
        }
    }

    @Test
    fun `getPopularMedia throws NoInternetConnectionException when offline`() = runTest {
        every { networkChecker.isConnected } returns MutableStateFlow(false)
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow(language)
        assertThrows<NoInternetConnectionException> {
            repo.getPopularMedia()
        }
    }

    @Test
    fun `addMediaToLocal throws addMediaToLocalException on failure`() = runTest {
        val media = Media(
            123,
            "",
            "Fail",
            MediaType.TvShow,
            listOf(com.paris_2.domain.media.entity.Category.Action),
            mockk(),
            4.0
        )

        coEvery { local.addMedia(any()) } throws RuntimeException("DB insert failed")

        assertThrows<FailedException> {
            repo.addMediaToContinueWatching(media)
        }
    }


    @Test
    fun `getRatedMedia throws NoRatedMediaFoundException when remote call fails`() = runTest {
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery { remote.getRatedMovies(any(), any()) } throws RuntimeException("Failed")
        coEvery { remote.getRatedTvShows(any(), any()) } returns mockk(relaxed = true)

        assertThrows<FailedException> {
            repo.getRatedMedia(accountId = 1)
        }
    }

    @Test
    fun `getMoviesByCategory should return mapped movie list from remote`() = runTest {
        val category = com.paris_2.domain.media.entity.Category.Action // assume .toId() = 28
        val page = 1
        val language = "en"

        val resultDto = ResultDto(
            id = 1,
            title = "Action Movie",
            genre_ids = listOf(28),
            overview = "Explosions everywhere",
            poster_path = "/action.jpg",
            vote_average = 8.0,
            vote_count = 100,
            adult = false,
            backdrop_path = "/backdrop.jpg",
            original_language = "en",
            original_title = "Action Movie",
            release_date = "2023-01-01",
            popularity = 1.0,
            video = true,
        )

        val movieByCategoryDto = MovieByCategoryDto(
            page = 1,
            resultDto = listOf(resultDto),
            total_pages = 5,
            total_results = 20
        )

        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow(language)
        coEvery { remote.getMoviesByCategory(28, page, language) } returns movieByCategoryDto


        val result = repo.getMoviesByCategory(category, page)


        assertThat(result.first().type).isEqualTo(MediaType.Movie)
        coVerify(exactly = 1) { remote.getMoviesByCategory(28, page, language) }
    }

    @Test
    fun `getTvShowsByCategory should return mapped TV list from remote`() = runTest {
        // Given
        val category = com.paris_2.domain.media.entity.Category.Drama // Assume toId() = 18
        val page = 1
        val language = "en"

        val tvResultDto = TvResultDto(
            adult = false,
            backdrop_path = "/backdrop_tv.jpg",
            first_air_date = "2022-05-05",
            genre_ids = listOf(18),
            id = 100,
            name = "Drama Series",
            origin_country = listOf("US"),
            original_language = "en",
            original_name = "Drama Series",
            overview = "Emotional rollercoaster",
            popularity = 70.0,
            poster_path = "/drama.jpg",
            vote_average = 9.0,
            vote_count = 500
        )

        val tvShowByCategoryDto = TvShowByCategoryDto(
            page = 1,
            tvResultDto = listOf(tvResultDto),
            total_pages = 5,
            total_results = 10
        )

        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow(language)
        coEvery { remote.getTvShowsByCategory(18, page, language) } returns tvShowByCategoryDto

        // When
        val result = repo.getTvShowsByCategory(category, page)

        // Then
        assertThat(result.first().type).isEqualTo(MediaType.TvShow)
        coVerify(exactly = 1) { remote.getTvShowsByCategory(18, page, language) }
    }
}