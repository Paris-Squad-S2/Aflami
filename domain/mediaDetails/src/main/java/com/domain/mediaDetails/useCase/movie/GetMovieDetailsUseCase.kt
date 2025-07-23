package com.domain.mediaDetails.useCase.movie

import com.domain.mediaDetails.model.Movie
import com.domain.mediaDetails.repository.MovieRepository

class GetMovieDetailsUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Movie {
        return movieRepository.getMovieDetails(movieId)
    }
}