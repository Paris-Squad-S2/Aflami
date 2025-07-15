package com.datasource.remote.mediadetails.service

import com.example.movie.models.remote.MovieCreditsDto
import com.example.movie.models.remote.MovieDto
import com.example.movie.models.remote.MovieImagesDto
import com.example.movie.models.remote.MovieReviewsDto
import com.example.movie.models.remote.MovieSimilarsDto


interface KtorMovieDetailsApiService {
    suspend fun getMovieDetails(movieId: Int, language: String): MovieDto
    suspend fun getMovieImages(movieId: Int): MovieImagesDto
    suspend fun getMovieReviews(movieId: Int, page: Int, language: String): MovieReviewsDto
    suspend fun getSimilarMovies(movieId: Int, page: Int, language: String): MovieSimilarsDto
    suspend fun getMovieCredits(movieId: Int, language: String): MovieCreditsDto
}

