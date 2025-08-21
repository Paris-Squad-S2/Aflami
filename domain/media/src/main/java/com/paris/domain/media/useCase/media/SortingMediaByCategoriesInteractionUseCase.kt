package com.paris.domain.media.useCase.media

import com.paris.domain.media.entity.Media
import com.paris.domain.media.repository.GenresInteractionRepository

class SortingMediaByCategoriesInteractionUseCase(
    private val genresInteractionRepository: GenresInteractionRepository
) {
    suspend operator fun invoke(list: List<Media>): List<Media> {
        val interactions = genresInteractionRepository.getAllInteractions()
        val interactionMap = interactions.associate { it.category to it.interactionCount }
        return list.sortedByDescending { media ->
            media.categories.sumOf { categoryId ->
                interactionMap[categoryId] ?: 0
            }
        }
    }
}