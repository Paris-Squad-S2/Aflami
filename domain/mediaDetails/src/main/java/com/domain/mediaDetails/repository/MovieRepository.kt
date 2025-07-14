package com.domain.mediaDetails.repository

import com.domain.mediaDetails.model.Cast
import com.domain.mediaDetails.model.Gallery
import com.domain.mediaDetails.model.Movie
import com.domain.mediaDetails.model.ProductionCompany
import com.domain.mediaDetails.model.Review
import com.domain.mediaDetails.model.Season

interface MovieRepository {
    suspend fun getMovieDetails(movieId: Int): Movie
    suspend fun getMovieCast(movieId: Int): List<Cast>
    suspend fun getMovieRecommendations(movieId: Int): List<Movie>
    suspend fun getMovieGallery(movieId: Int): Gallery
    suspend fun getCompanyProducts(movieId: Int): List<ProductionCompany>
    suspend fun getMovieReview(movieId: Int): List<Review>
    suspend fun addMovieToFavorite(movieId: Int)
}