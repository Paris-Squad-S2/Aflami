package com.repository.media.models.remote.movie

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoveRatingDto(
    @SerialName("status_code")
    val status_code: Int,
    @SerialName("status_message")
    val status_message: String,
    @SerialName("success")
    val success: Boolean
)