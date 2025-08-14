package com.feature.guessGame.guessGameUi.screen.guessQuestionScreen

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.feature.guessGame.guessGameUi.common.BaseViewModel
import com.feature.guessGame.guessGameUi.navigation.GuessGameDestinations
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GuessQuestionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : GuessQuestionInteractionListener,
    BaseViewModel<GuessQuestionUiState>(GuessQuestionUiState()) {

    private val args = savedStateHandle.toRoute<GuessGameDestinations.GuessQuestionScreen>()
    private val questionType = args.questionType
    private val totalQuestions = args.totalQuestions
    private val timePerQuestion = args.timePerQuestion
    private val pointsPerQuestion = args.pointsPerQuestion

    private var questions: List<Question> = emptyList()
    private var currentIndex = 0

    init {
//       loadQuestions()
//        getFakeGuessQuestionUiState()

    }

    private fun loadQuestions() {

    }

    override fun onAnswerSelected(answer: String) {
        updateState(screenState.value.copy(selectedAnswer = answer))
    }

    override fun onHintUsed() {
        val state = screenState.value
        if (state.hintUsed) return

        if (state.userPoints < 10) {
            updateState(screenState.value.copy(showNotEnoughPointsDialog = true))
            return
        }

        val wrongAnswers = state.remainingAnswers.filterNot { it == state.correctAnswer }
        val answerToRemove = wrongAnswers.randomOrNull()

        updateState(
            screenState.value.copy(
                userPoints = state.userPoints - 10,
                hintUsed = true,
                remainingAnswers = state.remainingAnswers.filterNot { ans -> ans == answerToRemove }
            )
        )
    }

    override fun onNextClicked() {
        if (currentIndex + 1 < questions.size) {
            currentIndex++
            val nextQuestion = questions[currentIndex]

            updateState(
                screenState.value.copy(
                    currentStep = currentIndex + 1,
                    questionText = nextQuestion.text,
                    answers = nextQuestion.answers,
                    remainingAnswers = nextQuestion.answers,
                    correctAnswer = nextQuestion.correctAnswer,
                    selectedAnswer = null,
                    hintUsed = false
                )
            )
        } else {
            navigate(GuessGameDestinations.FinishGameScreen)
        }
    }

    override fun onTimeFinished() {
        updateState(screenState.value.copy(showNotEnoughPointsDialog = false))
    }

    override fun onDismissNotEnoughPointsDialog() {
        updateState(screenState.value.copy(showNotEnoughPointsDialog = false))
    }

    override fun onCancelClick() {
        navigate(GuessGameDestinations.GuessGameScreen)
    }
}
