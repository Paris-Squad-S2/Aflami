package com.datasource.remote.mediadetails.models.movie

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieCreditsDto(
    @SerialName("id") val id: Int? = null,
    @SerialName("cast") val cast: List<MovieCastDto>? = null,
    @SerialName("crew") val crew: List<MovieCrewDto>? = null
)

