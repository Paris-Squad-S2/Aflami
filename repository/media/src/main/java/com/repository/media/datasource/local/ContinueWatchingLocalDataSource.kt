package com.repository.media.datasource.local

import com.repository.media.entity.MediaEntity
import kotlinx.coroutines.flow.Flow

interface ContinueWatchingLocalDataSource {
    suspend fun addMedia(media: MediaEntity)
    fun getAllMedia(): Flow<List<MediaEntity>>
}