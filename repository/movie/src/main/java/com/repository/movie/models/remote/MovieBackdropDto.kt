package com.repository.movie.models.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieBackdropDto(
    @SerialName("aspect_ratio") val aspectRatio: Double? = null,
    @SerialName("file_path") val filePath: String? = null,
    @SerialName("height") val height: Int? = null,
    @SerialName("iso_639_1") val iso6391: String? = null,
    @SerialName("vote_average") val voteAverage: Double? = null,
    @SerialName("vote_count") val voteCount: Int? = null,
    @SerialName("width") val width: Int? = null
)