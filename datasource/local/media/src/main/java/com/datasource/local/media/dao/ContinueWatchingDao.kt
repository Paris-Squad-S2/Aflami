package com.datasource.local.media.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repository.media.entity.MediaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContinueWatchingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMediaContinueWatching(media: MediaEntity)

    @Query("SELECT * FROM media_table")
    fun getMediaContinueWatching(): Flow<List<MediaEntity>>
}