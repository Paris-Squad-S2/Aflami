package com.paris_2.domain.media.useCase.movie

import com.paris_2.domain.media.entity.Movie
import com.paris_2.domain.media.repository.MovieRepository

class GetMovieDetailsUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Movie {
        return movieRepository.getMovieDetails(movieId)
    }
}