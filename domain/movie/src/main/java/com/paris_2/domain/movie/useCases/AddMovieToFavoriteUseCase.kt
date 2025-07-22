package com.paris_2.domain.movie.useCases

import com.paris_2.domain.movie.repository.MovieRepository

class AddMovieToFavoriteUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int){
        return movieRepository.addMovieToFavorite(movieId)
    }
}