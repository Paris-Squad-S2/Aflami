package com.feature.guessGame.guessGameUi.screen.guessGameScreen

import com.feature.guessGame.guessGameUi.common.BaseViewModel
import com.feature.guessGame.guessGameUi.navigation.GuessGameDestinations
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
        val settings =
            DifficultySettings.getDifficultySettings(screenState.value.selectedDifficulty)
        val questionType = gameIdToQuestionType[screenState.value.selectedGameId] ?: return

        updateState(screenState.value.copy(showDifficultyDialog = false))

        navigate(
            GuessGameDestinations.GuessQuestionScreen(
                questionType = questionType,
                totalQuestions = settings.numberOfQuestions,
                timePerQuestion = settings.timePerQuestionSec,
                pointsPerQuestion = settings.pointsPerQuestion
            )
        )
    }

    override fun onDismissDifficultyDialog() {
        updateState(screenState.value.copy(showDifficultyDialog = false))
    }

    companion object {
        const val GAME_ID_RELEASE_YEAR = "guess_release_year"
        const val GAME_ID_GENRE = "guess_genre"

        private val gameIdToQuestionType = mapOf(
            GAME_ID_RELEASE_YEAR to QuestionType.RELEASE_YEAR,
            GAME_ID_GENRE to QuestionType.GENRE
        )
    }
}
