package com.domain.mediaDetails.useCases.movie

import com.domain.mediaDetails.repository.MovieRepository

class AddMovieToFavoriteUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int){
        return movieRepository.addMovieToFavorite(movieId)
    }
}