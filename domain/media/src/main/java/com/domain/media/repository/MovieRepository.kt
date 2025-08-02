package com.domain.media.repository

import com.domain.media.entity.Cast
import com.domain.media.entity.Image
import com.domain.media.entity.Movie
import com.domain.media.entity.MovieSimilar
import com.domain.media.entity.MovieVideo
import com.domain.media.entity.ProductionCompany
import com.domain.media.entity.Review

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