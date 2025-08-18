package com.repository.media.models.remote.movie

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RatingDto(
    @SerialName("value")
    val value: Float
)