package com.feature.guessGame.guessGameUi.screen.resultScreen

import com.feature.guessGame.guessGameUi.common.BaseViewModel
import com.feature.guessGame.guessGameUi.navigation.GuessGameDestinations
import javax.inject.Inject

class ResultViewModel @Inject constructor(

) : BaseViewModel<ResultUiState>(ResultUiState()), ResultInteractionListener {

    init {
        loadSessionData()
    }

    fun loadSessionData() {

    }

    override fun onExitClicked() {
        navigate(destination = GuessGameDestinations.GuessGameScreen)
    }

    override fun onPlayAgainClicked() {
        TODO("Not yet implemented")
    }

    override fun onBackToMenuClicked() {
        navigate(destination = GuessGameDestinations.GuessGameScreen)
    }

}