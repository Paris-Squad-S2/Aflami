package com.domain.search.useCase

import com.domain.search.model.GenreUserInteraction
import com.domain.search.repository.GenresInteractionRepository

class IncrementCategoryInteractionUseCase(
    private val genresInteractionRepository: GenresInteractionRepository
) {
    suspend operator fun invoke(genreIds: List<Int>) {
        genreIds.forEach { genreId ->
            val count = genresInteractionRepository.getCategoryInteractions(genreId) ?: 0
            genresInteractionRepository.upsertInteraction(
                GenreUserInteraction(genreId = genreId, interactionCount = count + 1)
            )
        }
    }
}