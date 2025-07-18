package com.datasource.local.datasource

import com.datasource.local.dao.MovieDao
import com.repository.movie.dataSource.local.MovieLocalDataSource
import com.repository.movie.models.local.MovieEntity

class MovieLocalDataSourceImpl(private val dao: MovieDao) : MovieLocalDataSource {
    override suspend fun addMovie(movie: MovieEntity) = dao.addMovies(movie)

    override suspend fun getMovie(movieId: Int,language: String): MovieEntity? = dao.getMovies(movieId,language)

}