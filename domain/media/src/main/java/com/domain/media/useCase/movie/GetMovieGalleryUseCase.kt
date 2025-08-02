package com.domain.media.useCase.movie

import com.domain.media.entity.Image
import com.domain.media.repository.MovieRepository

class GetMovieGalleryUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): List<Image> {
        return movieRepository.getMovieGallery(movieId)
    }
}