package com.example.movie.models.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieCastDto(
    @SerialName("id") val id: Int? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("profile_path") val profilePath: String? = null,
    @SerialName("character") val character: String? = null
)

