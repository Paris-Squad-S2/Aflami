package com.domain.mediaDetails.useCases.movie

import com.domain.mediaDetails.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import testUtils.fakeReviews
import kotlin.test.assertEquals

class GetMovieReviewsUseCaseTest {
    private lateinit var getMovieReviewsUseCase: GetMovieReviewsUseCase
    private val movieRepository: MovieRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        getMovieReviewsUseCase = GetMovieReviewsUseCase(movieRepository)
    }

    @Test
    fun `should return movie reviews from repository`() = runTest {
        // Given
        coEvery { movieRepository.getMovieReview(movieId,page) } returns fakeReviews

        // when
        val result = getMovieReviewsUseCase(movieId,page)

        // Then
        assertEquals(result, fakeReviews)

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
    fun `should return empty list when no cast found`() = runTest{
        // Given
        coEvery { movieRepository.getMovieReview(movieId,page) } returns emptyList()

        // when
        val result = getMovieReviewsUseCase(movieId,page)

        // Then
        assertEquals(result, emptyList())
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

    private companion object{
        val movieId = 1
        val page = 1
    }

}