package com.domain.media.useCase.movie

import com.domain.media.entity.MovieSimilar
import com.domain.media.repository.MovieRepository

class GetMovieRecommendationsUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int, page: Int): List<MovieSimilar> {
        return movieRepository.getMovieRecommendations(movieId, page)
    }
}