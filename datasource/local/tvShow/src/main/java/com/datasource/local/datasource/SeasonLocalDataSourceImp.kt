package com.datasource.local.datasource

import com.datasource.local.dao.SeasonDao
import com.repository.dataSource.local.SeasonLocalDataSource
import com.repository.model.local.SeasonEntity

class SeasonLocalDataSourceImp(private val dao: SeasonDao) : SeasonLocalDataSource {
    override suspend fun addSeasonDetails(seasons: SeasonEntity) = dao.addSeason(seasons)

    override suspend fun getSeasonDetailsByTvShowId(tvShowId: Int): SeasonEntity? =
        dao.getSeasonByTvShowId(tvShowId)
}