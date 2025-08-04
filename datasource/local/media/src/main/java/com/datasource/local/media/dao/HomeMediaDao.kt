package com.datasource.local.media.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repository.media.entity.MediaEntity

@Dao
interface HomeMediaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMedia(media: MediaEntity)

    @Query("SELECT * FROM media_table")
    suspend fun getAllMedia(): List<MediaEntity>
}