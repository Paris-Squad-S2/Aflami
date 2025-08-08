package com.repository.media.datasource.local

import com.repository.media.entity.GenreUserInteractionEntity


interface GenresInteractionDataSource {
    suspend fun upsertInteraction(interaction: GenreUserInteractionEntity)
    suspend fun getCategoryInteractions(genreId: Int): Int?
    suspend fun getAllInteractions(): List<GenreUserInteractionEntity>
}