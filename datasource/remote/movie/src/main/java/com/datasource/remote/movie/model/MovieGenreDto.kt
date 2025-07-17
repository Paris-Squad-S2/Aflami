package com.datasource.remote.movie.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieGenreDto(
    @SerialName("id") val id: Int? = null,
    @SerialName("name") val name: String? = null
)