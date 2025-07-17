package com.repository.datasource.local

import com.repository.entity.TvShowEntity


interface TvShowLocalDataSource {
    suspend fun addTvShow(tvShow: TvShowEntity)
    suspend fun getTvShowId(tvShowId: Int): TvShowEntity?
}