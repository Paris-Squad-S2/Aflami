package com.feature.home.homeApi

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

@Serializable
sealed interface HomeDestination

@OptIn(kotlinx.serialization.InternalSerializationApi::class)
 fun HomeDestination.toJson(): String =
    Json.encodeToString(HomeDestination::class.serializer(), this)

 fun String.fromJsonToHomeDestination(): HomeDestination =
    Json.decodeFromString(this)