package com.datasource.local.media.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repository.media.models.local.media.Category
import com.repository.media.models.local.media.HomeMediaEntity
import com.repository.media.models.local.media.MediaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MediaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMediaContinueWatching(media: MediaEntity)

    @Query("SELECT * FROM media_table")
    fun getMediaContinueWatching(): Flow<List<MediaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHomeMedia(media: List<HomeMediaEntity>)

    @Query("SELECT * FROM home_media_table WHERE category = :category AND language = :language")
    suspend fun getHomeMediaByCategory(category: Category, language: String): List<HomeMediaEntity>

    @Query("DELETE FROM home_media_table WHERE category = :category")
    suspend fun clearHomeMediaByCategory(category: Category)
}