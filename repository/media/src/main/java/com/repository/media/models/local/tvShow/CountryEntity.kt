package com.repository.media.models.local.tvShow


import kotlinx.serialization.Serializable

@Serializable
data class CountryEntity(
    val countryCode: String,
    val name: String,
)