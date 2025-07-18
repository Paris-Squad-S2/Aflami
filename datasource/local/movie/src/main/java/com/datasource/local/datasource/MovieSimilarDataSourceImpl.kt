package com.datasource.local.datasource

import com.datasource.local.dao.MovieSimilarDao
import com.repository.movie.dataSource.local.MovieSimilarLocalDataSource
import com.repository.movie.models.local.MovieSimilarEntity

class MovieSimilarDataSourceImpl(
    val similarDao: MovieSimilarDao
) : MovieSimilarLocalDataSource {

    override suspend fun addSimilarMovies(movieSimilar: List<MovieSimilarEntity>) =
        similarDao.addSimilarMovies(movieSimilar)

    override suspend fun getSimilarMovies(
        movieId: Int,
        page: Int,
        language: String
    ): List<MovieSimilarEntity>? = similarDao.getSimilarMovies(movieId, page, language)

}