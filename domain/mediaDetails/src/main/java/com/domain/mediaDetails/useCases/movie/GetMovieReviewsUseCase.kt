package com.domain.mediaDetails.useCases.movie

import com.domain.mediaDetails.model.Review
import com.domain.mediaDetails.repository.MovieRepository

class GetMovieReviewsUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): List<Review> {
        return movieRepository.getMovieReview(movieId)
    }
}