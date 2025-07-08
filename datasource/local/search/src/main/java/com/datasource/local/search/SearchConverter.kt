package com.datasource.local.search

import androidx.room.TypeConverter
import com.repository.search.entity.MediaTypeEntity
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
    fun fromEnumToString(mediaTypeEntity: com.repository.search.entity.MediaTypeEntity): String {
        return mediaTypeEntity.name
    }

    @TypeConverter
    fun fromStringToEnum(type: String): com.repository.search.entity.MediaTypeEntity {
        return com.repository.search.entity.MediaTypeEntity.valueOf(type)
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