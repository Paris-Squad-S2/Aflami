package com.paris_2.domain.media.useCase.movie

import com.paris_2.domain.media.entity.Image
import com.paris_2.domain.media.repository.MovieRepository

class GetMovieGalleryUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): List<Image> {
        return movieRepository.getMovieGallery(movieId)
    }
}