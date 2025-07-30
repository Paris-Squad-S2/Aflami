package com.domain.mediaDetails.useCase.movie

import com.domain.mediaDetails.repository.MovieRepository

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