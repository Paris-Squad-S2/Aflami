package com.repository.media.models.remote.tvShow

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoveTvRatingDto(
    @SerialName("status_code")
    val statusCode: Int,
    @SerialName("status_message")
    val statusMessage: String,
    @SerialName("success")
    val success: Boolean
)