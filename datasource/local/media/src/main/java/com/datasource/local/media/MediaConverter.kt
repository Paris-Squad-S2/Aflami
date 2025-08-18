package com.datasource.local.media

import androidx.room.TypeConverter
import com.repository.media.models.local.media.Category
import com.repository.media.models.local.media.MediaTypeEntity
import com.repository.model.local.EpisodeEntity
import com.repository.model.local.SeasonEntity
import com.repository.model.local.TVGenreEntity
import com.repository.model.local.TVProductionCompanyEntity
import com.repository.model.local.TVImageEntity
import com.repository.media.models.local.moive.MovieGenreEntity
import com.repository.media.models.local.moive.MovieImageEntity
import com.repository.media.models.local.moive.MovieProductionCompanyEntity
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

class MediaConverter {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromListOfIntToString(list: List<Int>?): String? {
        return list?.let { json.encodeToString(ListSerializer(Int.serializer()), it) }
    }

    @TypeConverter
    fun fromStringToListOfInt(value: String?): List<Int> {
        return value?.let { json.decodeFromString(ListSerializer(Int.serializer()), it) }
            ?: emptyList()
    }

    @TypeConverter
    fun fromMediaTypeToString(type: MediaTypeEntity): String = type.name

    @TypeConverter
    fun fromStringToMediaType(type: String): MediaTypeEntity = MediaTypeEntity.valueOf(type)

    @TypeConverter
    fun fromCategory(category: Category): String {
        return category.name
    }

    @TypeConverter
    fun toCategory(category: String): Category {
        return Category.valueOf(category)
    }

    @TypeConverter
    fun fromMovieProductionCompanyList(companies: List<MovieProductionCompanyEntity>?): String? =
        companies.let { json.encodeToString(it) }


    @TypeConverter
    fun toMovieProductionCompanyList(companiesString: String?): List<MovieProductionCompanyEntity>? =
        companiesString?.let {
            json.decodeFromString<List<MovieProductionCompanyEntity>>(
                companiesString
            )
        }

    @TypeConverter
    fun fromMovieGenreEntityList(genres: List<MovieGenreEntity>?): String? {
        if (genres == null) {
            return null
        }
        return json.encodeToString(
            ListSerializer(kotlinx.serialization.serializer<MovieGenreEntity>()),
            genres
        )
    }

    @TypeConverter
    fun toMovieGenreEntityList(genresString: String?): List<MovieGenreEntity>? {
        if (genresString == null) {
            return null
        }
        return json.decodeFromString<List<MovieGenreEntity>>(genresString)
    }

    @TypeConverter
    fun fromMovieImageList(images: List<MovieImageEntity>?): String? {
        if (images == null) {
            return null
        }
        return json.encodeToString(
            ListSerializer(
                kotlinx.serialization.serializer<MovieImageEntity>()
            ),
            images
        )
    }

    @TypeConverter
    fun toMovieImageList(imagesString: String?): List<MovieImageEntity>? {
        if (imagesString == null) {
            return null
        }
        return json.decodeFromString<List<MovieImageEntity>>(imagesString)
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
    @TypeConverter
    fun fromTVProductionCompanyList(companies: List<TVProductionCompanyEntity>?): String? =
        companies.let { json.encodeToString(it) }


    @TypeConverter
    fun toTVProductionCompanyList(companiesString: String?): List<TVProductionCompanyEntity>? =
        companiesString?.let {
            json.decodeFromString<List<TVProductionCompanyEntity>>(
                companiesString
            )
        }

    @TypeConverter
    fun fromTVGenreEntityList(genres: List<TVGenreEntity>?): String? {
        if (genres == null) {
            return null
        }
        return json.encodeToString(ListSerializer(serializer<TVGenreEntity>()), genres)
    }

    @TypeConverter
    fun toTVGenreEntityList(genresString: String?): List<TVGenreEntity>? {
        if (genresString == null) {
            return null
        }
        return json.decodeFromString<List<TVGenreEntity>>(genresString)
    }

    @TypeConverter
    fun fromTVImageList(images: List<TVImageEntity>?): String? {
        if (images == null) {
            return null
        }
        return json.encodeToString(
            ListSerializer(
                serializer<TVImageEntity>()
            ),
            images
        )
    }

    @TypeConverter
    fun toTVImageList(imagesString: String?): List<TVImageEntity>? =
        imagesString?.let { json.decodeFromString<List<TVImageEntity>>(it) }

    @TypeConverter
    fun fromEpisodeList(episodes: List<EpisodeEntity>?): String? =
        json.encodeToString(episodes)

    @TypeConverter
    fun toEpisodeList(episodesJson: String?): List<EpisodeEntity>? =
        episodesJson?.let { json.decodeFromString<List<EpisodeEntity>>(it) }


    @TypeConverter
    fun fronListOfSeasonEntity(seasons: List<SeasonEntity>?): String? {
        return seasons?.let { json.encodeToString(it) }
    }

    @TypeConverter
    fun toListOfSeasonEntity(seasonsString: String?): List<SeasonEntity>? {
        return seasonsString?.let { json.decodeFromString<List<SeasonEntity>>(it) }
    }

}