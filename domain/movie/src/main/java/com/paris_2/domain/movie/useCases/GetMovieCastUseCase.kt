package com.paris_2.domain.movie.useCases

import com.paris_2.domain.movie.model.MovieCast
import com.paris_2.domain.movie.repository.MovieRepository

class GetMovieCastUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): List<MovieCast> {
        return movieRepository.getMovieCast(movieId)
    }
}