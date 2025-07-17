package com.repository.dataSource.local

import com.repository.model.local.SeasonEntity

interface SeasonLocalDataSource {
    suspend fun addSeasonDetails(seasons: SeasonEntity)
    suspend fun getSeasonDetailsByTvShowId(tvShowId: Int): SeasonEntity?
}