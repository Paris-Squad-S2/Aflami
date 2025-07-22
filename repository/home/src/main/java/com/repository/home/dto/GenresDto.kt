package com.paris_2.home.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenresDto(
    @SerialName("genres") val genreDto: List<GenreDto>? = null
)