package com.repository.media.datasource.local

import com.repository.media.entity.MediaSearchEntity
import com.repository.media.entity.SearchType


interface MediaLocalDataSource {
    suspend fun addAllMedia(media: List<MediaSearchEntity>)
    suspend fun getAllMedia(): List<MediaSearchEntity>
    suspend fun getMediaByCountry(country: String,page:Int, language: String): List<MediaSearchEntity>
    suspend fun getMediaByActor(actor: String,page:Int, language: String): List<MediaSearchEntity>
    suspend fun getMediaByTitleQuery(query: String,page: Int, language: String) : List<MediaSearchEntity>
    suspend fun getCachedMedia(): List<MediaSearchEntity>
    suspend fun clearAllMediaBySearchQuery(searchQuery: String, searchType: SearchType)
}
