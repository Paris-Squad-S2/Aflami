package com.repository.media.models.remote.movie


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieGenreDto(
    @SerialName("id") val id: Int? = null,
    @SerialName("name") val name: String? = null
)