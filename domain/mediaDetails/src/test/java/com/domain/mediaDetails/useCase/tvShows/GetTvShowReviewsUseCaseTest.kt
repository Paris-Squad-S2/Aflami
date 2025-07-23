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
        coEvery { tvShowRepository.getTvShowReview(tvShowId, page) } returns fakeReviews

        // when
        val result = getTvShowReviewsUseCase(tvShowId, page)

        // Then
        assertEquals(result, fakeReviews)

    }

    @Test
    fun `should call repository to get tv show reviews`() = runTest {
        // Given
        coEvery { tvShowRepository.getTvShowReview(tvShowId, page) } returns fakeReviews

        // When
        getTvShowReviewsUseCase(tvShowId, page)

        // Then
        coVerify(exactly = 1) { tvShowRepository.getTvShowReview(tvShowId, page) }
    }

    @Test
    fun `should return empty list when no cast found`() = runTest {
        // Given
        coEvery { tvShowRepository.getTvShowReview(tvShowId, page) } returns emptyList()

        // when
        val result = getTvShowReviewsUseCase(tvShowId, page)

        // Then
        assertTrue(result.isEmpty())
    }


    @Test
    fun `should return empty list when no reviews found`() = runTest {
        // Given
        coEvery { tvShowRepository.getTvShowReview(tvShowId, page) } returns emptyList()

        // When
        val result = getTvShowReviewsUseCase(tvShowId, page)

        // Then
        assertTrue(result.isEmpty())
    }


    private companion object {
        val tvShowId = 1
        val page = 1
    }

}