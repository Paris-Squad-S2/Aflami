package com.repository.movie.dataSource.local

import com.repository.movie.models.local.CastEntity

interface CastLocalDataSource {
    suspend fun addCast(cast: List<CastEntity>)
    suspend fun getCastByMovieId(movieId:Int): List<CastEntity>
}