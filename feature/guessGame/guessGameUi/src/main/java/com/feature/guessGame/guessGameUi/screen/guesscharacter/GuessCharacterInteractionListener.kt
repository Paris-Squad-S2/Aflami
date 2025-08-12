package com.feature.guessGame.guessGameUi.screen.guesscharacter

interface GuessCharacterInteractionListener {
    fun onHintClicked()
    fun onGuessClicked(guess: String)
    fun onNextClicked()
    fun onBackClicked()
}