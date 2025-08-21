package com.paris.domain.media.useCase.search

import com.paris.domain.media.entity.Category
import com.paris.domain.media.entity.GenreUserInteraction
import com.paris.domain.media.repository.GenresInteractionRepository

class IncrementCategoryInteractionUseCase(
    private val genresInteractionRepository: GenresInteractionRepository
) {
    suspend operator fun invoke(categories: List<Category>) {
        categories.forEach { genreId ->
            val count = genresInteractionRepository.getCategoryInteractions(genreId) ?: 0
            genresInteractionRepository.upsertInteraction(
                GenreUserInteraction(category = genreId, interactionCount = count + 1)
            )
        }
    }
}