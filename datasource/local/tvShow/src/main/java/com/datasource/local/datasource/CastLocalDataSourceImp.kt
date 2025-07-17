package com.datasource.local.datasource

import com.datasource.local.dao.CastDao
import com.repository.dataSource.local.CastLocalDataSource
import com.repository.model.local.CastEntity


class CastLocalDataSourceImp(private val dao: CastDao) : CastLocalDataSource {
    override suspend fun addCast(cast: List<CastEntity>) = dao.addCast(cast)

    override suspend fun getCastByTvShowId(tvShowId: Int): List<CastEntity> =
        dao.getCastByTvShowId(tvShowId)
}