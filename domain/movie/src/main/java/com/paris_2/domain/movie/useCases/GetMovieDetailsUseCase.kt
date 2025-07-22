package com.paris_2.domain.movie.useCases

import com.paris_2.domain.movie.model.Movie
import com.paris_2.domain.movie.repository.MovieRepository

class GetMovieDetailsUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Movie {
        return movieRepository.getMovieDetails(movieId)
    }
}