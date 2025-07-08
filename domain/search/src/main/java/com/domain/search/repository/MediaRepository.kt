package com.domain.search.repository

import com.domain.search.model.Media

interface MediaRepository {
    suspend fun getAllMedia(): List<Media>
    suspend fun getMediaByCountryName(countryName: String): List<Media>
    suspend fun getMediaByActor(actorName: String): List<Media>
}