package com.repository.media.models.remote.media

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenresDto(
    @SerialName("genres") val genreDto: List<GenreDto>? = null
)