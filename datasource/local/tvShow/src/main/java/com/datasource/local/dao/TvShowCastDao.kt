package com.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repository.model.local.CastEntity
import com.repository.model.local.TvShowSimilarEntity

@Dao
interface TvShowCastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCast(casts: List<CastEntity>)

    @Query("SELECT * FROM cast_tv_shows_table WHERE tvShowId = :tvShowId AND language = :language")
    suspend fun getCastByTvShowId(tvShowId: Int,language: String): List<CastEntity>
}