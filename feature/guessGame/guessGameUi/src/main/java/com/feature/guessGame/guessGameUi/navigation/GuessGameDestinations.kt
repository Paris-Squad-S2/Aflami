package com.feature.guessGame.guessGameUi.navigation

import com.feature.guessGame.guessGameUi.screen.guessGameScreen.mapper.UiGameLevel
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
        val imageType: QuestionType,
    ) : GuessGameDestination

    @Serializable
    data class GuessQuestionScreen(
        val questionType: QuestionType,
        val totalQuestions: Int,
        val timePerQuestion: Int,
        val pointsPerQuestion: Int,
        val gameLevel: UiGameLevel,
    ) : GuessGameDestination

    @Serializable
    data class FinishGameScreen (
       val totalGameTime : Int,
       val totalGamePoints : Int,
       val gameType : QuestionType
    ) : GuessGameDestination

}

@Serializable
enum class QuestionType {
    RELEASE_YEAR,
    GENRE,
    ACTOR,
    POSTER
}