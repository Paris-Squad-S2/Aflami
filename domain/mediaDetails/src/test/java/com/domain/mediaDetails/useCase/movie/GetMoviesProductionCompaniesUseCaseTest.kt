package com.domain.mediaDetails.useCase.movie

import com.domain.mediaDetails.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import testUtils.fakeProductionCompanies
import kotlin.test.assertEquals

class GetMoviesProductionCompaniesUseCaseTest {
    private lateinit var getMoviesProductionCompaniesUseCase: GetMoviesProductionCompaniesUseCase
    private val movieRepository: MovieRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        getMoviesProductionCompaniesUseCase = GetMoviesProductionCompaniesUseCase(movieRepository)
    }

    @Test
    fun `should return movie production from repository`() = runTest {
        // Given
        val movieId = 1
        coEvery { movieRepository.getCompanyProducts(movieId) } returns fakeProductionCompanies

        // Then
        val result = getMoviesProductionCompaniesUseCase(movieId)

        // when
        assertEquals(result, fakeProductionCompanies)

    }

    @Test
    fun `should return empty list when no cast found`() = runTest{
        // Given
        coEvery { movieRepository.getCompanyProducts(movieId) } returns emptyList()

        // Then
        val result = getMoviesProductionCompaniesUseCase(movieId)

        // when
        assertEquals(result, emptyList())
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

    private companion object{
        val movieId = 1

    }


}