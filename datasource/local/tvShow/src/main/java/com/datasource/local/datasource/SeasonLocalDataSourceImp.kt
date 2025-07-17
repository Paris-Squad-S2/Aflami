package com.datasource.local.datasource

import com.datasource.local.dao.SeasonDao
import com.repository.datasource.local.SeasonLocalDataSource
import com.repository.entity.SeasonEntity

class SeasonLocalDataSourceImp(private val dao: SeasonDao) : SeasonLocalDataSource {
    override suspend fun addSeason(seasons: List<SeasonEntity>) = dao.addSeason(seasons)

    override suspend fun getSeasonsByTvShowId(tvShowId: Int): List<SeasonEntity>? =
        dao.getSeasonByTvShowId(tvShowId)
}