package com.repository.movie.dataSource.remote

import com.repository.movie.models.remote.MovieCreditsDto
import com.repository.movie.models.remote.MovieDto
import com.repository.movie.models.remote.MovieImagesDto
import com.repository.movie.models.remote.MovieReviewsDto
import com.repository.movie.models.remote.MovieSimilarsDto
import com.repository.movie.models.remote.MovieVideoDto

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