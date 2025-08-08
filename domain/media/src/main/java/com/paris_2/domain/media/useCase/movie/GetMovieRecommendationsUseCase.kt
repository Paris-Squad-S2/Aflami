package com.paris_2.domain.media.useCase.movie

import com.paris_2.domain.media.entity.MovieSimilar
import com.paris_2.domain.media.repository.MovieRepository

class GetMovieRecommendationsUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int, page: Int): List<MovieSimilar> {
        return movieRepository.getMovieRecommendations(movieId, page)
    }
}