package com.repository.movie.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country_table")
data class CountryEntity(
    @PrimaryKey
    val countryCode: String,
    val name: String,
)