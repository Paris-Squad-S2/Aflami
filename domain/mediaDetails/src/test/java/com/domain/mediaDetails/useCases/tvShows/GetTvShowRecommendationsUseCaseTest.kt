package com.domain.mediaDetails.useCases.tvShows

import com.domain.mediaDetails.repository.TvShowRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import testUtils.fakeTvShows

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
        val tvShowId = 1
        val page = 1
        // when
        coEvery { tvShowRepository.getTvShowRecommendations(tvShowId,page) } returns fakeTvShows

        // Then
        val result = getTvShowRecommendationsUseCase(tvShowId,page)
        assertEquals(result, fakeTvShows)

    }

    @Test
    fun `should return empty list when no cast found`() = runTest {
        // Given
        val tvShowId = 1
        val page = 1


        // when
        coEvery { tvShowRepository.getTvShowRecommendations(tvShowId,page) } returns emptyList()

        // Then
        val result = getTvShowRecommendationsUseCase(tvShowId,page)
        assertTrue(result.isEmpty())
    }

}