package com.datasource.local.datasource

import com.datasource.local.dao.MovieDao
import com.repository.movie.dataSource.local.MovieLocalDataSource
import com.repository.movie.models.local.MovieEntity

class MovieLocalDataSourceImp(private val dao: MovieDao) : MovieLocalDataSource {
    override suspend fun addMovie(movie: MovieEntity) = dao.addMovies(movie)

    override suspend fun getMovieById(movieId: Int): MovieEntity? = dao.getMovieById(movieId)

}