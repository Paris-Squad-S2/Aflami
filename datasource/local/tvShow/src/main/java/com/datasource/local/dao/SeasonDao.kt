package com.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repository.model.local.SeasonEntity

@Dao
interface SeasonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSeason(season: SeasonEntity)

    @Query("SELECT * FROM seasons_table WHERE tvShowId = :tvShowId AND seasonNumber = :seasonNumber LIMIT 1")
    suspend fun getSeasonByTvShowIdAndSeasonNumber(tvShowId: Int, seasonNumber: Int): SeasonEntity?
}