package com.paris_2.domain.media.useCase.tvShows

import com.google.common.truth.Truth.assertThat
import com.paris_2.domain.media.repository.TvShowRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.paris_2.domain.media.testUtils.fakeProductionCompanies

class GetTvShowsProductionCompaniesUseCaseTest {

    private lateinit var getTvShowsProductionCompaniesUseCase: GetTvShowsProductionCompaniesUseCase
    private val tvShowRepository: TvShowRepository = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        getTvShowsProductionCompaniesUseCase =
            GetTvShowsProductionCompaniesUseCase(tvShowRepository)
    }

    @Test
    fun `should return tv show production companies from repository`() = runTest {
        // Given
        coEvery { tvShowRepository.getCompanyProducts(tvShowId) } returns fakeProductionCompanies

        // When
        val result = getTvShowsProductionCompaniesUseCase(tvShowId)

        // Then
        assertThat(result).isEqualTo(fakeProductionCompanies)
    }

    @Test
    fun `should return empty list when no production companies found`() = runTest {
        // Given
        coEvery { tvShowRepository.getCompanyProducts(tvShowId) } returns emptyList()

        // When
        val result = getTvShowsProductionCompaniesUseCase(tvShowId)

        // Then
        assertThat(result).isEmpty()
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
        const val tvShowId = 1
    }
}