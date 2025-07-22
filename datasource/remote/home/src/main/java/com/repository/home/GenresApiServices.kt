package com.repository.home

import com.repository.home.dto.GenresDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GenresApiServices {
    @GET("genre/movie/list")
    suspend fun getAllGenres(
        @Query("language")
        language: String
    ): GenresDto
}