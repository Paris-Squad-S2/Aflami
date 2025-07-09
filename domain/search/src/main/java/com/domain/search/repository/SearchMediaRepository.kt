package com.domain.search.repository

import com.domain.search.model.Media

interface SearchMediaRepository {
    suspend fun getMediaByActor(actorName: String): List<Media>
    suspend fun getMoviesByCountry(countryName: String): List<Media>
    suspend fun getMediaByQuery(query: String): List<Media>
}