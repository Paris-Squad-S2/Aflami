package com.paris_2.repository.authentication.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GuestSessionDto(
    @SerialName("success") val success: Boolean? = null,
    @SerialName("guest_session_id") val guestSessionId: String? = null,
    @SerialName("expires_at") val expiresAt: String? = null
)
