package com.domain.mediaDetails.useCase.tvShows

import com.domain.mediaDetails.repository.TvShowRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import testUtils.fakeProductionCompanies

class GetTvShowsProductionCompaniesUseCaseTest {
    private lateinit var getTvShowsProductionCompaniesUseCase: GetTvShowsProductionCompaniesUseCase
    private val tvShowRepository: TvShowRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        getTvShowsProductionCompaniesUseCase =
            GetTvShowsProductionCompaniesUseCase(tvShowRepository)
    }

    @Test
    fun `should return tv show production companies from repository`() = runTest {
        // Given
        coEvery { tvShowRepository.getCompanyProducts(tvShowId) } returns fakeProductionCompanies

        // when
        val result = getTvShowsProductionCompaniesUseCase(tvShowId)

        // Then
        assertEquals(result, fakeProductionCompanies)

    }

    @Test
    fun `should return empty list when no cast found`() = runTest {
        // Given
        coEvery { tvShowRepository.getCompanyProducts(tvShowId) } returns emptyList()

        // when
        val result = getTvShowsProductionCompaniesUseCase(tvShowId)

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `should call repository to get production companies`() = runTest {
        // Given
        coEvery { tvShowRepository.getCompanyProducts(tvShowId) } returns fakeProductionCompanies

        // When
        getTvShowsProductionCompaniesUseCase(tvShowId)

        // Then
        coVerify(exactly = 1) { tvShowRepository.getCompanyProducts(tvShowId) }
    }


    private companion object {
        val tvShowId = 1

    }
}