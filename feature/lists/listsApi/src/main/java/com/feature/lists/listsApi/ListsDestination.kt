package com.feature.lists.listsApi

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

@Serializable
sealed interface ListsDestination

@OptIn(kotlinx.serialization.InternalSerializationApi::class)
 fun ListsDestination.toJson(): String =
    Json.encodeToString(ListsDestination::class.serializer(), this)

 fun String.fromJsonToListsDestination(): ListsDestination =
    Json.decodeFromString(this)