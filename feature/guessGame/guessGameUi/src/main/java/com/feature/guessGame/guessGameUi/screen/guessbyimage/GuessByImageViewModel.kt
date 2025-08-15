package com.feature.guessGame.guessGameUi.screen.guessbyimage

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.feature.guessGame.guessGameUi.common.BaseViewModel
import com.feature.guessGame.guessGameUi.navigation.GuessGameDestinations
import com.feature.guessGame.guessGameUi.screen.guessGameScreen.mapper.UiGameLevel
import com.feature.guessGame.guessGameUi.screen.guessQuestionScreen.mapper.toUiLevel
import com.paris_2.domain.game.entity.GameSession
import com.paris_2.domain.game.usecases.MoveToNextQuestionUseCase
import com.paris_2.domain.game.usecases.guessActor.GuessActorSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GuessByImageViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val guessActorSessionUseCase: GuessActorSessionUseCase,
    private val moveToNextQuestionUseCase: MoveToNextQuestionUseCase,
) : BaseViewModel<GuessCharacterUIState>(GuessCharacterUIState()), GuessByImageInteractionListener {

    private val args = savedStateHandle.toRoute<GuessGameDestinations.GuessByImageScreen>()
    private val level = args.gameLevel
    private val questionType = args.questionType
    private var currentSession: GameSession? = null
    private var startTimeMillis: Long = 0
    private val time = when (level) {
        UiGameLevel.HARD -> 10
        UiGameLevel.MEDIUM -> 30
        UiGameLevel.EASY -> 45
    }


    init {
        Log.d("navTest", "GuessByImageViewModel")
        generateSession(level)
        currentSession?.let { session ->
            loadQuestion(session)
        } ?: Log.e("navTest", "Error: currentSession is null")
    }

    fun generateSession(questionType: UiGameLevel) {
        tryToExecute(
            execute = {
                Log.d("navTest", "generateSession Enter with $questionType")
                Log.d("navTest", "generateSession Enter with ${questionType.toUiLevel()}")
                val session = guessActorSessionUseCase.startNewSession(questionType.toUiLevel())
                currentSession = session
                startTimeMillis = System.currentTimeMillis()
                Log.d("navTest", "generateSession done with ${session.questions.size} questions")
                session
            },
            onSuccess = { session ->
                updateState(
                    newState = screenState.value.copy(
                        time = time
                    )
                )
                loadQuestion(session)
            },
            onError = {
                Log.e("navTest", "Error in generateSession $it")
                updateState(screenState.value.copy(error = it))
            }
        )
    }

    private fun loadQuestion(session: GameSession) {
        val firstQuestion = session.questions.firstOrNull()
        if (firstQuestion != null) {
            updateState(
                screenState.value.copy(
                    currentQuestion = session.currentQuestionIndex,
                    questionUiState = session.questions.map { it.toUiModel() },
                )
            )
        }
    }

    override fun onNextClicked() {
        updateState(
            newState = screenState.value.copy(
                time = time
            )
        )
        currentSession?.let { session ->
            val updatedSession = moveToNextQuestionUseCase(session)

            if (updatedSession.isCompleted) {
                val totalTimeSeconds =
                    ((System.currentTimeMillis() - startTimeMillis) / 1000).toInt()
                updatedSession.duration = totalTimeSeconds
                navigate(
                    GuessGameDestinations.FinishGameScreen(
                        totalGameTime = updatedSession.duration,
                        totalGamePoints = updatedSession.score,
                        gameType = questionType,
                        gameLevel = level,
                    )
                )
            } else {
                updateState(
                    screenState.value.copy(
                        currentQuestion = updatedSession.currentQuestionIndex,
                        questionUiState = updatedSession.questions.map { it.toUiModel() }
                    )
                )
            }
        } ?: Log.e("navTest", "No session found when onNextClicked called")
    }



    override fun onAnswerSelected(answer: String) {
        currentSession?.let { session ->
            val currentIndex = session.currentQuestionIndex
            val currentQuestion = session.questions.getOrNull(currentIndex)

            if (currentQuestion?.selectedAnswer != null) return

            if (currentQuestion != null) {
                currentQuestion.selectedAnswer = answer
                val isCorrect = answer == currentQuestion.correctAnswer
                if (isCorrect) {
                    updateScore()
                }
                updateState(
                    screenState.value.copy(
                        questionUiState = session.questions.map { it.toUiModel() },
                        isChoiceCorrect = isCorrect,
                    )
                )
            }
        } ?: Log.e("navTest", "No session found when onAnswerSelected called")
    }

    fun updateScore() {
        val points = when (level) {
            UiGameLevel.HARD -> 20
            UiGameLevel.MEDIUM -> 10
            UiGameLevel.EASY -> 5
        }
        Log.d("navTest", "updateScore: ${currentSession?.score}")
        currentSession?.let { session ->
            session.score += points
        }

        updateState(
            screenState.value.copy(
                score = screenState.value.score + points
            )
        )
    }



    override fun onHintUsed() {

    }

    override fun onTimeFinished() {
        onNextClicked()
    }

    override fun onDismissNotEnoughPointsDialog() {

    }

    override fun onCancelClick() {
        navigateUp()
    }


}