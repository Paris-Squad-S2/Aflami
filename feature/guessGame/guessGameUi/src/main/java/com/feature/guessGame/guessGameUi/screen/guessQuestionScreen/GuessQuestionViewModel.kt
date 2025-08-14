package com.feature.guessGame.guessGameUi.screen.guessQuestionScreen

import android.util.Log
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
    BaseViewModel<GuessQuestionUiState>(
        GuessQuestionUiState(
            totalQuestions = savedStateHandle.toRoute<GuessGameDestinations.GuessQuestionScreen>().totalQuestions,
            timePerQuestion = savedStateHandle.toRoute<GuessGameDestinations.GuessQuestionScreen>().timePerQuestion,
            pointsPerQuestion = savedStateHandle.toRoute<GuessGameDestinations.GuessQuestionScreen>().pointsPerQuestion,
            gameTitle = savedStateHandle.toRoute<GuessGameDestinations.GuessQuestionScreen>().questionType.name
        )
    ) {

    init {
        Log.d("navTest", "GuessQuestionViewModel")
    }
    private val args = savedStateHandle.toRoute<GuessGameDestinations.GuessQuestionScreen>()
    private val questionType = args.questionType
    private var questions: List<Question> = emptyList()
    private var currentIndex = 0


    init {
//       loadQuestions()
//        getFakeGuessQuestionUiState()

    }


    init {

        questions = listOf(
            Question(
                text = "In which year was 'Batman' released?",
                answers = listOf("2008", "2010", "2012", "2014"),
                correctAnswer = "2010"
            ),
            Question(
                text = "Which genre does 'Inception' belong to?",
                answers = listOf("Action", "Sci-Fi", "Comedy", "Drama"),
                correctAnswer = "Sci-Fi"
            )
        )

        updateState(
            screenState.value.copy(
                totalQuestions = args.totalQuestions,
                timePerQuestion = args.timePerQuestion,
                pointsPerQuestion = args.pointsPerQuestion,
                questionText = questions.first().text,
                answers = questions.first().answers,
                remainingAnswers = questions.first().answers,
                correctAnswer = questions.first().correctAnswer
            )
        )
    }


    private fun loadQuestions() {

    }

    override fun onAnswerSelected(answer: String) {
        val state = screenState.value
        val isCorrect = answer == state.correctAnswer
        updateState(
            state.copy(
                selectedAnswer = answer,
                remainingAnswers = state.remainingAnswers,
            )
        )
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
        onNextClicked()
    }

    override fun onDismissNotEnoughPointsDialog() {
        updateState(screenState.value.copy(showNotEnoughPointsDialog = false))
    }

    override fun onCancelClick() {
        navigate(GuessGameDestinations.GuessGameScreen)
    }
}
