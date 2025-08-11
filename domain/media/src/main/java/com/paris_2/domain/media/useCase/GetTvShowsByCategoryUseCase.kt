package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.entity.Category
import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.repository.MediaRepository

class GetTvShowsByCategoryUseCase(
    private val mediaRepository: MediaRepository
) {
    suspend operator fun invoke(category: Category, page: Int): List<Media> {
        return mediaRepository.getTvShowsByCategory(
            category = category,
            page = page
        )
    }
}