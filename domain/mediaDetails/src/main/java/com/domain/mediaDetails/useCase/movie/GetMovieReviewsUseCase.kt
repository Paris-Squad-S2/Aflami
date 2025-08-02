package com.domain.mediaDetails.useCase.movie

import com.domain.media.entity.Review
import com.domain.media.repository.MovieRepository

class GetMovieReviewsUseCase(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(movieId: Int, page: Int ): List<Review> {
        return movieRepository.getMovieReview(movieId, page)
    }
}