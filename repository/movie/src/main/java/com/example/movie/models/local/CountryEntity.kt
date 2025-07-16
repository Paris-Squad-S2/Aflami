package com.example.movie.models.local

import kotlinx.serialization.Serializable

@Serializable
data class CountryEntity(
    val countryCode: String,
    val name: String,
)