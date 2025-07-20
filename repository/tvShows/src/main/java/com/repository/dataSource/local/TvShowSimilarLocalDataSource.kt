package com.repository.dataSource.local

import com.repository.model.local.TvShowSimilarEntity

interface TvShowSimilarLocalDataSource {
    suspend fun addSimilarTvShows(tvShowSimilar: List<TvShowSimilarEntity>)
    suspend fun getSimilarTvShows(
        tvShowId: Int,
        page: Int,
        language: String
    ): List<TvShowSimilarEntity>
}