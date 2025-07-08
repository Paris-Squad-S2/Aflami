package com.datasource.local.search

import androidx.room.TypeConverter
import com.datasource.local.search.entity.MediaTypeEntity
import kotlinx.datetime.LocalDate

class SearchConverter {
    @TypeConverter
    fun fromStringToListOfString(value: String): List<String> {
        return value.split(",")
    }

    @TypeConverter
    fun fromListOfStringToString(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun fromEnumToString(mediaTypeEntity: MediaTypeEntity): String {
        return mediaTypeEntity.name
    }

    @TypeConverter
    fun fromStringToEnum(type: String): MediaTypeEntity {
        return MediaTypeEntity.valueOf(type)
    }

    @TypeConverter
    fun fromStringToDateTime(time: String): LocalDate {
        return LocalDate.parse(time)
    }

    @TypeConverter
    fun fromDateTimeToString(localDate: LocalDate): String {
        return localDate.toString()
    }

}