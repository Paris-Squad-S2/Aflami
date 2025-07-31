package com.datasource.local.datasource

import com.datasource.local.dao.SeasonDao
import com.repository.dataSource.local.TvShowSeasonLocalDataSource
import com.repository.model.local.SeasonEntity

class TvShowSeasonLocalDataSourceImp(private val dao: SeasonDao) : TvShowSeasonLocalDataSource {
    override suspend fun addSeasonDetails(seasons: SeasonEntity) = dao.addSeason(seasons)

    override suspend fun getSeasonDetailsByTvShowIdAndSeasonNumber(tvShowId: Int, seasonNumber: Int): SeasonEntity? =
        dao.getSeasonByTvShowIdAndSeasonNumber(tvShowId, seasonNumber)
}