package com.repository.search.dataSource.local

import com.repository.search.entity.MediaEntity
import com.repository.search.entity.SearchType

interface MediaLocalDataSource {
    suspend fun addAllMedia(media: List<MediaEntity>)
    suspend fun getAllMedia(): List<MediaEntity>
    suspend fun getMediaByCountry(country: String,page:Int): List<MediaEntity>
    suspend fun getMediaByActor(actor: String,page:Int): List<MediaEntity>
    suspend fun getMediaByTitleQuery(query: String,page: Int) : List<MediaEntity>
    suspend fun getCachedMedia(): List<MediaEntity>
    suspend fun clearAllMediaBySearchQuery(searchQuery: String, searchType: SearchType)
}