package com.repository.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastsDto(
    @SerialName("casts") val casts: List<CastDto>? = null,
)

@Serializable
data class CastDto(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("imageUri")
    val imageUri: String? = null,
)

