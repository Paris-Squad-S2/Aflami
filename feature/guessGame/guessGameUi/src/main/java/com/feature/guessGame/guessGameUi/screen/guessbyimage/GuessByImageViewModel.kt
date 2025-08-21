package com.feature.guessGame.guessGameUi.screen.guessbyimage

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavOptions
import androidx.navigation.toRoute
import com.feature.guessGame.guessGameUi.HintUsageResult
import com.feature.guessGame.guessGameUi.common.BaseViewModel
import com.feature.guessGame.guessGameUi.navigation.Destinations
import com.feature.guessGame.guessGameUi.navigation.QuestionType
import com.feature.guessGame.guessGameUi.screen.guessGameScreen.mapper.UiGameLevel
import com.feature.guessGame.guessGameUi.screen.guessQuestionScreen.getTitleResId
import com.feature.guessGame.guessGameUi.screen.guessQuestionScreen.mapper.toUiLevel
import com.paris.domain.game.entity.GameSession
import com.paris.domain.game.entity.Question
import com.paris.domain.game.usecases.GenerateGuessActorSessionUseCase
import com.paris.domain.game.usecases.GenerateGuessMovieSessionUseCase
import com.paris.domain.game.usecases.MoveToNextQuestionUseCase
import com.paris.domain.game.usecases.UseHintUseCase
import com.paris.domain.user.usecase.auth.GetAccountIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GuessByImageViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val generateGuessMovieSessionUseCase: GenerateGuessMovieSessionUseCase,
    private val generateGuessActorSessionUseCase: GenerateGuessActorSessionUseCase,
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

        updateState(screenState.value.copy(isLoading = true))

        tryToExecute(
            execute = {
                val session =
                    if (questionType == QuestionType.ACTOR) generateGuessActorSessionUseCase.startNewSession(
                        gameLevel.toUiLevel()
                    ) else
                        generateGuessMovieSessionUseCase.startNewSession(gameLevel.toUiLevel())
                currentSession = session
                startTimeMillis = System.currentTimeMillis()
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
        }
    }

    override fun onNextClicked() {
        currentSession?.let { session ->
            val updatedSession = moveToNextQuestionUseCase(session)
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
                    ),
                    NavOptions.Builder()
                        .setPopUpTo(0, true)
                        .build()
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
        }
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
        }
    }

    fun updateScore() {
        val points = when (level) {
            UiGameLevel.HARD -> 20
            UiGameLevel.MEDIUM -> 10
            UiGameLevel.EASY -> 5
        }
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
        val session = currentSession ?: return
        val currentQuestion = session.getCurrentQuestion()


        if (!canUseHint(currentQuestion)) return

        tryToExecute(
            execute = { handleHintUse(session, currentQuestion) },
            onSuccess = { handleHintResult(it) },
            onError = {
                updateState(screenState.value.copy(showNotEnoughPointsDialog = true, isLoading = false))
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

    override fun onRetry() {
        updateState(
            screenState.value.copy(
                error = null,
                isLoading = true
            )
        )
        generateSession(level)
    }


    private fun canUseHint(question: Question?): Boolean {
        return when {
            question?.usedHint == true -> {
                false
            }

            question?.selectedAnswer != null -> {
                false
            }

            else -> true
        }
    }

    private suspend fun handleHintUse(session: GameSession, question: Question?): HintUsageResult {
        val userId = getAccountIdUseCase() ?: -1
        val hintUsed = useHintUseCase(session, userId)

        return if (hintUsed) {
            question?.usedHint = true
            HintUsageResult.Success(question)
        } else {
            HintUsageResult.NotEnoughPoints
        }
    }

    private fun handleHintResult(result: HintUsageResult) {
        when (result) {
            is HintUsageResult.Success -> {
                applyHint(result.updatedQuestion)
                updateState(screenState.value.copy(hintUsed = true))
            }

            is HintUsageResult.NotEnoughPoints -> updateState(
                screenState.value.copy(
                    showNotEnoughPointsDialog = true
                )
            )


            is HintUsageResult.Failed -> updateState(screenState.value.copy(error = result.error))


            is HintUsageResult.AlreadyUsed -> updateState(screenState.value.copy(error = "Hint already used"))

        }
    }

    private fun applyHint(updatedQuestion: Question?) {
        val session = currentSession ?: return

        val index = session.questions.indexOfFirst { it.id == updatedQuestion?.id }
        if (index != -1) {
            session.questions = session.questions.toMutableList()
                .also { it[index] = it[index].copy(usedHint = true) }
        }

        updateState(
            screenState.value.copy(
                questionUiState = session.questions.map { it.toUiModel() }
            )
        )
    }

    companion object {
        const val IMAGE_BASE_URL =  "https://image.tmdb.org/t/p/w500"
    }
}