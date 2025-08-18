package com.repository.media.models.local.media

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries_table")
data class CountryEntity(
    @PrimaryKey
    val countryCode: String,
    val englishName: String,
    val arabicName: String
)