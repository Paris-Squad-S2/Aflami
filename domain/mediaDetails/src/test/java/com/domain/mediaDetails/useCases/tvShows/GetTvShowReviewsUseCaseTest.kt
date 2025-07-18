package com.domain.mediaDetails.useCases.tvShows

import com.domain.mediaDetails.repository.TvShowRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import testUtils.fakeReviews

class GetTvShowReviewsUseCaseTest {
    private lateinit var getTvShowReviewsUseCase: GetTvShowReviewsUseCase
    private val tvShowRepository: TvShowRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        getTvShowReviewsUseCase = GetTvShowReviewsUseCase(tvShowRepository)
    }

    @Test
    fun `should return tv show review from repository`() = runTest {
        // Given
        val tvShowId = 1
        val page = 1

        // when
        coEvery { tvShowRepository.getTvShowReview(tvShowId, page) } returns fakeReviews

        // Then
        val result = getTvShowReviewsUseCase(tvShowId,page)
        assertEquals(result, fakeReviews)

    }

    @Test
    fun `should return empty list when no cast found`() = runTest {
        // Given
        val tvShowId = 1
        val page = 1

        // when
        coEvery { tvShowRepository.getTvShowReview(tvShowId,page) } returns emptyList()

        // Then
        val result = getTvShowReviewsUseCase(tvShowId,page)
        assertTrue(result.isEmpty())
    }

}