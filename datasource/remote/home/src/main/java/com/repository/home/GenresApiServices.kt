package com.repository.home

import com.repository.media.dto.GenresDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GenresApiServices {
    @GET("genre/movie/list")
    suspend fun getMoviesGenres(
        @Query("language")
        language: String
    ): GenresDto
}