package com.repository.media.datasource.local

import com.repository.media.entity.MediaEntity

interface HomeMediaLocalDataSource {
    suspend fun addMedia(media: MediaEntity)
    suspend fun getAllMedia(): List<MediaEntity>
}