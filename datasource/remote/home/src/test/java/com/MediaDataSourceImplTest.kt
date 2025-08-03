package com

import com.google.common.truth.Truth.assertThat
import com.repository.home.MediaApiService
import com.repository.home.MediaDataSourceImpl
import com.repository.media.dto.DatesDto
import com.repository.media.dto.MovieDto
import com.repository.media.dto.MovieListDto
import com.repository.media.dto.TvDto
import com.repository.media.dto.TvListDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class MediaDataSourceImplTest {
    private lateinit var apiService: MediaApiService
    private lateinit var mediaDataSource: MediaDataSourceImpl

    @Before
    fun setUp() {
        apiService = mockk(relaxed = true)
        mediaDataSource = MediaDataSourceImpl(apiService)
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
    }
}
