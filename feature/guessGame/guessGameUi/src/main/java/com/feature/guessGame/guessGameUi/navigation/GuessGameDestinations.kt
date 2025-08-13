package com.feature.guessGame.guessGameUi.navigation

import kotlinx.serialization.Serializable

sealed interface GuessGameDestinations : GuessGameGraph {

    @Serializable
    data object GuessGameGraph1 : GuessGameGraph

    @Serializable
    data object GuessGameScreen : GuessGameDestination

    @Serializable
    data object GuessCharacterScreen : GuessGameDestination

    @Serializable
    data object GuessMovieScreen : GuessGameDestination

    @Serializable
    data class GuessQuestionScreen(
        val questionType: QuestionType,
    ) : GuessGameDestination

    @Serializable
    data object FinishGameScreen : GuessGameDestination

}

@Serializable
enum class QuestionType {
    RELEASE_YEAR,
    GENRE
}