package com.datasource.local.media.datasource

import com.datasource.local.media.dao.ContinueWatchingDao
import com.repository.media.datasource.local.ContinueWatchingLocalDataSource
import com.repository.media.entity.MediaEntity

class ContinueWatchingLocalDataSourceImpl (
    private val continueWatchingDao: ContinueWatchingDao
) : ContinueWatchingLocalDataSource {

    override suspend fun addMedia(media: MediaEntity) {
        continueWatchingDao.addMediaContinueWatching(media)
    }

    override suspend fun getAllMedia(): List<MediaEntity> {
        return continueWatchingDao.getMediaContinueWatching()
    }
}