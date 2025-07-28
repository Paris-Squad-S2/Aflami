package com.datasource.local.datasource

import com.datasource.local.dao.TvShowCastDao
import com.repository.dataSource.local.TvShowCastLocalDataSource
import com.repository.model.local.CastEntity
import javax.inject.Inject

class TvShowCastLocalDataSourceImp @Inject constructor(private val dao: TvShowCastDao) : TvShowCastLocalDataSource {
    override suspend fun addCast(cast: List<CastEntity>) = dao.addCast(cast)

    override suspend fun getCastByTvShowId(tvShowId: Int,language: String): List<CastEntity> =
        dao.getCastByTvShowId(tvShowId,language)
}