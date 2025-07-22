package com.paris_2.domain.movie.useCases

import com.paris_2.domain.movie.model.MovieSimilar
import com.paris_2.domain.movie.repository.MovieRepository

class GetMovieRecommendationsUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int, page: Int): List<MovieSimilar> {
        return movieRepository.getMovieRecommendations(movieId, page)
    }
}