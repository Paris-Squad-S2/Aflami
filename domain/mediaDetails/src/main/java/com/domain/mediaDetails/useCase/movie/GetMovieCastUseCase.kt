package com.domain.mediaDetails.useCase.movie

import com.domain.media.entity.Cast
import com.domain.media.repository.MovieRepository

class GetMovieCastUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): List<Cast> {
        return movieRepository.getMovieCast(movieId)
    }
}