package com.datasource.local.home

import kotlinx.serialization.json.Json
import androidx.room.TypeConverter
import com.repository.media.entity.MediaTypeEntity
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer

class HomeConverter {
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

}