package com.datasource.local.datasource

import com.datasource.local.dao.CastDao
import com.example.movie.dataSource.local.CastLocalDataSource
import com.example.movie.models.local.CastEntity

class CastLocalDataSourceImp(private val dao: CastDao) : CastLocalDataSource {

    override suspend fun addCast(cast: List<CastEntity>) = dao.addCast(cast)

    override suspend fun getCastByMovieId(movieId: Int): List<CastEntity> = dao.getCastByMovieId(movieId)

}