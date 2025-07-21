package com.paris_2.home.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DatesDto(
    @SerialName("maximum")
    val maximum: String,
    @SerialName("minimum")
    val minimum: String
)