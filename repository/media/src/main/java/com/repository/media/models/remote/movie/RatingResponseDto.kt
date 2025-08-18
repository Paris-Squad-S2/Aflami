package com.repository.media.models.remote.movie

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RatingResponseDto(
    @SerialName("status_code") val statusCode: Int? = null,
    @SerialName("status_message") val statusMessage: String? = null
)