package com.domain.media.useCase

import com.domain.media.model.GenreUserInteraction
import com.domain.media.repository.GenresInteractionRepository

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