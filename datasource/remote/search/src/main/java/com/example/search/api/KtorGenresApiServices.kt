package com.example.search.api

import com.example.search.models.GenreDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class KtorGenresApiServices(
    private val httpClient: HttpClient,
    private val baseUrl: String,
) : GenresApiServices {
    companion object {
        private const val GENRES_ENDPOINT = "genre/movie/list"
    }

    override suspend fun getAllGenres(): GenreDto {
        return httpClient.get("$baseUrl/$GENRES_ENDPOINT").body()
    }

}