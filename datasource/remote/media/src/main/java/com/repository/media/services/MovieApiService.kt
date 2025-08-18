package com.repository.media.services

import com.repository.media.models.remote.movie.MovieCreditsDto
import com.repository.media.models.remote.movie.MovieDto
import com.repository.media.models.remote.movie.MovieImagesDto
import com.repository.media.models.remote.movie.MovieReviewsDto
import com.repository.media.models.remote.movie.MovieSimilarsDto
import com.repository.media.models.remote.movie.MovieVideoDto
import com.repository.media.models.remote.movie.RatingDto
import com.repository.media.models.remote.movie.RatingResponseDto
import com.repository.media.models.remote.movie.RemoveRatingDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
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