package com.domain.mediaDetails.useCases.tvShows

import com.domain.mediaDetails.repository.TvShowRepository
import io.mockk.coEvery
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
        val tvShowId = 1

        // when
        coEvery { tvShowRepository.getCompanyProducts(tvShowId) } returns fakeProductionCompanies

        // Then
        val result = getTvShowsProductionCompaniesUseCase(tvShowId)
        assertEquals(result, fakeProductionCompanies)

    }

    @Test
    fun `should return empty list when no cast found`() = runTest {
        // Given
        val tvShowId = 1

        // when
        coEvery { tvShowRepository.getCompanyProducts(tvShowId) } returns emptyList()

        // Then
        val result = getTvShowsProductionCompaniesUseCase(tvShowId)
        assertTrue(result.isEmpty())
    }

}