package com.datasource.remote.movie

import com.datasource.remote.movie.model.MovieCreditsDto
import com.datasource.remote.movie.model.MovieDto
import com.datasource.remote.movie.model.MovieImagesDto
import com.datasource.remote.movie.model.MovieReviewsDto
import com.datasource.remote.movie.model.MovieSimilarsDto

interface MovieDetailsRemoteDataSource {
    suspend fun getMovieDetails(movieId: Int, language: String): MovieDto
    suspend fun getMovieImages(movieId: Int): MovieImagesDto
    suspend fun getMovieReviews(movieId: Int, page: Int, language: String): MovieReviewsDto
    suspend fun getSimilarMovies(movieId: Int, page: Int, language: String): MovieSimilarsDto
    suspend fun getMovieCredits(movieId: Int, language: String): MovieCreditsDto
}

