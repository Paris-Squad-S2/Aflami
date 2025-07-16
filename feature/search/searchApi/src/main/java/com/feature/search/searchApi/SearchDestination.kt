package com.feature.search.searchApi

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

@Serializable
sealed interface SearchDestination

@OptIn(kotlinx.serialization.InternalSerializationApi::class)
fun SearchDestination.toJson(): String =
    Json.encodeToString(SearchDestination::class.serializer(), this)

fun String.fromJsonToSearchDestination(): SearchDestination =
    Json.decodeFromString(this)