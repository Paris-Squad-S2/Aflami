package com.feature.guessGame.guessGameUi.screen.guessQuestionScreen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.feature.guessGame.guessGameUi.common.BaseViewModel
import com.feature.guessGame.guessGameUi.navigation.GuessGameDestinations
import com.feature.guessGame.guessGameUi.navigation.QuestionType
import com.feature.guessGame.guessGameUi.screen.guessQuestionScreen.mapper.toUiLevel
import com.paris_2.domain.game.entity.GameSession
import com.paris_2.domain.game.usecases.MoveToNextQuestionUseCase
import com.paris_2.domain.game.usecases.RemoveAnswerHintUseCase
import com.paris_2.domain.game.usecases.SubmitAnswerUseCase
import com.paris_2.domain.game.usecases.whenIsReleased.WhenIsReleasedSessionUseCase
import com.paris_2.domain.game.usecases.whichGenre.WhichGenreSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GuessQuestionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val whenIsReleasedSessionUseCase: WhenIsReleasedSessionUseCase,
    private val whichGenreSessionUseCase: WhichGenreSessionUseCase,
    private val submitAnswerUseCase: SubmitAnswerUseCase,
    private val removeAnswerHintUseCase: RemoveAnswerHintUseCase,
    private val moveToNextQuestionUseCase: MoveToNextQuestionUseCase,

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
    private lateinit var session: GameSession


    init {
        loadQuestions()
    }

    private fun loadQuestions() {
        tryToExecute(
            onSuccess = { gameSession ->
                session = gameSession
                val first = session.getCurrentQuestion()
                updateState(
                    screenState.value.copy(
                        questionText = first?.content ?: "",
                        answers = first?.options?.map { it.selectedAnswer }.orEmpty(),
                        remainingAnswers = first?.options?.map { it.selectedAnswer }.orEmpty(),
                        correctAnswer = first?.correctAnswer ?: ""
                    )
                )
            },
            onError = { error -> }
        ) {
            when (questionType) {
                QuestionType.RELEASE_YEAR -> whenIsReleasedSessionUseCase.startNewSession(args.gameLevel.toUiLevel())
                QuestionType.GENRE -> whichGenreSessionUseCase.startNewSession(args.gameLevel.toUiLevel())
                QuestionType.ACTOR -> TODO()
            }
        }
    }


    override fun onAnswerSelected(answer: String) {
        if (!::session.isInitialized) return

        submitAnswerUseCase(session, answer)

        val currentQuestion = session.getCurrentQuestion()

        updateState(
            screenState.value.copy(
                selectedAnswer = answer,
                questionText = currentQuestion?.content ?: "",
                answers = currentQuestion?.options?.map { it.selectedAnswer }.orEmpty(),
                remainingAnswers = currentQuestion?.options?.map { it.selectedAnswer }.orEmpty(),
                correctAnswer = currentQuestion?.correctAnswer ?: "",
                userPoints = session.score
            )
        )
    }


    override fun onHintUsed() {
        val state = screenState.value
        if (!::session.isInitialized) return

        when (val result = removeAnswerHintUseCase(session, state.hintUsed, state.userPoints)) {
            is RemoveAnswerHintUseCase.UseHintResult.NotEnoughPoints -> {
                updateState(state.copy(showNotEnoughPointsDialog = true))
            }

            is RemoveAnswerHintUseCase.UseHintResult.AlreadyUsed -> {
            }

            is RemoveAnswerHintUseCase.UseHintResult.Success -> {
                updateState(
                    state.copy(
                        userPoints = state.userPoints - 10,
                        hintUsed = true,
                        remainingAnswers = result.updatedQuestion.options.map { it.selectedAnswer }
                    )
                )
            }
        }
    }

    override fun onNextClicked() {
        if (!::session.isInitialized) return

        val updatedSession = moveToNextQuestionUseCase(session)

        if (updatedSession.isCompleted) {
            navigate(GuessGameDestinations.FinishGameScreen)
        } else {
            val nextQuestion = updatedSession.getCurrentQuestion()
            updateState(
                screenState.value.copy(
                    currentStep = updatedSession.currentQuestionIndex + 1,
                    questionText = nextQuestion?.content ?: "",
                    answers = nextQuestion?.options?.map { it.selectedAnswer }.orEmpty(),
                    remainingAnswers = nextQuestion?.options?.map { it.selectedAnswer }.orEmpty(),
                    correctAnswer = nextQuestion?.correctAnswer ?: "",
                    selectedAnswer = null,
                    hintUsed = false
                )
            )
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
