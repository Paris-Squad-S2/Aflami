package com.repository.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries_table")
data class CountryEntity(
    @PrimaryKey
    val countryCode: String,
    val name: String,
)