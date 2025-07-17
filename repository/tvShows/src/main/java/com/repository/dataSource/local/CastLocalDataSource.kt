package com.repository.dataSource.local

import com.repository.model.local.CastEntity

interface CastLocalDataSource {
    suspend fun addCast(cast: List<CastEntity>)
    suspend fun getCastByTvShowId(tvShowId: Int): List<CastEntity>
}