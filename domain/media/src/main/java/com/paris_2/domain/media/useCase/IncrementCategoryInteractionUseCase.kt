package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.entity.Genre
import com.paris_2.domain.media.entity.GenreUserInteraction
import com.paris_2.domain.media.repository.GenresInteractionRepository

class IncrementCategoryInteractionUseCase(
    private val genresInteractionRepository: GenresInteractionRepository
) {
    suspend operator fun invoke(genre: List<Genre>) {
        genre.forEach { genre ->
            val count = genresInteractionRepository.getCategoryInteractions(genre) ?: 0
            genresInteractionRepository.upsertInteraction(
                GenreUserInteraction(genre = genre, interactionCount = count + 1)
            )
        }
    }
}