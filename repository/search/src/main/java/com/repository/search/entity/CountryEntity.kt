package com.repository.search.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries_table")
data class CountryEntity(
    @PrimaryKey
    val id: String,
    val name: String
)