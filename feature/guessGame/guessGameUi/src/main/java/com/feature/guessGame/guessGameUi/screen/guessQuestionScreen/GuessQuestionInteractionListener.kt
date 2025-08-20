package com.feature.guessGame.guessGameUi.screen.guessQuestionScreen

interface GuessQuestionInteractionListener {
    fun onAnswerSelected(answer: String)
    fun onHintUsed()
    fun onNextClicked()
    fun onTimeFinished()
   fun onDismissNotEnoughPointsDialog()
    fun onCancelClick()

    fun onRetry()
}
