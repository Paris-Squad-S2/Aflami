package com.datasource.local.media.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repository.media.entity.GenreEntity

@Dao
interface GenresDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGenres(genres: List<GenreEntity>)

    @Query("SELECT * FROM Genres_table WHERE language = :language")
    suspend fun getGenres(language: String): List<GenreEntity>

}