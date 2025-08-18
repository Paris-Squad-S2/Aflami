package com.repository.media.datasource.local

import com.repository.media.models.local.media.Category
import com.repository.media.models.local.media.HomeMediaEntity
import com.repository.media.models.local.media.MediaEntity
import kotlinx.coroutines.flow.Flow

interface MediaLocalDataSource {
    suspend fun addHomeMedia(mediaList: List<HomeMediaEntity>)
    suspend fun getHomeMediaByCategory(category: Category, language: String): List<HomeMediaEntity>
    suspend fun clearHomeMediaByCategory(category: Category)
    suspend fun addMediaContinueWatching(media: MediaEntity)
    fun getMediaContinueWatching(): Flow<List<MediaEntity>>
}