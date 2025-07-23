package com.paris_2.repository.authentication.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val username: String,
    val password: String,
    @SerialName("request_token") val requestToken: String
)