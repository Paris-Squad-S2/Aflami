package com.repository.datasource.local

import com.repository.entity.MovieEntity

interface MovieLocalDataSource {
    suspend fun addMovie(movie: MovieEntity)
    suspend fun getMovieById(movieId:Int): MovieEntity?
}