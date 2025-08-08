package com.paris_2.domain.media.useCase.movie

import com.paris_2.domain.media.entity.Review
import com.paris_2.domain.media.repository.MovieRepository

class GetMovieReviewsUseCase(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(movieId: Int, page: Int ): List<Review> {
        return movieRepository.getMovieReview(movieId, page)
    }
}