package com.datasource.remote.mediadetails.models.tvShow

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvShowCreditsDto(
    @SerialName("id") val id: Int? = null,
    @SerialName("cast") val cast: List<TvShowCastDto>? = null,
    @SerialName("crew") val crew: List<TvShowCrewDto>? = null
)

