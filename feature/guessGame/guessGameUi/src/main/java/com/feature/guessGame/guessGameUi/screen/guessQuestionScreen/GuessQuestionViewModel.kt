package com.feature.guessGame.guessGameUi.screen.guessQuestionScreen

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.feature.guessGame.guessGameUi.common.BaseViewModel
import com.feature.guessGame.guessGameUi.navigation.GuessGameDestinations
import com.feature.guessGame.guessGameUi.navigation.QuestionType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GuessQuestionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle

) : GuessQuestionInteractionListener,
    BaseViewModel<GuessQuestionUiState>(GuessQuestionUiState()) {

    private val questionType: QuestionType =
        savedStateHandle.toRoute<GuessGameDestinations.GuessQuestionScreen>().questionType

    init {
        loadQuestionsForType()
    }

    private fun loadQuestionsForType() {
        when (questionType) {
            QuestionType.RELEASE_YEAR -> {}
            QuestionType.GENRE -> {}
        }
    }



    override fun onAnswerSelected(answer: String) {

    }

    override fun onHintUsed() {
    }

    override fun onNextClicked() {
    }

    override fun onTimeFinished() {
    }

}