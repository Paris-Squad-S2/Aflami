package com.domain.media.useCase.movie

import com.domain.media.repository.MovieRepository

class AddRatingToMovieUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int, rating: Float) {
        return movieRepository.addRatingToMovie(
            movieId,
            rating,
        )
    }
}