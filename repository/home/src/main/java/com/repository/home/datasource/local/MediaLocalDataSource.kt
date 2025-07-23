package com.repository.home.datasource.local

import com.repository.home.entity.MediaEntity

interface MediaLocalDataSource {
    suspend fun addMedia(media: MediaEntity)
    suspend fun getAllMedia(): List<MediaEntity>
}