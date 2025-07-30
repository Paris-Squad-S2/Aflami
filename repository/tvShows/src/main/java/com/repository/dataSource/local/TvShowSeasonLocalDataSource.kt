package com.repository.dataSource.local

import com.repository.model.local.SeasonEntity

interface TvShowSeasonLocalDataSource {
    suspend fun addSeasonDetails(seasons: SeasonEntity)
    suspend fun getSeasonDetailsByTvShowIdAndSeasonNumber(tvShowId: Int, seasonNumber: Int): SeasonEntity?
}