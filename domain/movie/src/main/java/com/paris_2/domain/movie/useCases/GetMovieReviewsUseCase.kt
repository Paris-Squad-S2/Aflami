package com.paris_2.domain.movie.useCases

import com.paris_2.domain.movie.model.MovieReview
import com.paris_2.domain.movie.repository.MovieRepository

class GetMovieReviewsUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int, page: Int): List<MovieReview> {
        return movieRepository.getMovieReview(movieId,page)
    }
}