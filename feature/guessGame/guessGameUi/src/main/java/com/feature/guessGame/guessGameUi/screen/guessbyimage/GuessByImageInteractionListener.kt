package com.feature.guessGame.guessGameUi.screen.guessbyimage

interface GuessByImageInteractionListener {
    fun onHintClicked()
    fun onGuessClicked(guess: String)
    fun onNextClicked()
    fun onBackClicked()
}