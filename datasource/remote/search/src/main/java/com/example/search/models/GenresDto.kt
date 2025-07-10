package com.example.search.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenresDto(
    @SerialName("genres") val genreDto: List<GenreDto>? = null
)