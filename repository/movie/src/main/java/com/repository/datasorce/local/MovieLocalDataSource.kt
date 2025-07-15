package com.repository.datasorce.local

import com.repository.entity.MovieEntity

interface MovieLocalDataSource {
    suspend fun addMovie(movie: MovieEntity)
    suspend fun getMovie(movieId:Int): MovieEntity
}