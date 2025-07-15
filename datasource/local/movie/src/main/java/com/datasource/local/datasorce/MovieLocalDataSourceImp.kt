package com.datasource.local.datasorce

import com.datasource.local.dao.MovieDao
import com.repository.datasorce.local.MovieLocalDataSource
import com.repository.entity.MovieEntity

class MovieLocalDataSourceImp(private val dao: MovieDao) : MovieLocalDataSource {
    override suspend fun addMovie(movie: MovieEntity) = dao.addMovies(movie)

    override suspend fun getMovie(movieId: Int): MovieEntity = dao.getMovies(movieId)

}