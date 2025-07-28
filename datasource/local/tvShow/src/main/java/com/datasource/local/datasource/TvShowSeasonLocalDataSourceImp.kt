package com.datasource.local.datasource

import com.datasource.local.dao.SeasonDao
import com.repository.dataSource.local.TvShowSeasonLocalDataSource
import com.repository.model.local.SeasonEntity
import javax.inject.Inject

class TvShowSeasonLocalDataSourceImp @Inject constructor(private val dao: SeasonDao) : TvShowSeasonLocalDataSource {
    override suspend fun addSeasonDetails(seasons: SeasonEntity) = dao.addSeason(seasons)

    override suspend fun getSeasonDetailsByTvShowId(tvShowId: Int): SeasonEntity? =
        dao.getSeasonByTvShowId(tvShowId)
}