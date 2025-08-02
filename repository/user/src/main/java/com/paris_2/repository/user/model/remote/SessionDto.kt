package com.paris_2.repository.user.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SessionDto(
    @SerialName("session_id") val sessionId: String? = null,
    @SerialName("success") val success: Boolean? = null
)