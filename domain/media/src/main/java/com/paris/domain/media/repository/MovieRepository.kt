package com.paris.domain.media.repository

import com.paris.domain.media.entity.Cast
import com.paris.domain.media.entity.Image
import com.paris.domain.media.entity.MediaVideo
import com.paris.domain.media.entity.Movie
import com.paris.domain.media.entity.MovieSimilar
import com.paris.domain.media.entity.ProductionCompany
import com.paris.domain.media.entity.Review

interface MovieRepository {
    suspend fun getMovieDetails(movieId: Int): Movie
    suspend fun getMovieCast(movieId: Int): List<Cast>
    suspend fun getMovieRecommendations(movieId: Int,page: Int): List<MovieSimilar>
    suspend fun getMovieGallery(movieId: Int): List<Image>
    suspend fun getCompanyProducts(movieId: Int): List<ProductionCompany>
    suspend fun getMovieReview(movieId: Int,page: Int): List<Review>
    suspend fun getTrailerVideoForMovie(movieId: Int): List<MediaVideo>
    suspend fun addRatingToMovie(movieId: Int, rating: Float)
    suspend fun deleteMovieRating(movieId: Int)
}