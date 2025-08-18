package com.repository.media.datasource.local

import com.repository.media.entity.Category
import com.repository.media.entity.HomeMediaEntity
import com.repository.media.entity.MediaEntity
import kotlinx.coroutines.flow.Flow

interface MediaLocalDataSource {
    suspend fun addHomeMedia(mediaList: List<HomeMediaEntity>)
    suspend fun getHomeMediaByCategory(category: Category, language: String): List<HomeMediaEntity>
    suspend fun clearHomeMediaByCategory(category: Category)
    suspend fun addMediaContinueWatching(media: MediaEntity)
    fun getMediaContinueWatching(): Flow<List<MediaEntity>>
}