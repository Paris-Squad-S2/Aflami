package com.datasource.local.search

import androidx.room.TypeConverter
import com.repository.search.entity.MediaTypeEntity
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

class SearchConverter {
    private val json = Json { ignoreUnknownKeys = true }
    private val listSerializer = ListSerializer(String.Companion.serializer())

    @TypeConverter
    fun fromStringToListOfString(value: String): List<String> =
        json.decodeFromString(listSerializer, value)

    @TypeConverter
    fun fromListOfStringToString(list: List<String>): String =
        json.encodeToString(listSerializer, list)

    @TypeConverter
    fun fromEnumToString(mediaTypeEntity: MediaTypeEntity): String = mediaTypeEntity.name

    @TypeConverter
    fun fromStringToEnum(type: String): MediaTypeEntity = MediaTypeEntity.valueOf(type)

    @TypeConverter
    fun fromLocalDate(date: LocalDate): String {
        return date.toString()
    }

    @TypeConverter
    fun toLocalDate(dateString: String): LocalDate {
        return LocalDate.Companion.parse(dateString)
    }

    @TypeConverter
    fun fromLocalDateTime(date: LocalDateTime): String {
        return date.toString()
    }

    @TypeConverter
    fun toLocalDateTime(dateString: String): LocalDateTime {
        return LocalDateTime.Companion.parse(dateString)
    }

}