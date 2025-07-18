package com.repository.movie.dataSource.local

import com.repository.movie.models.local.MovieSimilarEntity

interface MovieSimilarLocalDataSource {
    suspend fun addSimilarMovies(movieSimilar: List<MovieSimilarEntity>)
    suspend fun getSimilarMovies(
        movieId: Int,
        page: Int,
        language: String
    ): List<MovieSimilarEntity>?
}