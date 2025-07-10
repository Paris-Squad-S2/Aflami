package com.example.search.service.implementation

import com.example.search.service.contract.GenresApiServices
import com.repository.search.dto.GenresDto
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

    override suspend fun getAllGenres(): GenresDto {
        return httpClient.get("$baseUrl/$GENRES_ENDPOINT").body()
    }

}