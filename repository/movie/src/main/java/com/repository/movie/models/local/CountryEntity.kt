package com.repository.movie.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "country_table")
@Serializable
data class CountryEntity(
    @PrimaryKey
    val countryCode: String,
    val name: String,
)