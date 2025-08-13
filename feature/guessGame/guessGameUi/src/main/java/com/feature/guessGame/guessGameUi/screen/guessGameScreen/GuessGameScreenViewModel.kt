package com.feature.guessGame.guessGameUi.screen.guessGameScreen

import com.feature.guessGame.guessGameUi.common.BaseViewModel
import com.feature.guessGame.guessGameUi.navigation.GuessGameDestinations
import com.feature.guessGame.guessGameUi.navigation.QuestionType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GuessGameScreenViewModel @Inject constructor()
    : GuessGameScreenInteractionListener,
    BaseViewModel<GuessGameScreenUiState>(GuessGameScreenUiState()) {
    override fun onGamePlayClicked(gameTitle: String) {
        val questionType = when (gameTitle) {
            "guess_release_year" -> QuestionType.RELEASE_YEAR
            "guess_genre" -> QuestionType.GENRE
            else -> QuestionType.RELEASE_YEAR
        }
        navigate(GuessGameDestinations.GuessQuestionScreen(questionType))
    }


}