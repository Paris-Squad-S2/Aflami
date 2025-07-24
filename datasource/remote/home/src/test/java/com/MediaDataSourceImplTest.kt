package com

import com.google.common.truth.Truth.assertThat
import com.repository.home.MediaApiService
import com.repository.home.MediaDataSourceImpl
import com.repository.home.dto.DatesDto
import com.repository.home.dto.MovieDto
import com.repository.home.dto.MovieListDto
import com.repository.home.dto.TvDto
import com.repository.home.dto.TvListDto
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
        coEvery { apiService.getPopularMovie() } throws apiException
        try {
            mediaDataSource.getPopularMovies()
            throw AssertionError("Should have propagated the exception")
        } catch (e: Exception) {
            assertThat(e).isEqualTo(apiException)
        }
    }

    @Test
    fun `getPopularMovies should return data from api`() = runTest {
        coEvery { apiService.getPopularMovie() } returns movieListDto
        val result = mediaDataSource.getPopularMovies()
        assertThat(result).isEqualTo(movieListDto)
        coVerify(exactly = 1) { apiService.getPopularMovie() }
    }

    @Test
    fun `getTopRatedMovies should return data from api`() = runTest {
        coEvery { apiService.getTopRatedMovie() } returns movieListDto
        val result = mediaDataSource.getTopRatedMovies()
        assertThat(result).isEqualTo(movieListDto)
        coVerify(exactly = 1) { apiService.getTopRatedMovie() }
    }

    @Test
    fun `getUpcomingMovies should return data from api`() = runTest {
        coEvery { apiService.getUpcoming() } returns movieListDto
        val result = mediaDataSource.getUpcomingMovies()
        assertThat(result).isEqualTo(movieListDto)
        coVerify(exactly = 1) { apiService.getUpcoming() }
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
        coEvery { apiService.getPopularTv() } returns tvListDto
        val result = mediaDataSource.getPopularTvShows()
        assertThat(result).isEqualTo(tvListDto)
        coVerify(exactly = 1) { apiService.getPopularTv() }
    }

    @Test
    fun `getTopRatedTvShows should return data from api`() = runTest {
        coEvery { apiService.getTopRatedTv() } returns tvListDto
        val result = mediaDataSource.getTopRatedTvShows()
        assertThat(result).isEqualTo(tvListDto)
        coVerify(exactly = 1) { apiService.getTopRatedTv() }
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
