package com.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.repository.entity.CastEntity

@Dao
interface CastDao {
    @Insert
    suspend fun addCast(genres: List<CastEntity>)

    @Query("SELECT * FROM Cast_Table")
    suspend fun getCast(): List<CastEntity>
}