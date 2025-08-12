package com.feature.guessGame.guessGameUi.screen.letsPlayScreen

import com.feature.guessGame.guessGameUi.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GuessGameScreenViewModel @Inject constructor()
    : GuessGameScreenInteractionListener,
    BaseViewModel<LetsPlayScreenUiState>(LetsPlayScreenUiState()) {
    override fun onGamePlayClicked(gameTitle: String) {

    }

}