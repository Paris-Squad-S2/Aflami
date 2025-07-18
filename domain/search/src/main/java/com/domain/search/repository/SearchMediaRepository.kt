package com.domain.search.repository

import com.domain.search.model.Media

interface SearchMediaRepository {
    suspend fun getMediaByActor(actorName: String,page:Int): List<Media>
    suspend fun getMoviesByCountry(countryName: String,page: Int): List<Media>
    suspend fun getMediaByQuery(query: String,page: Int): List<Media>
}