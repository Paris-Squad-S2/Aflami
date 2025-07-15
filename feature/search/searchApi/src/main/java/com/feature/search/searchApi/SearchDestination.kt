package com.feature.search.searchApi

import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

interface SearchDestination

@OptIn(kotlinx.serialization.InternalSerializationApi::class)
inline fun <reified T : SearchDestination> T.toJson(): String =
    Json.encodeToString(T::class.serializer(), this)

inline fun <reified T : SearchDestination> String.fromJson(): T =
    Json.decodeFromString(this)