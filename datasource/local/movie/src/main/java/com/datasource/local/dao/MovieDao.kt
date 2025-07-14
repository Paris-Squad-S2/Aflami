package com.datasource.local.dao

import com.repository.entity.MovieEntity

interface MovieDao {
    suspend fun addMovies(movies: List<MovieEntity>)
    suspend fun getMovies(): List<MovieEntity>
}