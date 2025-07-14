package com.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repository.entity.ProductionCompanyEntity

@Dao
interface ProductionCompanyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProductionCompanies(productionCompanies: List<ProductionCompanyEntity>)

    @Query("SELECT * FROM productionCompany_table")
    suspend fun getProductionCompanies(): List<ProductionCompanyEntity>
}