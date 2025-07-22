package com.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repository.model.local.TvShowSimilarEntity

@Dao
interface TvShowSimilarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSimilarTvShows(tvShows: List<TvShowSimilarEntity>)

    @Query("SELECT * FROM tv_shows_similar_table WHERE id = :tvShowId AND page = :page AND language = :language")
    suspend fun getSimilarTvShows(tvShowId: Int,page: Int, language: String): List<TvShowSimilarEntity>
}