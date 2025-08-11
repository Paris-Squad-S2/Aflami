package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.repository.MediaRepository

class GetMoviesByCategoryUseCase(
    private val mediaRepository: MediaRepository
) {
    suspend operator fun invoke(genreId: Int, page: Int): List<Media> {
        return mediaRepository.getMoviesByCategory(
            genreId = genreId,
            page = page
        )
    }
}