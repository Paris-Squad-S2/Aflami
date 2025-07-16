package com.example.movie.dataSource.local

import com.example.movie.models.local.MovieEntity

interface MovieLocalDataSource {
    suspend fun addMovie(movie: MovieEntity)
    suspend fun getMovie(movieId:Int): MovieEntity?
}