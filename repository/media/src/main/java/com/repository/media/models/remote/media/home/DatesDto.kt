package com.repository.media.models.remote.media.home


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DatesDto(
    @SerialName("maximum")
    val maximum: String,
    @SerialName("minimum")
    val minimum: String
)