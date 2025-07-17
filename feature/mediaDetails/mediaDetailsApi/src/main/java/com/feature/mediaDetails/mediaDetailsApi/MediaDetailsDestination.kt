package com.feature.mediaDetails.mediaDetailsApi

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

@Serializable
sealed interface MediaDetailsDestination

@OptIn(kotlinx.serialization.InternalSerializationApi::class)
 fun MediaDetailsDestination.toJson(): String =
    Json.encodeToString(MediaDetailsDestination::class.serializer(), this)

 fun String.fromJsonToMediaDetailsDestination(): MediaDetailsDestination =
    Json.decodeFromString(this)