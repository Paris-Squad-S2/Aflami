package com.example.movie.models.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieBelongsToCollectionDto(
    @SerialName("backdrop_path") val backdropPath: String? = null,
    @SerialName("id") val id: Int? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("poster_path") val posterPath: String? = null
)