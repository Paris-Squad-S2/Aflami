package com.paris_2.domain.movie.useCases

import com.paris_2.domain.movie.model.MovieGallery
import com.paris_2.domain.movie.repository.MovieRepository

class GetMovieGalleryUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): MovieGallery {
        return movieRepository.getMovieGallery(movieId)
    }
}