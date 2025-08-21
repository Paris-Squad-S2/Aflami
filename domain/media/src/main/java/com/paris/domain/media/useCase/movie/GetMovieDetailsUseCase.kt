package com.paris.domain.media.useCase.movie

import com.paris.domain.media.entity.Movie
import com.paris.domain.media.repository.MovieRepository

class GetMovieDetailsUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Movie {
        return movieRepository.getMovieDetails(movieId)
    }
}