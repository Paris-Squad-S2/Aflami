package com.repository.search.service.implementation

import com.repository.search.dto.SearchDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitSearchApiService {

    @GET("search/multi")
    suspend fun searchMulti(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("language") language: String
    ): SearchDto

    @GET("search/person")
    suspend fun searchPerson(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("language") language: String
    ): SearchDto

    @GET("discover/movie")
    suspend fun searchCountryCode(
        @Query("page") page: Int,
        @Query("language") language: String,
        @Query("with_origin_country") countryCode: String,
    ): SearchDto

}