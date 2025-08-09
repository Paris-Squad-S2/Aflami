package com.repository.media.datasource.local

import com.repository.media.entity.GenreUserInteractionEntity


interface GenresInteractionDataSource {
    suspend fun upsertGenresInteraction(interaction: GenreUserInteractionEntity)
    suspend fun getCategoryByGenreId(genreId: Int): Int?
    suspend fun getGenresInteractions(): List<GenreUserInteractionEntity>
}