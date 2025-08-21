package com.repository.media.services

import com.repository.guessgame.dto.ActorPopularityListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ActorPopularityApiService{
    @GET("person/popular")
    suspend fun getPopularActors(
        @Query("page") page: Int,
        @Query("language") language: String = "en-US",
        @Query("adult") isAdult: Boolean = false
    ) : ActorPopularityListDto
}