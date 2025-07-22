package com.repository.home

import com.repository.home.dto.MovieListDto
import com.repository.home.dto.TvListDto
import retrofit2.http.GET

interface MediaApiService {

    @GET("movie/popular")
    suspend fun getPopularMovie(): MovieListDto

    @GET("movie/top_rated")
    suspend fun getTopRatedMovie(): MovieListDto

    @GET("movie/upcoming")
    suspend fun getUpcoming(): MovieListDto

    @GET("movie/now_playing")
    suspend fun getNowPlaying(): MovieListDto

    @GET("tv/top_rated")
    suspend fun getTopRatedTv(): TvListDto

    @GET("tv/popular")
    suspend fun getPopularTv(): TvListDto

}

