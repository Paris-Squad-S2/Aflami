package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.repository.GenresInteractionRepository

class SortingMediaByCategoriesInteractionUseCase(
    private val genresInteractionRepository: GenresInteractionRepository
) {
    suspend operator fun invoke(list: List<Media>): List<Media> {
        val interactions = genresInteractionRepository.getAllInteractions()
        val interactionMap = interactions.associate { it.genreId to it.interactionCount }
        return list.sortedByDescending { media ->
            media.categoryIds.sumOf { categoryId ->
                interactionMap[categoryId] ?: 0
            }
        }
    }
}