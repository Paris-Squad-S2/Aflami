package com.feature.guessGame.guessGameUi.screen.guessGameScreen

import com.feature.guessGame.guessGameUi.common.BaseViewModel
import com.feature.guessGame.guessGameUi.navigation.GuessGameDestinations
import com.feature.guessGame.guessGameUi.navigation.QuestionType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GuessGameScreenViewModel @Inject constructor() : GuessGameScreenInteractionListener,
    BaseViewModel<GuessGameScreenUiState>(GuessGameScreenUiState()) {

    override fun onGamePlayClicked(gameId: String) {
        when (gameId) {
            "guess_character" -> {}
            "guess_movie" -> {}
            "guess_release_year" -> {
                navigate(GuessGameDestinations.GuessQuestionScreen(QuestionType.RELEASE_YEAR))
            }

            "guess_genre" -> {
                navigate(GuessGameDestinations.GuessQuestionScreen(QuestionType.GENRE))
            }

            else -> ""
        }
    }
}