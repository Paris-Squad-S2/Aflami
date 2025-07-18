package com.datasource

import androidx.room.TypeConverter
import com.repository.movie.models.local.GenreEntity
import com.repository.movie.models.local.ImageEntity
import com.repository.movie.models.local.ProductionCompanyEntity
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
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
    fun fromImageList(images: List<ImageEntity>?): String? {
        if (images == null) {
            return null
        }
        return json.encodeToString(
            ListSerializer(
                serializer<ImageEntity>()
            ),
            images
        )
    }

    @TypeConverter
    fun toImageList(imagesString: String?): List<ImageEntity>? {
        if (imagesString == null) {
            return null
        }
        return json.decodeFromString<List<ImageEntity>>(imagesString)
    }

    @TypeConverter
    fun fromLocalDate(value: LocalDate?): String? = value?.toString()

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? = value?.let { LocalDate.parse(it) }

    @TypeConverter
    fun fromLocalDateTime(date: LocalDateTime?): String? {
        return date?.toString()
    }

    @TypeConverter
    fun toLocalDateTime(dateString: String?): LocalDateTime? {
        return dateString?.let { LocalDateTime.Companion.parse(it) }
    }
}
