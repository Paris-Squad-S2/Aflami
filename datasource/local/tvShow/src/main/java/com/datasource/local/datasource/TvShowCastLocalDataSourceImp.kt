package com.datasource.local.datasource

import com.datasource.local.dao.TvShowCastDao
import com.repository.dataSource.local.TvShowCastLocalDataSource
import com.repository.model.local.CastEntity


class TvShowCastLocalDataSourceImp(private val dao: TvShowCastDao) : TvShowCastLocalDataSource {
    override suspend fun addCast(cast: List<CastEntity>) = dao.addCast(cast)

    override suspend fun getCastByTvShowId(tvShowId: Int): List<CastEntity> =
        dao.getCastByTvShowId(tvShowId)
}