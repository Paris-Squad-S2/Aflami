package com.repository.home

import com.repository.home.dto.MediaListDto
import retrofit2.http.GET

interface MediaApiService {

    @GET("movie/popular")
    suspend fun getPopularMovie(): MediaListDto

    @GET("movie/top_rated")
    suspend fun getTopRatedMovie(): MediaListDto

    @GET("movie/upcoming")
    suspend fun getUpcoming(): MediaListDto

    @GET("movie/now_playing")
    suspend fun getNowPlaying(): MediaListDto

    @GET("tv/top_rated")
    suspend fun getTopRatedTv(): MediaListDto

    @GET("tv/popular")
    suspend fun getPopularTv(): MediaListDto

}

