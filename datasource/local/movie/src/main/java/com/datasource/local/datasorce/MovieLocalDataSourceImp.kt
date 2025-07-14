package com.datasource.local.datasorce

import com.datasource.local.dao.MovieDao
import com.repository.datasorce.local.MovieLocalDataSource
import com.repository.entity.MovieEntity

class MovieLocalDataSourceImp(private val dao: MovieDao) : MovieLocalDataSource {
    override suspend fun addMovies(movies: List<MovieEntity>) = dao.addMovies(movies)

    override suspend fun getMovies(): List<MovieEntity> = dao.getMovies()

}