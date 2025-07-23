package com.datasource.local.home.datasource

import com.datasource.local.home.dao.MediaDao
import com.repository.home.datasource.local.HomeMediaLocalDataSource
import com.repository.home.entity.MediaEntity

class HomeMediaLocalDataSourceImpl(
    private val mediaDao: MediaDao
) : HomeMediaLocalDataSource {

    override suspend fun addMedia(media: MediaEntity) {
        mediaDao.addMedia(media)
    }

    override suspend fun getAllMedia(): List<MediaEntity> {
        return mediaDao.getAllMedia()
    }
}