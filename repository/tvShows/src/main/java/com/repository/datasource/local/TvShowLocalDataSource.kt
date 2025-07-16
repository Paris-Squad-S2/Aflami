package com.repository.datasource.local

import com.repository.entity.TvShowEntity


interface TvShowLocalDataSource {
    suspend fun addMovie(tvShow: TvShowEntity)
    suspend fun getMovie(tvShowId: Int): TvShowEntity??
}