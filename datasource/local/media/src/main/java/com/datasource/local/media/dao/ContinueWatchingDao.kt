package com.datasource.local.media.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repository.media.entity.MediaEntity

@Dao
interface ContinueWatchingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMediaContinueWatching(media: MediaEntity)

    @Query("SELECT * FROM media_table")
    suspend fun getMediaContinueWatching(): List<MediaEntity>
}