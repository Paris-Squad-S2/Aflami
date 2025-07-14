package com.datasource

import androidx.room.TypeConverter
import com.repository.entity.ProductionCompanyEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MovieDetailConverter {
    private val json = Json { ignoreUnknownKeys = true } // Configure Json as needed

    @TypeConverter
    fun fromProductionCompanyList(companies: List<ProductionCompanyEntity>?): String? {
        if (companies == null) {
            return null
        }
        return json.encodeToString(companies)
    }

    @TypeConverter
    fun toProductionCompanyList(companiesString: String?): List<ProductionCompanyEntity>? {
        if (companiesString == null) {
            return null
        }
        return json.decodeFromString<List<ProductionCompanyEntity>>(companiesString)
    }
}