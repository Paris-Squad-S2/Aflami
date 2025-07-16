package com.repository.datasource.local

import com.repository.entity.SeasonEntity

interface SeasonLocalDataSource {
    suspend fun addSeason(seasons: List<SeasonEntity>)
    suspend fun getSeasonsByTvShowId(tvShowId: Int): List<SeasonEntity>?
}