package com.repository.search.dataSource.local

import com.repository.search.entity.GenreUserInteractionEntity

interface GenresInteractionDataSource {
    suspend fun upsertInteraction(interaction: GenreUserInteractionEntity)
    suspend fun getCategoryInteractions(genreId: Int): Int?
    suspend fun getAllInteractions(): List<GenreUserInteractionEntity>
}