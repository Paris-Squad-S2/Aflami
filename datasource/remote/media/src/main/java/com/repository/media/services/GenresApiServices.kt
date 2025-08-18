package com.repository.media.services

import com.repository.media.models.remote.media.GenresDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GenresApiServices {
    @GET("genre/movie/list")
    suspend fun getMoviesGenres(
        @Query("language")
        language: String,
    ): GenresDto
}