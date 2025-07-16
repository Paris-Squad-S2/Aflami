package com.example.search

import com.example.search.service.contract.GenresApiServices
import com.google.common.truth.Truth.assertThat
import com.repository.search.dto.GenreDto
import com.repository.search.dto.GenresDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GenresRemoteDataSourceImpTest {

    private val mockGenresApiServices = mockk<GenresApiServices>()

    private lateinit var genresRemoteDataSource: GenresRemoteDataSourceImp

    @Before
    fun setUp() {
        genresRemoteDataSource = GenresRemoteDataSourceImp(mockGenresApiServices)
    }

    @Test
    fun `getAllGenres should  return expected GenresDto when API call is successful`() =
        runTest {
            // Given
            val expectedGenres = GenresDto(
                genreDto = listOf(
                    GenreDto(id = 28, name = "Action"),
                    GenreDto(id = 12, name = "Adventure")
                )
            )
            // When
            coEvery { mockGenresApiServices.getAllGenres() } returns expectedGenres
            val actualGenres = genresRemoteDataSource.getAllGenres()
            // Then
            assertThat(actualGenres).isEqualTo(expectedGenres)
            coVerify(exactly = 1) { mockGenresApiServices.getAllGenres() }
        }

    @Test
    fun `getAllGenres should return empty GenresDto when API returns empty GenresDto`() =
        runTest {
            // Given
            val expectedGenres = GenresDto(genreDto = emptyList())
            coEvery { mockGenresApiServices.getAllGenres() } returns expectedGenres
            // When
            val actualGenres = genresRemoteDataSource.getAllGenres()
            // Then
            assertThat(actualGenres.genreDto).isEmpty()
            coVerify(exactly = 1) { mockGenresApiServices.getAllGenres() }
        }

    @Test
    fun `getAllGenres should propagate exception when API call fails`() =
        runTest {
            // Given
            val apiException = RuntimeException("API Error")
            coEvery { mockGenresApiServices.getAllGenres() } throws apiException

            // When and Then
            try {
                genresRemoteDataSource.getAllGenres()
                throw AssertionError("Should have propagated the exception")
            } catch (e: Exception) {
                assertThat(e).isEqualTo(apiException)
            }
            coVerify(exactly = 1) { mockGenresApiServices.getAllGenres() }
        }
}
