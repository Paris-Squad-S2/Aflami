package com.domain.mediaDetails.useCase.movie

import com.domain.mediaDetails.entity.Image
import com.domain.mediaDetails.repository.MovieRepository

class GetMovieGalleryUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): List<Image> {
        return movieRepository.getMovieGallery(movieId)
    }
}