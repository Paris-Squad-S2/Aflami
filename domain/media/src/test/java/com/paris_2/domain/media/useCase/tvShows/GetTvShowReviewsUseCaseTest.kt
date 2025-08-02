package com.paris_2.domain.media.useCase.tvShows

import com.domain.media.repository.TvShowRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.domain.media.testUtils.fakeReviews

class GetTvShowReviewsUseCaseTest {

    private lateinit var getTvShowReviewsUseCase: GetTvShowReviewsUseCase
    private val tvShowRepository: TvShowRepository = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        getTvShowReviewsUseCase = GetTvShowReviewsUseCase(tvShowRepository)
    }

    @Test
    fun `should return tv show review from repository`() = runTest {
        // Given
        coEvery { tvShowRepository.getTvShowReview(tvShowId, page) } returns fakeReviews

        // When
        val result = getTvShowReviewsUseCase(tvShowId, page)

        // Then
        assertEquals(fakeReviews, result)
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
    fun `should return empty list when no reviews found`() = runTest {
        // Given
        coEvery { tvShowRepository.getTvShowReview(tvShowId, page) } returns emptyList()

        // When
        val result = getTvShowReviewsUseCase(tvShowId, page)

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `should verify repository call when no reviews found`() = runTest {
        // Given
        coEvery { tvShowRepository.getTvShowReview(tvShowId, page) } returns emptyList()

        // When
        getTvShowReviewsUseCase(tvShowId, page)

        // Then
        coVerify(exactly = 1) { tvShowRepository.getTvShowReview(tvShowId, page) }
    }

    private companion object {
        const val tvShowId = 1
        const val page = 1
    }
}
