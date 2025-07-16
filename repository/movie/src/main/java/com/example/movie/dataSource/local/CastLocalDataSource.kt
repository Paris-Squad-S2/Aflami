package com.example.movie.dataSource.local

import com.example.movie.models.local.CastEntity

interface CastLocalDataSource {
    suspend fun addCast(cast: List<CastEntity>)
    suspend fun getCastByMovieId(movieId:Int): List<CastEntity>
}