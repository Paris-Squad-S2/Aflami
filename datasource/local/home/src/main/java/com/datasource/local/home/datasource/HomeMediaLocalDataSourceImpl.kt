package com.datasource.local.home.datasource

import com.datasource.local.home.dao.HomeMediaDao
import com.repository.home.datasource.local.HomeMediaLocalDataSource
import com.repository.home.entity.MediaEntity
import javax.inject.Inject

class HomeMediaLocalDataSourceImpl @Inject constructor(
    private val mediaDao: HomeMediaDao
) : HomeMediaLocalDataSource {

    override suspend fun addMedia(media: MediaEntity) {
        mediaDao.addMedia(media)
    }

    override suspend fun getAllMedia(): List<MediaEntity> {
        return mediaDao.getAllMedia()
    }
}