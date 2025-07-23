package com.domain.mediaDetails.useCases.tvShows

import com.domain.mediaDetails.repository.TvShowRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import testUtils.fakeTvShowsSimilar

class GetTvShowRecommendationsUseCaseTest {
    private lateinit var getTvShowRecommendationsUseCase: GetTvShowRecommendationsUseCase
    private val tvShowRepository: TvShowRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        getTvShowRecommendationsUseCase = GetTvShowRecommendationsUseCase(tvShowRepository)
    }

    @Test
    fun `should return tv show recommendation from repository`() = runTest {
        // Given
        coEvery {
            tvShowRepository.getTvShowRecommendations(
                tvShowId,
                page
            )
        } returns fakeTvShowsSimilar

        // when
        val result = getTvShowRecommendationsUseCase(tvShowId, page)

        // Then
        assertEquals(result, fakeTvShowsSimilar)

    }

    @Test
    fun `should verify repository interaction for recommendations`() = runTest {
        // Given
        coEvery {
            tvShowRepository.getTvShowRecommendations(
                tvShowId,
                page
            )
        } returns fakeTvShowsSimilar

        // When
        getTvShowRecommendationsUseCase(tvShowId, page)

        // Then
        coVerify(exactly = 1) { tvShowRepository.getTvShowRecommendations(tvShowId, page) }
    }

    @Test
    fun `should return empty list when no cast found`() = runTest {
        // Given
        coEvery { tvShowRepository.getTvShowRecommendations(tvShowId, page) } returns emptyList()

        // when
        val result = getTvShowRecommendationsUseCase(tvShowId, page)

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `should verify repository interaction for empty recommendations`() = runTest {
        // Given
        coEvery { tvShowRepository.getTvShowRecommendations(tvShowId, page) } returns emptyList()

        // When
        getTvShowRecommendationsUseCase(tvShowId, page)

        // Then
        coVerify(exactly = 1) { tvShowRepository.getTvShowRecommendations(tvShowId, page) }
    }

    private companion object {
        val tvShowId = 1
        val page = 1
    }

}