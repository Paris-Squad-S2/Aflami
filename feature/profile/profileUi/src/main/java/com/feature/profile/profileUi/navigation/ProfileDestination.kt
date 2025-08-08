package com.feature.profile.profileUi.navigation

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

@Serializable
sealed interface ProfileDestination

@OptIn(InternalSerializationApi::class)
fun ProfileDestination.toJson(): String =
    Json.encodeToString(ProfileDestination::class.serializer(), this)

fun String.fromJsonToMediaDetailsDestination(): ProfileDestination =
    Json.decodeFromString(this)