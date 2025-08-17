package com.feature.guessGame.guessGameUi.screen.guessbyimage

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.feature.guessGame.guessGameUi.common.BaseViewModel
import com.feature.guessGame.guessGameUi.navigation.Destinations
import com.feature.guessGame.guessGameUi.navigation.QuestionType
import com.feature.guessGame.guessGameUi.screen.guessGameScreen.mapper.UiGameLevel
import com.feature.guessGame.guessGameUi.screen.guessQuestionScreen.getTitleResId
import com.feature.guessGame.guessGameUi.screen.guessQuestionScreen.mapper.toUiLevel
import com.paris_2.domain.game.entity.GameSession
import com.paris_2.domain.game.entity.Question
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

    private val args = savedStateHandle.toRoute<Destinations.GuessByImageScreen>()
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
                    Destinations.FinishGameScreen(
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
                        isChoiceCorrect = false,
                        hintUsed = currentSession?.getCurrentQuestion()?.usedHint ?: false
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
                        hintUsed = currentQuestion.usedHint,
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
        val session = currentSession ?: run {
            Log.e("GuessByImageVM", "No active session when hint requested")
            return
        }

        val currentQuestion = session.getCurrentQuestion() ?: run {
            Log.e("GuessByImageVM", "No current question when hint requested")
            return
        }

        when {
            currentQuestion.usedHint -> {
                Log.d("GuessByImageVM", "Hint already used for this question")
                return
            }

            currentQuestion.selectedAnswer != null -> {
                Log.d("GuessByImageVM", "Cannot use hint after answering")
                return
            }
        }

        tryToExecute(
            execute = {
                val userId = getAccountIdUseCase()
                    ?: throw IllegalStateException("No user account found")

                val hintUsed = useHintUseCase(session, userId)

                if (hintUsed) {
                    currentQuestion.usedHint = true
                    return@tryToExecute HintUsageResult.Success(currentQuestion)
                } else {
                    return@tryToExecute HintUsageResult.NotEnoughPoints
                }
            },
            onSuccess = { result ->
                when (result) {
                    is HintUsageResult.Success -> {
                        applyHint(result.updatedQuestion)
                        updateState(screenState.value.copy(hintUsed = true))
                        Log.d(
                            "GuessByImageVM",
                            "Hint applied successfully. $HINT_COST points deducted."
                        )
                    }

                    HintUsageResult.NotEnoughPoints -> {
                        updateState(screenState.value.copy(showNotEnoughPointsDialog = true))
                        Log.d("GuessByImageVM", "Not enough points for hint. Required: $HINT_COST")
                    }

                    is HintUsageResult.Failed -> {
                        Log.e("GuessByImageVM", "Hint usage failed: ${result.error}")
                    }

                    HintUsageResult.AlreadyUsed -> {
                        Log.d("GuessByImageVM", "Hint already used")
                    }
                }
            },
            onError = { error ->
                Log.e("GuessByImageVM", "Error using hint: $error")
            }
        )
    }


    private fun applyHint(updatedQuestion: Question) {
        val session = currentSession ?: return

        val questionIndex = session.questions.indexOfFirst { it.id == updatedQuestion.id }
        if (questionIndex != -1) {
            session.questions = session.questions.toMutableList().also { questions ->
                questions[questionIndex] = questions[questionIndex].copy(usedHint = true)
            }
        }

        updateState(
            screenState.value.copy(
                questionUiState = session.questions.map { it.toUiModel() }
            )
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


    private sealed class HintUsageResult {
        data class Success(val updatedQuestion: Question) : HintUsageResult()
        object AlreadyUsed : HintUsageResult()
        object NotEnoughPoints : HintUsageResult()
        data class Failed(val error: String) : HintUsageResult()
    }

    companion object {
        private const val HINT_COST = 10
    }
}