package com.domain.search.repository

import com.domain.search.model.Media

interface MovieRepository {
    suspend fun getMoviesByCountry(countryName: String): List<Media>
    suspend fun getMoviesByActor(actorName: String): List<Media>
    suspend fun getAllMovies(): List<Media>
}