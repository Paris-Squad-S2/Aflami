package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.entity.Category
import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.repository.MediaRepository
import javax.inject.Inject

class FilterUpComingMediaByCategoriesUseCase @Inject constructor(
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