package com.domain.search.repository

import com.domain.search.model.Media
import com.domain.search.model.MediaType
import kotlinx.datetime.LocalDate

interface SearchMediaRepository {
    suspend fun getMediaByActor(actorName: String): List<Media>
    suspend fun getMoviesByCountry(countryName: String): List<Media>
    suspend fun getMediaByQuery(query: String): List<Media>
}