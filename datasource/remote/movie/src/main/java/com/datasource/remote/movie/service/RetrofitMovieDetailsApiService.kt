package com.datasource.remote.movie.service

import com.repository.movie.models.remote.MovieCreditsDto
import com.repository.movie.models.remote.MovieDto
import com.repository.movie.models.remote.MovieImagesDto
import com.repository.movie.models.remote.MovieReviewsDto
import com.repository.movie.models.remote.MovieSimilarsDto
import com.repository.movie.models.remote.MovieVideoDto
import com.repository.movie.models.remote.RatingDto
import com.repository.movie.models.remote.RatingResponseDto
import com.repository.movie.models.remote.RemoveRatingDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitMovieDetailsApiService {
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String
    ): MovieDto

    @GET("movie/{movie_id}/images")
    suspend fun getMovieImages(@Path("movie_id") movieId: Int): MovieImagesDto

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int,
        @Query("language") language: String
    ): MovieReviewsDto

    @GET("movie/{movie_id}/recommendations")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int,
        @Query("language") language: String
    ): MovieSimilarsDto

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String
    ): MovieCreditsDto

    @GET("movie/{movie_id}/videos")
    suspend fun getTrailerVideoForMovie(
        @Path("movie_id") movieId: Int
    ): MovieVideoDto

    @POST("movie/{movie_id}/rating")
    suspend fun addRatingToMovie(
        @Path("movie_id") movieId: Int,
        @Body rating: RatingDto
    ): RatingResponseDto

    @DELETE("movie/{movie_id}/rating")
    suspend fun deleteMovieRating(
        @Path("movie_id") movieId: Int
    ): RemoveRatingDto
}