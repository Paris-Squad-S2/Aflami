package com.repository.media

import com.google.common.truth.Truth.assertThat
import com.repository.media.services.MediaApiService
import com.repository.media.models.remote.media.home.DatesDto
import com.repository.media.models.remote.media.home.MovieDto
import com.repository.media.models.remote.media.home.MovieListDto
import com.repository.media.models.remote.media.home.TvDto
import com.repository.media.models.remote.media.home.TvListDto
import com.repository.media.models.remote.media.profile.RatedMoviesDto
import com.repository.media.models.remote.media.profile.RatedTvShowDtoo
import com.repository.media.MediaRemoteDataSourceImpl
import com.repository.media.dto.category.MovieByCategoryDto
import com.repository.media.dto.category.ResultDto
import com.repository.media.dto.category.TvResultDto
import com.repository.media.dto.category.TvShowByCategoryDto
import com.repository.media.dto.home.DatesDto
import com.repository.media.dto.home.MovieDto
import com.repository.media.dto.home.MovieListDto
import com.repository.media.dto.home.TvDto
import com.repository.media.dto.home.TvListDto
import com.repository.media.dto.profile.RatedMoviesDto
import com.repository.media.dto.profile.RatedTvShowDtoo
import com.repository.media.services.MediaApiService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class MediaDataSourceImplTest {
    private lateinit var apiService: MediaApiService
    private lateinit var mediaDataSource: MediaRemoteDataSourceImpl

    @Before
    fun setUp() {
        apiService = mockk(relaxed = true)
        mediaDataSource = MediaRemoteDataSourceImpl(apiService)
    }

    @Test
    fun `getPopularMovies should propagate exception when API call fails`() = runTest {
        val apiException = RuntimeException("API Error")
        coEvery { apiService.getPopularMovie(any()) } throws apiException
        try {
            mediaDataSource.getPopularMovies("en")
            throw AssertionError("Should have propagated the exception")
        } catch (e: Exception) {
            assertThat(e).isEqualTo(apiException)
        }
    }

    @Test
    fun `getPopularMovies should return data from api`() = runTest {
        coEvery { apiService.getPopularMovie(any()) } returns movieListDto
        val result = mediaDataSource.getPopularMovies("en")
        assertThat(result).isEqualTo(movieListDto)
        coVerify(exactly = 1) { apiService.getPopularMovie(any()) }
    }

    @Test
    fun `getTopRatedMovies should return data from api`() = runTest {
        coEvery { apiService.getTopRatedMovie(any()) } returns movieListDto
        val result = mediaDataSource.getTopRatedMovies("en")
        assertThat(result).isEqualTo(movieListDto)
        coVerify(exactly = 1) { apiService.getTopRatedMovie(any()) }
    }

    @Test
    fun `getUpcomingMovies should return data from api`() = runTest {
        coEvery { apiService.getUpcoming(any()) } returns movieListDto
        val result = mediaDataSource.getUpcomingMovies("en")
        assertThat(result).isEqualTo(movieListDto)
        coVerify(exactly = 1) { apiService.getUpcoming(any()) }
    }

    @Test
    fun `getNowPlayingMovies should return data from api`() = runTest {
        coEvery { apiService.getNowPlaying() } returns movieListDto
        val result = mediaDataSource.getNowPlayingMovies()
        assertThat(result).isEqualTo(movieListDto)
        coVerify(exactly = 1) { apiService.getNowPlaying() }
    }

    @Test
    fun `getPopularTvShows should return data from api`() = runTest {
        coEvery { apiService.getPopularTv(any()) } returns tvListDto
        val result = mediaDataSource.getPopularTvShows("en")
        assertThat(result).isEqualTo(tvListDto)
        coVerify(exactly = 1) { apiService.getPopularTv(any()) }
    }

    @Test
    fun `getTopRatedTvShows should return data from api`() = runTest {
        coEvery { apiService.getTopRatedTv(any()) } returns tvListDto
        val result = mediaDataSource.getTopRatedTvShows("en")
        assertThat(result).isEqualTo(tvListDto)
        coVerify(exactly = 1) { apiService.getTopRatedTv(any()) }
    }

    @Test
    fun `getRatedMovies should return data from api`() = runTest {
        val ratedMoviesDto = mockk<RatedMoviesDto>()
        coEvery { apiService.getRatedMovies(any(), any()) } returns ratedMoviesDto

        val result = mediaDataSource.getRatedMovies(123, "en")

        assertThat(result).isEqualTo(ratedMoviesDto)
        coVerify(exactly = 1) { apiService.getRatedMovies(123, "en") }
    }

    @Test
    fun `getRatedTvShows should return data from api`() = runTest {
        val ratedTvShowsDto = mockk<RatedTvShowDtoo>()
        coEvery { apiService.getRatedTvShows(any(), any()) } returns ratedTvShowsDto

        val result = mediaDataSource.getRatedTvShows(123, "en")

        assertThat(result).isEqualTo(ratedTvShowsDto)
        coVerify(exactly = 1) { apiService.getRatedTvShows(123, "en") }
    }

    @Test
    fun `getMoviesByCategory should return data from api`() = runTest {
        val genreId = 28
        val page = 1
        val language = "en"
        coEvery { apiService.getMoviesByCategory(genreId, language, page) } returns movieByCategoryDto

        val result = mediaDataSource.getMoviesByCategory(genreId, page, language)

        assertThat(result).isEqualTo(movieByCategoryDto)
        coVerify(exactly = 1) { apiService.getMoviesByCategory(genreId, language, page) }
    }

    @Test
    fun `getMoviesByCategory should propagate exception when API call fails`() = runTest {
        val genreId = 28
        val page = 1
        val language = "en"
        val apiException = RuntimeException("API Error")
        coEvery { apiService.getMoviesByCategory(genreId, language, page) } throws apiException

        try {
            mediaDataSource.getMoviesByCategory(genreId, page, language)
            throw AssertionError("Should have thrown an exception")
        } catch (e: Exception) {
            assertThat(e).isEqualTo(apiException)
        }
    }

    @Test
    fun `getTvShowByCategory should return data from api`() = runTest {
        val genreId = 28
        val page = 1
        val language = "en"
        coEvery { apiService.getTvShowsByCategory(genreId, language, page) } returns tvShowByCategoryDto

        val result = mediaDataSource.getTvShowsByCategory(genreId, page, language)

        assertThat(result).isEqualTo(tvShowByCategoryDto)
        coVerify(exactly = 1) { apiService.getTvShowsByCategory(genreId, language, page) }
    }

    @Test
    fun `getTvShowByCategory should propagate exception when API call fails`() = runTest {
        val genreId = 28
        val page = 1
        val language = "en"
        val apiException = RuntimeException("API Error")
        coEvery { apiService.getTvShowsByCategory(genreId, language, page) } throws apiException

        try {
            mediaDataSource.getTvShowsByCategory(genreId, page, language)
            throw AssertionError("Should have thrown an exception")
        } catch (e: Exception) {
            assertThat(e).isEqualTo(apiException)
        }
    }

    private companion object {
        val movieListDto = MovieListDto(
            page = 1,
            totalPages = 10,
            totalResults = 200,
            results = listOf(
                MovieDto(id = 1, title = "Movie 1"),
                MovieDto(id = 2, title = "Movie 2")
            ),
            dates = DatesDto(maximum = "2023-12-01", minimum = "2023-01-01")
        )
        val tvListDto = TvListDto(
            page = 1,
            totalPages = 5,
            totalResults = 100,
            results = listOf(
                TvDto(id = 1, name = "TV 1"),
                TvDto(id = 2, name = "TV 2")
            )
        )
        val movieByCategoryDto = MovieByCategoryDto(
            page = 1,
            resultDto = listOf(
                ResultDto(id = 1, title = "Movie 1", genre_ids = listOf(1, 2)),
                ResultDto(id = 2, title = "Movie 2", genre_ids = listOf(1, 2))
            ),
            total_pages = 5,
            total_results = 5,
        )

        val tvShowByCategoryDto = TvShowByCategoryDto(
            page = 1,
            tvResultDto = listOf(
                TvResultDto(
                    adult = false,
                    backdrop_path = "/gQUdijQy29P8JwI8U36yufKrgiD.jpg",
                    first_air_date = "2023-09-21",
                    genre_ids = listOf(18, 10766),
                    id = 210555,
                    name = "Silo",
                    origin_country = listOf("US"),
                    original_language = "en",
                    original_name = "Silo",
                    overview = "A dystopian thriller set in a giant underground silo where people live under strict rules.",
                    popularity = 1876.453,
                    poster_path = "/aBbqPyZrZG1DftZFreXjsNmuuQy.jpg",
                    vote_average = 8.2,
                    vote_count = 1345
                ),
                TvResultDto(
                    adult = false,
                    backdrop_path = "/bWUQ2gSDetJjVp6VGYqCeIv5DN8.jpg",
                    first_air_date = "2020-10-23",
                    genre_ids = listOf(10765, 18),
                    id = 88329,
                    name = "The Mandalorian",
                    origin_country = listOf("US"),
                    original_language = "en",
                    original_name = "The Mandalorian",
                    overview = "A lone bounty hunter in the outer reaches of the galaxy takes on a dangerous mission.",
                    popularity = 2154.789,
                    poster_path = "/sWgBv7LV2PRoQgkxwUWNbJFQiyM.jpg",
                    vote_average = 8.6,
                    vote_count = 7890
                )
            ),
            total_pages = 5,
            total_results = 10
        )
    }
}
