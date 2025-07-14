package com.example.search

import com.repository.search.dto.GenreDto
import com.repository.search.dto.GenresDto
import com.example.search.service.contract.GenresApiServices
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GenresRemoteDataSourceImpTest {

    private val mockGenresApiServices = mockk<GenresApiServices>()

    private lateinit var genresRemoteDataSource: GenresRemoteDataSourceImp
    private val language = "en"
    @Before
    fun setUp() {
        genresRemoteDataSource = GenresRemoteDataSourceImp(mockGenresApiServices)
    }

    @Test
    fun `when getAllGenres is called with successful response then return expected GenresDto`() = runTest {
        val expectedGenres = GenresDto(
            genreDto = listOf(
                GenreDto(id = 28, name = "Action"),
                GenreDto(id = 12, name = "Adventure")
            )
        )
        coEvery { mockGenresApiServices.getAllGenres(language) } returns expectedGenres

        val actualGenres = genresRemoteDataSource.getAllGenres(language)

        assertThat(actualGenres).isEqualTo(expectedGenres)
        coVerify(exactly = 1) { mockGenresApiServices.getAllGenres(language) }
    }

    @Test
    fun `when getAllGenres is called with empty response then return empty GenresDto`() = runTest {
        val expectedGenres = GenresDto(genreDto = emptyList())
        coEvery { mockGenresApiServices.getAllGenres(language) } returns expectedGenres

        val actualGenres = genresRemoteDataSource.getAllGenres(language)

        assertThat(actualGenres.genreDto).isEmpty()
        coVerify(exactly = 1) { mockGenresApiServices.getAllGenres(language) }
    }

    @Test
    fun `when getAllGenres is called with exception then propagate exception`() = runTest {
        val apiException = RuntimeException("API Error")
        coEvery { mockGenresApiServices.getAllGenres(language) } throws apiException

        try {
            genresRemoteDataSource.getAllGenres(language)
            throw AssertionError("Should have propagated the exception")
        } catch (e: Exception) {
            assertThat(e).isEqualTo(apiException)
        }

        coVerify(exactly = 1) { mockGenresApiServices.getAllGenres(language) }
    }
}
