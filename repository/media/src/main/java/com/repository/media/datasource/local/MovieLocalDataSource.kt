package com.repository.media.datasource.local

import com.repository.media.models.local.moive.MovieCastEntity
import com.repository.media.models.local.moive.MovieEntity
import com.repository.media.models.local.moive.MovieGalleryEntity
import com.repository.media.models.local.moive.MovieReviewEntity
import com.repository.media.models.local.moive.MovieSimilarEntity

interface MovieLocalDataSource {
    suspend fun addMovie(movie: MovieEntity)
    suspend fun getMovieById(movieId:Int,language: String): MovieEntity?
    suspend fun clearMovieById(movieId: Int, language: String)
    suspend fun addMovieCast(cast: List<MovieCastEntity>)
    suspend fun getCastByMovieId(movieId:Int,language: String): List<MovieCastEntity>
    suspend fun addMovieGallery(gallery: MovieGalleryEntity)
    suspend fun getGalleryByMovieId(movieId: Int): MovieGalleryEntity?
    suspend fun addMovieReviews(reviews: List<MovieReviewEntity>)
    suspend fun getReviewsByMovieId(movieId: Int, language: String): List<MovieReviewEntity>?
    suspend fun addSimilarMovies(movieSimilar: List<MovieSimilarEntity>)
    suspend fun getSimilarMovies(
        movieId: Int,
        page: Int,
        language: String
    ): List<MovieSimilarEntity>
}