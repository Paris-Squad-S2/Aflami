package com.datasource.local.media

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDateTime

class SearchConverter {
    @TypeConverter
    fun fromLocalDateTime(date: LocalDateTime?): String? {
        return date?.toString()
    }

    @TypeConverter
    fun toLocalDateTime(dateString: String?): LocalDateTime? {
        return dateString?.let { LocalDateTime.Companion.parse(it) }
    }

}