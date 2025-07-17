package com.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repository.entity.TvShowEntity

@Dao
interface TvShowDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTvShow(tvShow: TvShowEntity)

    @Query("SELECT * FROM tv_shows_table WHERE id = :tvShowId")
    suspend fun getTvShowById(tvShowId: Int): TvShowEntity?

}