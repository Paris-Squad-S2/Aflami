package com.domain.mediaDetails.useCase.movie

import com.domain.mediaDetails.model.Review
import com.domain.mediaDetails.repository.MovieRepository

class GetMovieReviewsUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int, page: Int=1): List<Review> {
        return movieRepository.getMovieReview(movieId,page)
    }
}