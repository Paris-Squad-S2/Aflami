package com.paris_2.repository.authentication.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestTokenDto(
    @SerialName("expires_at") val expiresAt: String? = null,
    @SerialName("request_token") val requestToken: String? = null,
    @SerialName("success") val success: Boolean? = null
)