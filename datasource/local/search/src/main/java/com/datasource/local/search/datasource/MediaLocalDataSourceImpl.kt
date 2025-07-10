package com.datasource.local.search.datasource

import com.datasource.local.search.dao.MediaDao
import com.repository.search.dataSource.local.MediaLocalDataSource
import com.repository.search.entity.MediaEntity

class MediaLocalDataSourceImpl(
    private val dao: MediaDao,
) : MediaLocalDataSource {

    override suspend fun addAllMedia(media: List<MediaEntity>) =
        dao.addAllMedia(media)

    override suspend fun getAllMedia(): List<MediaEntity> = dao.getAllMedia()

    override suspend fun getMediaByCountry(country: String): List<MediaEntity> =
        dao.getMediaByCountry(country)

    override suspend fun getMediaByActor(actor: String): List<MediaEntity> =
        dao.getMediaByActor(actor)

    override suspend fun getMediaByTitleQuery(query: String): List<MediaEntity> =
        dao.getMediaByTitleQuery(query)

    override suspend fun getCachedMedia(): List<MediaEntity> =
        dao.getCachedMedia()

    override suspend fun clearAllMediaBySearchQuery(searchQuery: String) {
        dao.clearAllMediaBySearchQuery(searchQuery)
    }

}