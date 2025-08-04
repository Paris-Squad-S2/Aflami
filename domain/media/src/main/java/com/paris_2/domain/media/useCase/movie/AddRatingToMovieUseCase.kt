package com.paris_2.domain.media.useCase.movie

import com.paris_2.domain.media.repository.MovieRepository

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