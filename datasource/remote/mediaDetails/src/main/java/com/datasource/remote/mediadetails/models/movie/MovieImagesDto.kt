package com.datasource.remote.mediadetails.models.movie


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieImagesDto(
    @SerialName("backdrops") val movieBackdropDto: List<MovieBackdropDto>? = null,
    @SerialName("id") val id: Int? = null,
    @SerialName("logos") val logos: List<MovieLogoDto>? = null,
    @SerialName("posters") val posters: List<MoviePosterDto>? = null
)