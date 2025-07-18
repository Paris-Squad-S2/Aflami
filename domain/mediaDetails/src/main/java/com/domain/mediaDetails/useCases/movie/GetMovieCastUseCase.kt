package com.domain.mediaDetails.useCases.movie

import com.domain.mediaDetails.model.Cast
import com.domain.mediaDetails.repository.MovieRepository

class GetMovieCastUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): List<Cast> {
        return movieRepository.getMovieCast(movieId)
    }
}