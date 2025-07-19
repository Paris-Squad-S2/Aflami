package com.datasource.local.datasource

import com.datasource.local.dao.MovieCastDao
import com.repository.movie.dataSource.local.MovieCastLocalDataSource
import com.repository.movie.models.local.CastEntity

class MovieMovieCastLocalDataSourceImpl(private val dao: MovieCastDao) : MovieCastLocalDataSource {

    override suspend fun addCast(cast: List<CastEntity>) = dao.addCast(cast)

    override suspend fun getCastByMovieId(movieId: Int,language: String): List<CastEntity>? = dao.getCastByMovieId(movieId,language)


}