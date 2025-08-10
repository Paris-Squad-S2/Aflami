package com.paris_2.domain.media.useCase.movie

import com.paris_2.domain.media.repository.MovieRepository
import javax.inject.Inject

class DeleteMovieRatingUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int) {
        return movieRepository.deleteMovieRating(movieId)
    }
}