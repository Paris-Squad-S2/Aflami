package com.domain.mediaDetails.useCases.movie

import com.domain.mediaDetails.model.Movie
import com.domain.mediaDetails.repository.MovieRepository

class GetMovieRecommendationsUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int, page: Int): List<Movie> {
        return movieRepository.getMovieRecommendations(movieId, page)
    }
}