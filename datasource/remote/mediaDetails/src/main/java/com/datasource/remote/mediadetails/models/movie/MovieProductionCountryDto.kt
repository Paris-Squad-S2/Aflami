package com.datasource.remote.mediadetails.models.movie


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieProductionCountryDto(
    @SerialName("iso_3166_1") val iso31661: String? = null,
    @SerialName("name") val name: String? = null
)