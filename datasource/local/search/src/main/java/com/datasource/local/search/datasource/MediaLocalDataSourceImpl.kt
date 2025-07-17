package com.datasource.local.search.datasource

import com.datasource.local.search.dao.MediaDao
import com.repository.search.dataSource.local.MediaLocalDataSource
import com.repository.search.entity.MediaEntity
import com.repository.search.entity.SearchType

class MediaLocalDataSourceImpl(
    private val dao: MediaDao,
) : MediaLocalDataSource {

    override suspend fun addAllMedia(media: List<MediaEntity>) =
        dao.addAllMedia(media)

    override suspend fun getAllMedia(): List<MediaEntity> = dao.getAllMedia()

    override suspend fun getMediaByCountry(country: String,page: Int,language: String): List<MediaEntity> =
        dao.getMediaByCountry(country,page,language)

    override suspend fun getMediaByActor(actor: String,page:Int,language: String): List<MediaEntity> =
        dao.getMediaByActor(actor,page,language)

    override suspend fun getMediaByTitleQuery(query: String,page: Int,language: String): List<MediaEntity> =
        dao.getMediaByTitleQuery(query, page,language)

    override suspend fun getCachedMedia(): List<MediaEntity> =
        dao.getCachedMedia()

    override suspend fun clearAllMediaBySearchQuery(searchQuery: String, searchType: SearchType) {
        dao.clearAllMediaBySearchQuery(searchQuery, searchType)
    }

}