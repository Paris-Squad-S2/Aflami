package com.repository.media.datasource.remote

import com.repository.media.models.remote.movie.MovieCreditsDto
import com.repository.media.models.remote.movie.MovieDto
import com.repository.media.models.remote.movie.MovieImagesDto
import com.repository.media.models.remote.movie.MovieReviewsDto
import com.repository.media.models.remote.movie.MovieSimilarsDto
import com.repository.media.models.remote.movie.MovieVideoDto

interface MovieRemoteDataSource {
    suspend fun getMovieDetails(movieId: Int, language: String): MovieDto
    suspend fun getMovieImages(movieId: Int): MovieImagesDto
    suspend fun getMovieReviews(movieId: Int, page: Int, language: String): MovieReviewsDto
    suspend fun getSimilarMovies(movieId: Int, page: Int, language: String): MovieSimilarsDto
    suspend fun getMovieCredits(movieId: Int, language: String): MovieCreditsDto
    suspend fun getTrailerVideoForMovie(movieId: Int): MovieVideoDto
    suspend fun addRatingToMovie(movieId: Int, rating: Float): Boolean
    suspend fun deleteMovieRating(movieId: Int): Boolean
}