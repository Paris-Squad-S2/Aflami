package com.example.movie.models.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieCreatedByDto(
    @SerialName("credit_id") val creditId: String? = null,
    @SerialName("gender") val gender: Int? = null,
    @SerialName("id") val id: Int? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("original_name") val originalName: String? = null,
    @SerialName("profile_path") val profilePath: String? = null
)