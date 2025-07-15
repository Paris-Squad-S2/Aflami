package com.example.search.service.implementation

import com.example.search.service.contract.GenresApiServices
import com.repository.search.dto.GenresDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class KtorGenresApiServices(private val httpClient: HttpClient) : GenresApiServices {
    companion object {
        private const val GENRES_ENDPOINT = "genre/movie/list"
        private const val TV_GENRES_ENDPOINT = "genre/tv/list"
    }

    override suspend fun getAllGenres(): GenresDto {
        val movieDto = httpClient.get(GENRES_ENDPOINT).body<GenresDto>()
        val tvDto = httpClient.get(TV_GENRES_ENDPOINT).body<GenresDto>()

        return movieDto.copy(genreDto = movieDto.genreDto.orEmpty() + tvDto.genreDto.orEmpty())
    }

}