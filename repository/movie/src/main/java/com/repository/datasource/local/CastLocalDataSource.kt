package com.repository.datasource.local

import com.repository.entity.CastEntity

interface CastLocalDataSource {
    suspend fun addCast(cast: List<CastEntity>)
    suspend fun getCast(): List<CastEntity>
}