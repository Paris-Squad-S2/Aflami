package com.repository.movie.dataSource.local

import com.repository.movie.models.local.MovieEntity

interface MovieLocalDataSource {
    suspend fun addMovie(movie: MovieEntity)
    suspend fun getMovieById(movieId:Int): MovieEntity?
}