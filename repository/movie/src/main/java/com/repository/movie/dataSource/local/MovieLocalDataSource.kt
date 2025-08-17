package com.repository.movie.dataSource.local

import com.repository.movie.models.local.MovieCastEntity
import com.repository.movie.models.local.MovieGalleryEntity
import com.repository.movie.models.local.MovieEntity
import com.repository.movie.models.local.MovieSimilarEntity
import com.repository.movie.models.local.MovieReviewEntity

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