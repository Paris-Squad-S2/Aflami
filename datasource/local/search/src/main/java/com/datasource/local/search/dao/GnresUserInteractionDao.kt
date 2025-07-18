package com.datasource.local.search.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.repository.search.entity.GenreUserInteractionEntity

@Dao
interface GenresUserInteractionDao {

    @Upsert
    suspend fun upsertInteraction(interaction: GenreUserInteractionEntity)

    @Query("SELECT interactionCount FROM genres_user_interaction WHERE genreId = :genreId")
    suspend fun getCategoryInteractions(genreId: Int): Int?

    @Query("SELECT * FROM genres_user_interaction")
    suspend fun getAllInteractions(): List<GenreUserInteractionEntity>
}

