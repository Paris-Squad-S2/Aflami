package com.paris.domain.media.useCase.movie

import com.paris.domain.media.repository.MovieRepository

class DeleteMovieRatingUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int) {
        return movieRepository.deleteMovieRating(movieId)
    }
}