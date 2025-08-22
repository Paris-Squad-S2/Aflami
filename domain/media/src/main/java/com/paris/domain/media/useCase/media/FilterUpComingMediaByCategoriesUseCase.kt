package com.paris.domain.media.useCase.media

import com.paris.domain.media.entity.Category
import com.paris.domain.media.entity.Media
import com.paris.domain.media.repository.MediaRepository

class FilterUpComingMediaByCategoriesUseCase(
    private val mediaRepository: MediaRepository
) {
    suspend operator fun invoke(categories: List<Category>): List<Media> {
        return mediaRepository.getUpComingMedia().filter { media ->
            media.categories.any {
                categories.contains(it)
            }
        }
    }
}