package com.datasource.local.search.datasource

import com.datasource.local.search.dao.MediaDao
import com.repository.media.datasource.local.MediaLocalDataSource
import com.repository.media.entity.MediaSearchEntity
import com.repository.media.entity.SearchType

class MediaLocalDataSourceImpl (
    private val dao: MediaDao,
) : MediaLocalDataSource {

    override suspend fun addAllMedia(media: List<MediaSearchEntity>) =
        dao.addAllMedia(media)

    override suspend fun getAllMedia(): List<MediaSearchEntity> = dao.getAllMedia()

    override suspend fun getMediaByCountry(country: String,page: Int,language: String): List<MediaSearchEntity> =
        dao.getMediaByCountry(country,page,language)

    override suspend fun getMediaByActor(actor: String,page:Int,language: String): List<MediaSearchEntity> =
        dao.getMediaByActor(actor,page,language)

    override suspend fun getMediaByTitleQuery(query: String,page: Int,language: String): List<MediaSearchEntity> =
        dao.getMediaByTitleQuery(query, page,language)

    override suspend fun getCachedMedia(): List<MediaSearchEntity> =
        dao.getCachedMedia()

    override suspend fun clearAllMediaBySearchQuery(searchQuery: String, searchType: SearchType) {
        dao.clearAllMediaBySearchQuery(searchQuery, searchType)
    }

}