package com.feature.guessGame.guessGameUi.navigation

import androidx.annotation.Keep
import com.feature.guessGame.guessGameUi.screen.guessGameScreen.mapper.UiGameLevel
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

interface Graph

@Serializable
sealed interface Destination

sealed interface Destinations : Graph {

    @Serializable
    data object Graph1 : Graph

    @Serializable
    data object Screen : Destination

    @Serializable
    data class GuessByImageScreen(
        val questionType: QuestionType,
        val totalQuestions: Int,
        val timePerQuestion: Int,
        val pointsPerQuestion: Int,
        val imageType: QuestionType,
        val gameLevel: UiGameLevel,
    ) : Destination

    @Serializable
    data class GuessQuestionScreen(
        val questionType: QuestionType,
        val totalQuestions: Int,
        val timePerQuestion: Int,
        val pointsPerQuestion: Int,
        val gameLevel: UiGameLevel,
    ) : Destination

    @Serializable
    data class FinishGameScreen (
       val totalGameTime : Int,
       val totalGamePoints : Int,
       val gameType: QuestionType,
       val gameLevel: UiGameLevel,
    ) : Destination
}

@OptIn(InternalSerializationApi::class)
fun Destination.toJson(): String =
    Json.encodeToString(Destination::class.serializer(), this)

fun String.fromJsonToDestination(): Destination =
    Json.decodeFromString(this)

@Serializable
@Keep
enum class QuestionType {
    RELEASE_YEAR,
    GENRE,
    ACTOR,
    POSTER
}