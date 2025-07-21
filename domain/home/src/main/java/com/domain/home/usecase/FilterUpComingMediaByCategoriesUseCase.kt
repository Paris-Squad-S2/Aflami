package com.domain.home.usecase

import com.domain.home.model.Media
import com.domain.home.repository.MediaRepository

class FilterUpComingMediaByCategoriesUseCase(
    private val mediaRepository : MediaRepository
) {
    suspend operator fun invoke(categories: List<Int>): List<Media>{
        return mediaRepository.getUpComingMedia().filter { media ->
            media.genreIds.any {
                categories.contains(it)
            }
        }
    }
}