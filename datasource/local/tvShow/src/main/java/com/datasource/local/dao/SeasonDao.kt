package com.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repository.entity.SeasonEntity

@Dao
interface SeasonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSeason(season: List<SeasonEntity>)

    @Query("SELECT * FROM seasons_table WHERE tvShowId = :tvShowId")
    suspend fun getSeasonByTvShowId(tvShowId: Int): List<SeasonEntity>?

}