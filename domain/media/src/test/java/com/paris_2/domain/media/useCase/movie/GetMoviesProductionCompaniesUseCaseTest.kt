package com.paris_2.domain.media.useCase.movie

import com.domain.media.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.domain.media.testUtils.fakeProductionCompanies
import kotlin.test.assertEquals

class GetMoviesProductionCompaniesUseCaseTest {

    private lateinit var getMoviesProductionCompaniesUseCase: GetMoviesProductionCompaniesUseCase
    private val movieRepository: MovieRepository = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        getMoviesProductionCompaniesUseCase = GetMoviesProductionCompaniesUseCase(movieRepository)
    }

    @Test
    fun `should return movie production from repository`() = runTest {
        // Given
        coEvery { movieRepository.getCompanyProducts(movieId) } returns fakeProductionCompanies

        // When
        val result = getMoviesProductionCompaniesUseCase(movieId)

        // Then
        assertEquals(fakeProductionCompanies, result)
    }

    @Test
    fun `should return empty list when no companies found`() = runTest {
        // Given
        coEvery { movieRepository.getCompanyProducts(movieId) } returns emptyList()

        // When
        val result = getMoviesProductionCompaniesUseCase(movieId)

        // Then
        assertEquals(emptyList(), result)
    }

    @Test
    fun `should call repository method when use case is invoked`() = runTest {
        // Given
        coEvery { movieRepository.getCompanyProducts(movieId) } returns fakeProductionCompanies

        // When
        getMoviesProductionCompaniesUseCase(movieId)

        // Then
        coVerify(exactly = 1) { movieRepository.getCompanyProducts(movieId) }
    }

    private companion object {
        const val movieId = 1
    }
}
