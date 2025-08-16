package com.feature.guessGame.guessGameUi.screen.guessGameScreen

import android.content.Context

interface GuessGameScreenInteractionListener {
    fun onGamePlayClicked(gameId: String)
    fun onSelectDifficulty(difficultyId: Int)
    fun onStartGame(context: Context)
    fun onDismissDifficultyDialog()
}