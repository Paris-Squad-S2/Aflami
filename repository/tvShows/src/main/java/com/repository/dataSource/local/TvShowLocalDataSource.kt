package com.repository.dataSource.local

import com.repository.model.local.TvShowEntity


interface TvShowLocalDataSource {
    suspend fun addTvShow(tvShow: TvShowEntity)
    suspend fun getTvShowId(tvShowId: Int,language: String): TvShowEntity?
    suspend fun clearTvShowById(tvShowId: Int, language: String)
}