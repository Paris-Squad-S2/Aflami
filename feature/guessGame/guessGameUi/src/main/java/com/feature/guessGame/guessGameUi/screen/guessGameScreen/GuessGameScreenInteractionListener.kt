package com.feature.guessGame.guessGameUi.screen.guessGameScreen

interface GuessGameScreenInteractionListener {
    fun onGamePlayClicked(gameId: String)
    fun onSelectDifficulty(difficultyId: Int)
    fun onStartGame()
    fun onDismissDifficultyDialog()
}