package com.repository.media.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DatesDto(
    @SerialName("maximum")
    val maximum: String,
    @SerialName("minimum")
    val minimum: String
)