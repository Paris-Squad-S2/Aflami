package com.repository.media

import com.google.common.truth.Truth.assertThat
import com.repository.media.services.GenresApiServices
import com.repository.media.models.remote.media.GenreDto
import com.repository.media.models.remote.media.GenresDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GenresDataSourceImplTest {
    private lateinit var apiService: GenresApiServices
    private lateinit var genresDataSource: GenresRemoteDataSourceImpl

    @Before
    fun setUp() {
        apiService = mockk(relaxed = true)
        genresDataSource = GenresRemoteDataSourceImpl(apiService)
    }

    @Test
    fun `getMoviesGenres should propagate exception when API call fails`() = runTest {
        // Given
        val apiException = RuntimeException("API Error")
        coEvery { apiService.getMoviesGenres(LANGUAGE) } throws apiException

        // When & Then
        try {
            genresDataSource.getMoviesGenres(LANGUAGE)
            throw AssertionError("Should have propagated the exception")
        } catch (e: Exception) {
            assertThat(e).isEqualTo(apiException)
        }
    }

    @Test
    fun `getMoviesGenres should return genres from API`() = runTest {
        // Given
        coEvery { apiService.getMoviesGenres(LANGUAGE) } returns expectedGenres
        // When
        val result = genresDataSource.getMoviesGenres(LANGUAGE)
        // Then
        assertThat(result).isEqualTo(expectedGenres)
    }

    @Test
    fun `getMoviesGenres should call service once`() = runTest {
        // Given
        coEvery { apiService.getMoviesGenres(LANGUAGE) } returns expectedGenres
        // When
        genresDataSource.getMoviesGenres(LANGUAGE)
        // Then
        coVerify(exactly = 1) { apiService.getMoviesGenres(LANGUAGE) }
    }

    private companion object {
        const val LANGUAGE = "en"
        val expectedGenres = GenresDto(
            genreDto = listOf(
                GenreDto(id = 28, name = "Action"),
                GenreDto(id = 12, name = "Adventure")
            )
        )
    }
}
