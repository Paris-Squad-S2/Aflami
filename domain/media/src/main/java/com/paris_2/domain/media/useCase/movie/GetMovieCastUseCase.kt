package com.paris_2.domain.media.useCase.movie

import com.paris_2.domain.media.entity.Cast
import com.paris_2.domain.media.repository.MovieRepository
import javax.inject.Inject

class GetMovieCastUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): List<Cast> {
        return movieRepository.getMovieCast(movieId)
    }
}