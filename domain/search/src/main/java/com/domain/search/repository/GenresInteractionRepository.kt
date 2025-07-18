package com.domain.search.repository

import com.domain.search.model.GenreUserInteractionModel

interface GenresInteractionRepository {
    suspend fun upsertInteraction(interaction: GenreUserInteractionModel)
    suspend fun getCategoryInteractions(genreId: Int): Int?
    suspend fun getAllInteractions(): List<GenreUserInteractionModel>
}
