package com.datasource.local.datasorce

import com.datasource.local.dao.CastDao
import com.repository.datasorce.local.CastLocalDataSource
import com.repository.entity.CastEntity

class CastDataSourceImp(private val dao: CastDao) : CastLocalDataSource {

    override suspend fun addCast(cast: List<CastEntity>) = dao.addCast(cast)

    override suspend fun getCast(): List<CastEntity> {
        TODO("Not yet implemented")
    }

}