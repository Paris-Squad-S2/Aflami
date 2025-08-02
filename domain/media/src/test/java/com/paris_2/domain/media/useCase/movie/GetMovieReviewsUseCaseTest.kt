package com.paris_2.domain.media.useCase.movie

import com.paris_2.domain.media.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.paris_2.domain.media.testUtils.fakeReviews
import kotlin.test.assertEquals

class GetMovieReviewsUseCaseTest {

    private lateinit var getMovieReviewsUseCase: GetMovieReviewsUseCase
    private val movieRepository: MovieRepository = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        getMovieReviewsUseCase = GetMovieReviewsUseCase(movieRepository)
    }

    @Test
    fun `should return movie reviews from repository`() = runTest {
        // Given
        coEvery { movieRepository.getMovieReview(movieId, page) } returns fakeReviews

        // When
        val result = getMovieReviewsUseCase(movieId, page)

        // Then
        assertEquals(fakeReviews, result)
    }

    @Test
    fun `should verify repository call when getting movie reviews`() = runTest {
        // Given
        coEvery { movieRepository.getMovieReview(movieId, page) } returns fakeReviews

        // When
        getMovieReviewsUseCase(movieId, page)

        // Then
        coVerify(exactly = 1) { movieRepository.getMovieReview(movieId, page) }
    }

    @Test
    fun `should return empty list when no reviews found`() = runTest {
        // Given
        coEvery { movieRepository.getMovieReview(movieId, page) } returns emptyList()

        // When
        val result = getMovieReviewsUseCase(movieId, page)

        // Then
        assertEquals(emptyList(), result)
    }

    @Test
    fun `should verify repository call when no reviews found`() = runTest {
        // Given
        coEvery { movieRepository.getMovieReview(movieId, page) } returns emptyList()

        // When
        getMovieReviewsUseCase(movieId, page)

        // Then
        coVerify(exactly = 1) { movieRepository.getMovieReview(movieId, page) }
    }

    private companion object {
        const val movieId = 1
        const val page = 1
    }
}
