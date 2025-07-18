package com.domain.search.useCases

import com.domain.search.model.Media
import com.domain.search.repository.GenresInteractionRepository

class SortingMediaByCategoriesInteractionUseCase(
    private val genresInteractionRepository: GenresInteractionRepository
) {
    suspend operator fun invoke(list: List<Media>): List<Media> {
        val interactions = genresInteractionRepository.getAllInteractions()
        val interactionMap = interactions.associate { it.genreId to it.interactionCount }
        return list.sortedByDescending { media ->
            media.categories.sumOf { categoryId ->
                interactionMap[categoryId] ?: 0
            }
        }
    }
}