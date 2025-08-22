package com.repository.media.models.remote.movie


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieProductionCountryDto(
    @SerialName("iso_3166_1") val countryCode: String? = null,
    @SerialName("name") val name: String? = null
)