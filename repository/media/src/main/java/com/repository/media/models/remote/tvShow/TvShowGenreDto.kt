package com.repository.media.models.remote.tvShow

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvShowGenreDto(
    @SerialName("id") val id: Int? = null,
    @SerialName("name") val name: String? = null
)