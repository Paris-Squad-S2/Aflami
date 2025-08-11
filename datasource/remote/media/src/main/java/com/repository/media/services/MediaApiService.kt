package com.repository.media.services

import com.repository.media.dto.category.MovieByCategoryDto
import com.repository.media.dto.home.MovieListDto
import com.repository.media.dto.home.TvListDto
import com.repository.media.dto.profile.RatedMoviesDto
import com.repository.media.dto.profile.RatedTvShowDtoo
import com.repository.media.dto.search.SearchDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MediaApiService {

    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("language")
        language: String
    ): MovieListDto

    @GET("movie/top_rated")
    suspend fun getTopRatedMovie(
        @Query("language")
        language: String
    ): MovieListDto

    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Query("language")
        language: String
    ): MovieListDto

    @GET("movie/now_playing")
    suspend fun getNowPlaying(): MovieListDto

    @GET("tv/top_rated")
    suspend fun getTopRatedTv(
        @Query("language")
        language: String
    ): TvListDto

    @GET("tv/popular")
    suspend fun getPopularTv(
        @Query("language")
        language: String
    ): TvListDto

    @GET("account/{account_id}/rated/movies")
    suspend fun getRatedMovies(
        @Path("account_id")
        accountId: Int,
        @Query("language")
        language: String
    ): RatedMoviesDto

    @GET("account/{account_id}/rated/tv")
    suspend fun getRatedTvShows(
        @Path("account_id")
        accountId: Int,
        @Query("language")
        language: String
    ): RatedTvShowDtoo

    @GET("discover/movie")
    suspend fun getMoviesByCategory(
        @Query("with_genres") genreId: Int,
        @Query("language") language: String,
        @Query("page") page: Int = 1
    ): MovieByCategoryDto

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

