package com.feature.guessGame.guessGameApi

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

@Serializable
sealed interface GuessGameDestination

@OptIn(kotlinx.serialization.InternalSerializationApi::class)
 fun GuessGameDestination.toJson(): String =
    Json.encodeToString(GuessGameDestination::class.serializer(), this)

 fun String.fromJsonToGuessGameDestination(): GuessGameDestination =
    Json.decodeFromString(this)