package com.datasource.local.home.datasource

import com.datasource.local.home.dao.MediaDao
import com.repository.home.datasource.local.MediaLocalDataSource
import com.repository.home.entity.MediaEntity

class MediaLocalDataSourceImpl(
    private val mediaDao: MediaDao
) : MediaLocalDataSource {

    override suspend fun addMedia(media: MediaEntity) {
        mediaDao.addMedia(media)
    }

    override suspend fun getAllMedia(): List<MediaEntity> {
        return mediaDao.getAllMedia()
    }
}