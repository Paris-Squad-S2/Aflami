package com.datasource.remote.tvShow.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvShowGenreDto(
    @SerialName("id") val id: Int? = null,
    @SerialName("name") val name: String? = null
)