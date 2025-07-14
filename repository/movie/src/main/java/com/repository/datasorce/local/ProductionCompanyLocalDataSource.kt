package com.repository.datasorce.local

import com.repository.entity.ProductionCompanyEntity

interface ProductionCompanyLocalDataSource {
    suspend fun addProductionCompany(productionCompany: List<ProductionCompanyEntity>)
    suspend fun getProductionCompany(): List<ProductionCompanyEntity>
}