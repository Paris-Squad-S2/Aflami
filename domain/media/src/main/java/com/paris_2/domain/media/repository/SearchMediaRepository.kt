package com.paris_2.domain.media.repository

import com.paris_2.domain.media.entity.Media

interface SearchMediaRepository {
    suspend fun getMediaByActor(actorName: String,page:Int): List<Media>
    suspend fun getMoviesByCountry(countryName: String,page: Int): List<Media>
    suspend fun getMediaByQuery(query: String,page: Int): List<Media>
}