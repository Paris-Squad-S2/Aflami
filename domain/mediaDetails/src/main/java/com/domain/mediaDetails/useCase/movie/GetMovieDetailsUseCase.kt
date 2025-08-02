package com.domain.mediaDetails.useCase.movie

import com.domain.media.entity.Movie
import com.domain.media.repository.MovieRepository

class GetMovieDetailsUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Movie {
        return movieRepository.getMovieDetails(movieId)
    }
}