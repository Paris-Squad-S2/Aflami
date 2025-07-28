package com.datasource.local.datasource

import com.datasource.local.dao.TvShowDao
import com.repository.dataSource.local.TvShowLocalDataSource
import com.repository.model.local.TvShowEntity
import javax.inject.Inject

class TvShowLocalDataSourceImp @Inject constructor(private val dao: TvShowDao) : TvShowLocalDataSource {
    override suspend fun addTvShow(tvShow: TvShowEntity) = dao.addTvShow(tvShow)
    override suspend fun getTvShowId(tvShowId: Int, language: String): TvShowEntity? {
        return dao.getTvShowById(tvShowId, language)
    }
}