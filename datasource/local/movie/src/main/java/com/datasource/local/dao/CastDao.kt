package com.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repository.entity.CastEntity

@Dao
interface CastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCast(casts: List<CastEntity>)

    @Query("SELECT * FROM cast_table WHERE movieId = :movieId")
    suspend fun getCastByMovieId(movieId: Int): List<CastEntity>
}