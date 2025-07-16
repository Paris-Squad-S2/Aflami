package com.datasource.local.datasource

import com.datasource.local.dao.MovieDao
import com.example.movie.dataSource.local.MovieLocalDataSource
import com.example.movie.models.local.MovieEntity

class MovieLocalDataSourceImp(private val dao: MovieDao) : MovieLocalDataSource {
    override suspend fun addMovie(movie: MovieEntity) = dao.addMovies(movie)

    override suspend fun getMovie(movieId: Int): MovieEntity? = dao.getMovies(movieId)

}