package com.datasource.local.datasource

import com.datasource.local.dao.MovieCastDao
import com.repository.movie.dataSource.local.MovieCastLocalDataSource
import com.repository.movie.models.local.CastEntity

class MovieCastLocalDataSourceImp(private val dao: MovieCastDao) : MovieCastLocalDataSource {

    override suspend fun addCast(cast: List<CastEntity>) = dao.addCast(cast)

    override suspend fun getCastByMovieId(movieId: Int): List<CastEntity> = dao.getCastByMovieId(movieId)

}