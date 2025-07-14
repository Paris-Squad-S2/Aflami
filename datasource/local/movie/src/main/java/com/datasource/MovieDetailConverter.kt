package com.datasource

import androidx.room.TypeConverter
import com.repository.entity.ProductionCompanyEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MovieDetailConverter {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromProductionCompanyList(companies: List<ProductionCompanyEntity>?): String? =
        companies.let { json.encodeToString(companies) }


    @TypeConverter
    fun toProductionCompanyList(companiesString: String?): List<ProductionCompanyEntity>? =
        companiesString?.let {
            json.decodeFromString<List<ProductionCompanyEntity>>(
                companiesString
            )
        }
}
