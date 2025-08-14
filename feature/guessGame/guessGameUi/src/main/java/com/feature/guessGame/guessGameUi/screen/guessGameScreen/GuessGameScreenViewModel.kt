package com.feature.guessGame.guessGameUi.screen.guessGameScreen

import android.util.Log
import com.feature.guessGame.guessGameUi.common.BaseViewModel
import com.feature.guessGame.guessGameUi.navigation.GuessGameDestinations.GuessByImageScreen
import com.feature.guessGame.guessGameUi.navigation.GuessGameDestinations.GuessQuestionScreen
import com.feature.guessGame.guessGameUi.navigation.ImageType
import com.feature.guessGame.guessGameUi.navigation.QuestionType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GuessGameScreenViewModel @Inject constructor() :
    GuessGameScreenInteractionListener,
    BaseViewModel<GuessGameScreenUiState>(GuessGameScreenUiState()) {

    override fun onGamePlayClicked(gameId: String) {
        updateState(
            screenState.value.copy(
                selectedGameId = gameId,
                showDifficultyDialog = true
            )
        )
    }

    override fun onSelectDifficulty(difficultyId: Int) {
        updateState(screenState.value.copy(selectedDifficulty = difficultyId))
    }

    override fun onStartGame() {
        Log.d("navTest", "onStartGame")
        val settings =
            DifficultySettings.getDifficultySettings(screenState.value.selectedDifficulty)

        Log.d("navTest", "settings = $settings")

        val questionType = gameIdToQuestionType[screenState.value.selectedGameId]

        updateState(screenState.value.copy(showDifficultyDialog = false))
        Log.d("navTest", "questionType = $questionType")
        when (questionType) {
            QuestionType.ACTOR -> {
                Log.d("navTest", "QuestionType.ACTOR")
                navigate(
                    GuessByImageScreen(
                        questionType = questionType,
                        totalQuestions = settings.numberOfQuestions,
                        timePerQuestion = settings.timePerQuestionSec,
                        pointsPerQuestion = settings.pointsPerQuestion,
                        imageType = ImageType.ACTOR
                    )
                )
            }

            QuestionType.RELEASE_YEAR -> {
                Log.d("navTest", "QuestionType.RELEASE_YEAR")

            }

            QuestionType.GENRE -> {
                Log.d("navTest", "QuestionType.GENRE")
                navigate(
                    GuessQuestionScreen(
                        questionType = questionType,
                        totalQuestions = settings.numberOfQuestions,
                        timePerQuestion = settings.timePerQuestionSec,
                        pointsPerQuestion = settings.pointsPerQuestion
                    )
                )
            }

            null -> {
                Log.d(
                    "navTest",
                    "Unknown question type for gameId: ${screenState.value.selectedGameId}"
                )
            }
        }
    }

    override fun onDismissDifficultyDialog() {
        updateState(screenState.value.copy(showDifficultyDialog = false))
    }

    companion object {
        const val GAME_ID_RELEASE_YEAR = "guess_release_year"
        const val GAME_ID_GENRE = "guess_genre"
        const val GAME_ID_ACTOR = "guess_character"

        private val gameIdToQuestionType = mapOf(
            GAME_ID_RELEASE_YEAR to QuestionType.RELEASE_YEAR,
            GAME_ID_GENRE to QuestionType.GENRE,
            GAME_ID_ACTOR to QuestionType.ACTOR
        )
    }
}
