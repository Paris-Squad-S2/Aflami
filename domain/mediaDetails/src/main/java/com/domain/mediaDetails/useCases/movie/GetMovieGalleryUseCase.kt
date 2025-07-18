package com.domain.mediaDetails.useCases.movie

import com.domain.mediaDetails.model.Gallery
import com.domain.mediaDetails.repository.MovieRepository

class GetMovieGalleryUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Gallery {
        return movieRepository.getMovieGallery(movieId)
    }
}