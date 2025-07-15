package com.datasource.remote.mediadetails

import com.datasource.remote.mediadetails.models.movie.MovieCreditsDto
import com.datasource.remote.mediadetails.models.movie.MovieDto
import com.datasource.remote.mediadetails.models.movie.MovieImagesDto
import com.datasource.remote.mediadetails.models.movie.MovieReviewsDto
import com.datasource.remote.mediadetails.models.movie.MovieSimilarsDto

interface MovieDetailsRemoteDataSource {
    suspend fun getMovieDetails(movieId: Int, language: String): MovieDto
    suspend fun getMovieImages(movieId: Int): MovieImagesDto
    suspend fun getMovieReviews(movieId: Int, page: Int, language: String): MovieReviewsDto
    suspend fun getSimilarMovies(movieId: Int, page: Int, language: String): MovieSimilarsDto
    suspend fun getMovieCredits(movieId: Int, language: String): MovieCreditsDto
}

