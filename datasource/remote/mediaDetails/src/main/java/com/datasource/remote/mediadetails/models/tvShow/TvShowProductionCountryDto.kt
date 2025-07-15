package com.datasource.remote.mediadetails.models.tvShow

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvShowProductionCountryDto(
    @SerialName("iso_3166_1") val iso31661: String? = null,
    @SerialName("name") val name: String? = null
)