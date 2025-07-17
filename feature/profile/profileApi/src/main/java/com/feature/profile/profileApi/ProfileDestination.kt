package com.feature.profile.profileApi

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

@Serializable
sealed interface ProfileDestination

@OptIn(kotlinx.serialization.InternalSerializationApi::class)
 fun ProfileDestination.toJson(): String =
    Json.encodeToString(ProfileDestination::class.serializer(), this)

 fun String.fromJsonToProfileDestination(): ProfileDestination =
    Json.decodeFromString(this)