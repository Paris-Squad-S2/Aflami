package com.domain.media.useCase

import com.domain.media.entity.Media
import com.domain.media.repository.MediaRepository

class FilterUpComingMediaByCategoriesUseCase(
    private val mediaRepository: MediaRepository
) {
    suspend operator fun invoke(categories: List<Int>): List<Media> {
        return mediaRepository.getUpComingMedia().filter { media ->
            media.categoryIds.any {
                categories.contains(it)
            }
        }
    }
}