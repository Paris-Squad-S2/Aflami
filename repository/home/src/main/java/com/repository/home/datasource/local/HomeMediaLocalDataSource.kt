package com.repository.home.datasource.local

import com.repository.home.entity.MediaEntity

interface HomeMediaLocalDataSource {
    suspend fun addMedia(media: MediaEntity)
    suspend fun getAllMedia(): List<MediaEntity>
}