package com.datasource.local.datasource

import com.datasource.local.dao.TvShowSimilarDao
import com.repository.dataSource.local.TvShowSimilarLocalDataSource
import com.repository.model.local.TvShowSimilarEntity
import javax.inject.Inject

class TvShowSimilarLocalDataSourceImpl @Inject constructor(
    private val tvShowSimilarDao: TvShowSimilarDao
): TvShowSimilarLocalDataSource{
    override suspend fun addSimilarTvShows(tvShowSimilar: List<TvShowSimilarEntity>) {
        tvShowSimilarDao.addSimilarTvShows(tvShowSimilar)
    }

    override suspend fun getSimilarTvShows(
        tvShowId: Int,
        page: Int,
        language: String
    ): List<TvShowSimilarEntity> {
        return tvShowSimilarDao.getSimilarTvShows(tvShowId,page,language)
    }

}