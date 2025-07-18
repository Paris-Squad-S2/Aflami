package com.repository.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvShowCreatedByDto(
    @SerialName("credit_id") val creditId: String? = null,
    @SerialName("gender") val gender: Int? = null,
    @SerialName("id") val id: Int? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("original_name") val originalName: String? = null,
    @SerialName("profile_path") val profilePath: String? = null
)