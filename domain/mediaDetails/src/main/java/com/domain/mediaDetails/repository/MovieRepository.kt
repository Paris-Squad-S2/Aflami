package com.domain.mediaDetails.repository

import com.domain.mediaDetails.entity.Cast
import com.domain.mediaDetails.entity.Image
import com.domain.mediaDetails.entity.Movie
import com.domain.mediaDetails.entity.MovieSimilar
import com.domain.mediaDetails.entity.MovieVideo
import com.domain.mediaDetails.entity.ProductionCompany
import com.domain.mediaDetails.entity.Review

interface MovieRepository {
    suspend fun getMovieDetails(movieId: Int): Movie
    suspend fun getMovieCast(movieId: Int): List<Cast>
    suspend fun getMovieRecommendations(movieId: Int,page: Int): List<MovieSimilar>
    suspend fun getMovieGallery(movieId: Int): List<Image>
    suspend fun getCompanyProducts(movieId: Int): List<ProductionCompany>
    suspend fun getMovieReview(movieId: Int,page: Int): List<Review>
    suspend fun getTrailerVideoForMovie(movieId: Int): List<MovieVideo>
    suspend fun addRatingToMovie(movieId: Int, rating: Float)
}