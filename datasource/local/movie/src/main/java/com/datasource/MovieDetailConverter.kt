package com.datasource

import androidx.room.TypeConverter
import com.repository.entity.CountryEntity
import com.repository.entity.GenreEntity
import com.repository.entity.ProductionCompanyEntity
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

class MovieDetailConverter {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromProductionCompanyList(companies: List<ProductionCompanyEntity>?): String? =
        companies.let { json.encodeToString(it) }


    @TypeConverter
    fun toProductionCompanyList(companiesString: String?): List<ProductionCompanyEntity>? =
        companiesString?.let {
            json.decodeFromString<List<ProductionCompanyEntity>>(
                companiesString
            )
        }

    @TypeConverter
    fun fromGenreEntityList(genres: List<GenreEntity>?): String? {
        if (genres == null) {
            return null
        }
        // Using explicit ListSerializer for robustness, similar to ProductionCompanyEntity
        return json.encodeToString(ListSerializer(serializer<GenreEntity>()), genres)
    }

    @TypeConverter
    fun toGenreEntityList(genresString: String?): List<GenreEntity>? {
        if (genresString == null) {
            return null
        }
        return json.decodeFromString<List<GenreEntity>>(genresString)
    }

    @TypeConverter
    fun fromCountryEntity(country: CountryEntity?): String? {
        if (country == null) {
            return null
        }

        return json.encodeToString(country)
    }

    @TypeConverter
    fun toCountryEntity(countryString: String?): CountryEntity? {
        if (countryString == null) {
            return null
        }
        return json.decodeFromString<CountryEntity>(countryString)
    }
}
