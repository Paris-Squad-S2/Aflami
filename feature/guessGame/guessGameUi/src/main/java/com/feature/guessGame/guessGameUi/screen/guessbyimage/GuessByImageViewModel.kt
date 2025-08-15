package com.feature.guessGame.guessGameUi.screen.guessbyimage

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.feature.guessGame.guessGameUi.common.BaseViewModel
import com.feature.guessGame.guessGameUi.navigation.GuessGameDestinations
import com.feature.guessGame.guessGameUi.navigation.QuestionType
import com.feature.guessGame.guessGameUi.screen.guessGameScreen.mapper.UiGameLevel
import com.feature.guessGame.guessGameUi.screen.guessQuestionScreen.getTitleResId
import com.feature.guessGame.guessGameUi.screen.guessQuestionScreen.mapper.toUiLevel
import com.paris_2.domain.game.entity.GameSession
import com.paris_2.domain.game.usecases.MoveToNextQuestionUseCase
import com.paris_2.domain.game.usecases.UseHintUseCase
import com.paris_2.domain.game.usecases.guessActor.GuessActorSessionUseCase
import com.paris_2.domain.game.usecases.guessMovieByPoster.GuessMovieSessionUseCase
import com.paris_2.domain.user.usecase.GetAccountIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GuessByImageViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val guessMovieSessionUseCase: GuessMovieSessionUseCase,
    private val guessActorSessionUseCase: GuessActorSessionUseCase,
    private val moveToNextQuestionUseCase: MoveToNextQuestionUseCase,
    private val useHintUseCase: UseHintUseCase,
    private val getAccountIdUseCase: GetAccountIdUseCase,
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
        generateSession(level)
        updateState(
            screenState.value.copy(
                screenTitle = questionType.getTitleResId()
            )
        )
        currentSession?.let { session ->
            loadQuestion(session)
        }
    }

    fun generateSession(gameLevel: UiGameLevel) {
        Log.d("TAG", "generateSession:${questionType} ")
        Log.d("TAG", "generateSession:${level} ")

        updateState(screenState.value.copy(isLoading = true))

        tryToExecute(
            execute = {
                val session =
                    if (questionType == QuestionType.ACTOR) guessActorSessionUseCase.startNewSession(
                        gameLevel.toUiLevel()
                    ) else
                        guessMovieSessionUseCase.startNewSession(gameLevel.toUiLevel())
                currentSession = session
                startTimeMillis = System.currentTimeMillis()
                Log.d("navTest", "generateSession done with ${session.questions.size} questions")
                session
            },
            onSuccess = { session ->
                updateState(
                    newState = screenState.value.copy(
                        time = time,
                        isLoading = false
                    )
                )
                loadQuestion(session)
            },
            onError = {
                Log.e("navTest", "Error in generateSession $it")
                updateState(
                    screenState.value.copy(
                        error = it, isLoading = false
                    )
                )
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
            val image = firstQuestion.content
            Log.d("image", "loadQuestion: $image")
        }
    }

    override fun onNextClicked() {
        currentSession?.let { session ->
            val updatedSession = moveToNextQuestionUseCase(session)
            Log.d(
                "image",
                "loadQuestion: ${updatedSession.questions.getOrNull(updatedSession.currentQuestionIndex)?.content}"
            )

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
                val newTime = when (level) {
                    UiGameLevel.HARD -> 10
                    UiGameLevel.MEDIUM -> 30
                    UiGameLevel.EASY -> 45
                }
                startTimeMillis = System.currentTimeMillis()

                updateState(
                    screenState.value.copy(
                        currentQuestion = updatedSession.currentQuestionIndex,
                        questionUiState = updatedSession.questions.map { it.toUiModel() },
                        time = newTime,
                        isChoiceCorrect = false
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
        val session = currentSession
        val currentQ = session?.getCurrentQuestion()
        if (currentQ?.usedHint == true) return

        tryToExecute(
            execute = {
                val userId = getAccountIdUseCase() ?: -1
                if (session != null && currentQ != null) {
                    val used = useHintUseCase(session, userId)
                    if (!used) {
                        updateState(screenState.value.copy(showNotEnoughPointsDialog = true))
                        return@tryToExecute
                    }
                    currentQ.usedHint = true
                    updateState(
                        screenState.value.copy(
                            questionUiState = session.questions.map { it.toUiModel() }
                        )
                    )
                } else {
                    updateState(screenState.value.copy(showNotEnoughPointsDialog = true))
                }
            },
            onError = { error ->
                Log.e("navTest", "Error using hint: $error")
                updateState(screenState.value.copy(error = error))
            }
        )
    }

    override fun onTimeFinished() {
        onNextClicked()
    }

    override fun onDismissNotEnoughPointsDialog() {
        updateState(
            newState = screenState.value.copy(
                showNotEnoughPointsDialog = false
            )
        )
    }

    override fun onCancelClick() {
        navigateUp()
    }
}