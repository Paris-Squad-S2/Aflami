package com.feature.profile.profileUi.navigation

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

@Serializable
sealed interface Destination {

    @Serializable
    data object WatchHistoryScreen : Destination

    @Serializable
    data object MyRatingScreen : Destination
}

@OptIn(InternalSerializationApi::class)
fun Destination.toJson(): String =
    Json.encodeToString(Destination::class.serializer(), this)

fun String.fromJsonToDestination(): Destination =
    Json.decodeFromString(this)