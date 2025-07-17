package com.datasource.local.datasource

import com.datasource.local.dao.MovieDao
import com.repository.datasource.local.MovieLocalDataSource
import com.repository.entity.MovieEntity

class MovieLocalDataSourceImp(private val dao: MovieDao) : MovieLocalDataSource {
    override suspend fun addMovie(movie: MovieEntity) = dao.addMovies(movie)

    override suspend fun getMovieById(movieId: Int): MovieEntity? = dao.getMovieById(movieId)

}