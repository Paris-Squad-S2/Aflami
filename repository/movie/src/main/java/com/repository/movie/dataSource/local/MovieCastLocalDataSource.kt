package com.repository.movie.dataSource.local

import com.repository.movie.models.local.CastEntity

interface MovieCastLocalDataSource {
    suspend fun addCast(cast: List<CastEntity>)
    suspend fun getCastByMovieId(movieId:Int,language: String): List<CastEntity>?
}