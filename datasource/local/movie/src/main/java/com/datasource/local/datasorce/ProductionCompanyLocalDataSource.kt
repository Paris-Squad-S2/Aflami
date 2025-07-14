package com.datasource.local.datasorce

import com.datasource.local.dao.ProductionCompanyDao
import com.repository.datasorce.local.ProductionCompanyLocalDataSource
import com.repository.entity.ProductionCompanyEntity

class ProductionCompanyLocalDataSource(private val dao: ProductionCompanyDao) :
    ProductionCompanyLocalDataSource {
    override suspend fun addProductionCompany(productionCompany: List<ProductionCompanyEntity>) =
        dao.addProductionCompanies(productionCompany)

    override suspend fun getProductionCompany(): List<ProductionCompanyEntity> =
        dao.getProductionCompanies()
}