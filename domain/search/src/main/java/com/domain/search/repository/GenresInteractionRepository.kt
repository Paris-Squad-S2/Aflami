package com.domain.search.repository

import com.domain.search.model.GenreUserInteraction

interface GenresInteractionRepository {
    suspend fun upsertInteraction(interaction: GenreUserInteraction)
    suspend fun getCategoryInteractions(genreId: Int): Int?
    suspend fun getAllInteractions(): List<GenreUserInteraction>
}
