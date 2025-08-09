package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.repository.MediaRepository
import com.paris_2.domain.media.entity.Genre

class FilterUpComingMediaByCategoriesUseCase(
    private val mediaRepository: MediaRepository
) {
    suspend operator fun invoke(categories: List<Genre>): List<Media> {
        return mediaRepository.getUpComingMedia().filter { media ->
            media.genres.any { categories.contains(it) }
        }
    }
}