package com.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repository.model.local.CastEntity

@Dao
interface TvShowCastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCast(casts: List<CastEntity>)

    @Query("SELECT * FROM cast_tv_shows_table WHERE tvShowId = :tvShowId")
    suspend fun getCastByTvShowId(tvShowId: Int): List<CastEntity>
}