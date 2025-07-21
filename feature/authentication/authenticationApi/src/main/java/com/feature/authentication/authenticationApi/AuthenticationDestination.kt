package com.feature.authentication.authenticationApi

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

@Serializable
sealed interface AuthenticationDestination

@OptIn(kotlinx.serialization.InternalSerializationApi::class)
 fun AuthenticationDestination.toJson(): String =
    Json.encodeToString(AuthenticationDestination::class.serializer(), this)

 fun String.fromJsonToAuthenticationDestination(): AuthenticationDestination =
    Json.decodeFromString(this)