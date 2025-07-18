package com.domain.search.useCases

import com.domain.search.model.GenreUserInteractionModel
import com.domain.search.repository.GenresInteractionRepository

class IncrementCategoryInteractionUseCase(
    private val genresInteractionRepository: GenresInteractionRepository
) {
    suspend operator fun invoke(genreIds: List<Int>) {
        genreIds.forEach { genreId ->
            val count = genresInteractionRepository.getCategoryInteractions(genreId) ?: 0
            genresInteractionRepository.upsertInteraction(
                GenreUserInteractionModel(genreId = genreId, interactionCount = count + 1)
            )
        }
    }
}