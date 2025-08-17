package com.datasource.local.media.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.repository.media.entity.GenreUserInteractionEntity

@Dao
interface GenresUserInteractionDao {

    @Upsert
    suspend fun upsertGenresInteraction(interaction: GenreUserInteractionEntity)

    @Query("SELECT interactionCount FROM genres_user_interaction WHERE genreId = :genreId")
    suspend fun getCategoryByGenreId(genreId: Int): Int?

    @Query("SELECT * FROM genres_user_interaction")
    suspend fun getGenresInteractions(): List<GenreUserInteractionEntity>
}

