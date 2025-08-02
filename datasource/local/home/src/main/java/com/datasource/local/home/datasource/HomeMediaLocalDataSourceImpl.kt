package com.datasource.local.home.datasource

import com.datasource.local.home.dao.HomeMediaDao
import com.repository.media.datasource.local.HomeMediaLocalDataSource
import com.repository.media.entity.MediaEntity

class HomeMediaLocalDataSourceImpl (
    private val mediaDao: HomeMediaDao
) : HomeMediaLocalDataSource {

    override suspend fun addMedia(media: MediaEntity) {
        mediaDao.addMedia(media)
    }

    override suspend fun getAllMedia(): List<MediaEntity> {
        return mediaDao.getAllMedia()
    }
}