package com.repository.search

import com.google.common.truth.Truth.assertThat
import com.repository.search.dto.GenreDto
import com.repository.search.dto.GenresDto
import com.repository.search.service.implementation.RetrofitGenresApiServices
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GenresRemoteDataSourceImpTest {

    private lateinit var retrofitGenresApiServices: RetrofitGenresApiServices

    private lateinit var genresRemoteDataSource: GenresRemoteDataSourceImp

    @Before
    fun setUp() {
        retrofitGenresApiServices = mockk(relaxed = true)
        genresRemoteDataSource = GenresRemoteDataSourceImp(retrofitGenresApiServices)
    }

    @Test
    fun `getAllGenresMovie should propagate exception when API call fails`() =
        runTest {
            // Given
            val apiException = RuntimeException("API Error")
            coEvery { retrofitGenresApiServices.getAllGenresMovie(LANGUAGE) } throws apiException

            // When and Then
            try {
                genresRemoteDataSource.getAllGenres(LANGUAGE)
                throw AssertionError("Should have propagated the exception")
            } catch (e: Exception) {
                assertThat(e).isEqualTo(apiException)
            }
        }


    @Test
    fun `getAllGenresMovie should call API once`() = runTest {
        // Given
        coEvery { retrofitGenresApiServices.getAllGenresMovie(LANGUAGE) } returns expectedGenres

        // When
        genresRemoteDataSource.getAllGenres(LANGUAGE)

        // Then
        coVerify(exactly = 1) { retrofitGenresApiServices.getAllGenresMovie(LANGUAGE) }
    }

    @Test
    fun `getAllGenresTvShow should call API once`() = runTest {
        // Given
        coEvery { retrofitGenresApiServices.getAllGenresTvShow(LANGUAGE) } returns expectedGenres

        // When
        genresRemoteDataSource.getAllGenres(LANGUAGE)

        // Then
        coVerify(exactly = 1) { retrofitGenresApiServices.getAllGenresTvShow(LANGUAGE) }

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
