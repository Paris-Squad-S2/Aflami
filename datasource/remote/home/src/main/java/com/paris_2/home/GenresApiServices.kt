package com.paris_2.home

import com.paris_2.home.dto.GenresDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GenresApiServices {
    @GET("genre/movie/list")
    suspend fun getAllGenres(
        @Query("language")
        language: String
    ): GenresDto
}