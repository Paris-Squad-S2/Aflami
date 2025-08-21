package com.paris.domain.media.useCase.movie

import com.paris.domain.media.entity.Cast
import com.paris.domain.media.repository.MovieRepository

class GetMovieCastUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): List<Cast> {
        return movieRepository.getMovieCast(movieId)
    }
}