package com.feature.guessGame.guessGameUi.navigation

import kotlinx.serialization.Serializable

sealed interface GuessGameDestinations : GuessGameGraph {

    @Serializable
    data object GuessGameGraph1 : GuessGameGraph

    @Serializable
    data object GuessGameScreen : GuessGameDestination

    @Serializable
    data class GuessByImageScreen(
        val questionType: QuestionType,
        val totalQuestions: Int,
        val timePerQuestion: Int,
        val pointsPerQuestion: Int,
        val imageType: ImageType
    ) : GuessGameDestination

    @Serializable
    data class GuessQuestionScreen(
        val questionType: QuestionType,
        val totalQuestions: Int,
        val timePerQuestion: Int,
        val pointsPerQuestion: Int
    ) : GuessGameDestination

    @Serializable
    data object FinishGameScreen : GuessGameDestination

}

@Serializable
enum class QuestionType {
    RELEASE_YEAR,
    GENRE,
    ACTOR
}

@Serializable
enum class ImageType {
    ACTOR,
    POSTER
}