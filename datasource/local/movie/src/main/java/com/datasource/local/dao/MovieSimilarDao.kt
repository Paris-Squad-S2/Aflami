package com.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repository.movie.models.local.MovieSimilarEntity

@Dao
interface MovieSimilarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSimilarMovies(movies: List<MovieSimilarEntity>)

    @Query("SELECT * FROM movies_similar_table WHERE id = :movieId AND page = :page AND language = :language")
    suspend fun getSimilarMovies(movieId: Int,page: Int, language: String): List<MovieSimilarEntity>?
}