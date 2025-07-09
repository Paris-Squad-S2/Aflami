package com.repository.search.dataSource

import com.repository.search.entity.MediaEntity

interface MediaLocalDataSource {
    suspend fun addAllMedia(media: List<MediaEntity>)
    suspend fun getAllMedia(): List<MediaEntity>
    suspend fun getMediaByCountry(country: String): List<MediaEntity>
    suspend fun getMediaByActor(actor: String): List<MediaEntity>
    suspend fun getMediaByTitleQuery(query: String) : List<MediaEntity>
}