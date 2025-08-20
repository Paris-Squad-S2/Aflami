package com.feature.guessGame.guessGameUi.screen.guessbyimage

interface GuessByImageInteractionListener {
    fun onAnswerSelected(answer: String)
    fun onHintUsed()
    fun onNextClicked()
    fun onTimeFinished()
    fun onDismissNotEnoughPointsDialog()
    fun onCancelClick()
    fun onRetry()
}