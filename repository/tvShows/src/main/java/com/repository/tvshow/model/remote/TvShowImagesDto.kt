package com.repository.tvshow.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvShowImagesDto(
    @SerialName("backdrops") val tvShowBackdropDto: List<TvShowBackdropDto>? = null,
    @SerialName("id") val id: Int? = null,
    @SerialName("logos") val logos: List<TvShowLogoDto>? = null,
    @SerialName("posters") val posters: List<TvShowPosterDto>? = null
)