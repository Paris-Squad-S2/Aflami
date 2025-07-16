package com.repository.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
data class CountryEntity(
    val countryCode: String,
    val name: String,
)