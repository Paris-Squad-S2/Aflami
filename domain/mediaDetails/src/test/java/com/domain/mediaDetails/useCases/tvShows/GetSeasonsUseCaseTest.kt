package com.domain.mediaDetails.useCases.tvShows

import com.domain.mediaDetails.repository.TvShowRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import testUtils.fakeSeasons
import kotlin.test.assertEquals

class GetSeasonsUseCaseTest {

    private lateinit var getSeasonDetailsUseCase: GetSeasonDetailsUseCase
    private val tvShowRepository: TvShowRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        getSeasonDetailsUseCase = GetSeasonDetailsUseCase(tvShowRepository)
    }

    @Test
    fun `should return season details from repository`() = runTest {
        // Given
        coEvery {
            tvShowRepository.getSeasonDetails(
                tvShowId,
                seasonNumber
            )
        } returns fakeSeasons.first()

        // when
        val result = getSeasonDetailsUseCase(tvShowId, seasonNumber)

        // Then
        assertEquals(result, fakeSeasons.first())

    }

    @Test
    fun `should call repository method when use case is invoked`() = runTest {
        // Given
        coEvery {
            tvShowRepository.getSeasonDetails(
                tvShowId,
                seasonNumber
            )
        } returns fakeSeasons.first()

        // When
        getSeasonDetailsUseCase(tvShowId, seasonNumber)

        // Then
        coVerify(exactly = 1) { tvShowRepository.getSeasonDetails(tvShowId, seasonNumber) }
    }

    private companion object {
        val tvShowId = 1
        val seasonNumber = 1
    }
}