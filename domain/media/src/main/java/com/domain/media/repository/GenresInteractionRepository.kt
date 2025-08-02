package com.domain.media.repository

import com.domain.media.model.GenreUserInteraction

interface GenresInteractionRepository {
    suspend fun upsertInteraction(interaction: GenreUserInteraction)
    suspend fun getCategoryInteractions(genreId: Int): Int?
    suspend fun getAllInteractions(): List<GenreUserInteraction>
}
