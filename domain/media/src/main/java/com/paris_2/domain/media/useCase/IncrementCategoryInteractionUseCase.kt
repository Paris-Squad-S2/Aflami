package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.entity.Category
import com.paris_2.domain.media.entity.GenreUserInteraction
import com.paris_2.domain.media.repository.GenresInteractionRepository
import javax.inject.Inject

class IncrementCategoryInteractionUseCase @Inject constructor(
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