package com.domain.mediaDetails.useCase.movie

import com.domain.mediaDetails.repository.MovieRepository

class AddMovieToFavoriteUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int){
        return movieRepository.addMovieToFavorite(movieId)
    }
}