package com.domain.media.useCase.tvShows

import com.domain.media.repository.TvShowRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.domain.media.testUtils.fakeSeasons
import kotlin.test.assertEquals

class GetSeasonsUseCaseTest {

    private lateinit var getSeasonDetailsUseCase: GetSeasonDetailsUseCase
    private val tvShowRepository: TvShowRepository = mockk(relaxed = true)

    @BeforeEach
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

        // When
        val result = getSeasonDetailsUseCase(tvShowId, seasonNumber)

        // Then
        assertEquals(fakeSeasons.first(), result)
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
        const val tvShowId = 1
        const val seasonNumber = 1
    }
}
