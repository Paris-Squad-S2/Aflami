package com.repository.search.service.implementation

import com.repository.media.dto.GenresDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitGenresApiServices {

    @GET("genre/movie/list")
    suspend fun getAllGenresMovie(@Query("language") language: String): GenresDto

    @GET("genre/tv/list")
    suspend fun getAllGenresTvShow(@Query("language") language: String): GenresDto
}