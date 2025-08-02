package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.entity.GenreUserInteraction
import com.paris_2.domain.media.repository.GenresInteractionRepository

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