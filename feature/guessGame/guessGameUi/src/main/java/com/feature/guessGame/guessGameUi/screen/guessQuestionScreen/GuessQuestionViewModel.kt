package com.feature.guessGame.guessGameUi.screen.guessQuestionScreen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.feature.guessGame.guessGameUi.common.BaseViewModel
import com.feature.guessGame.guessGameUi.navigation.Destinations
import com.feature.guessGame.guessGameUi.navigation.QuestionType
import com.feature.guessGame.guessGameUi.screen.guessGameScreen.mapper.UiGameLevel
import com.feature.guessGame.guessGameUi.screen.guessQuestionScreen.mapper.toUiLevel
import com.paris_2.domain.game.entity.GameSession
import com.paris_2.domain.game.entity.Question
import com.paris_2.domain.game.usecases.GetUserPointUseCase
import com.paris_2.domain.game.usecases.MoveToNextQuestionUseCase
import com.paris_2.domain.game.usecases.RemoveAnswerHintUseCase
import com.paris_2.domain.game.usecases.UseHintUseCase
import com.paris_2.domain.game.usecases.whenIsReleased.WhenIsReleasedSessionUseCase
import com.paris_2.domain.game.usecases.whichGenre.WhichGenreSessionUseCase
import com.paris_2.domain.user.usecase.GetAccountIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GuessQuestionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val whenIsReleasedSessionUseCase: WhenIsReleasedSessionUseCase,
    private val whichGenreSessionUseCase: WhichGenreSessionUseCase,
    private val removeAnswerHintUseCase: RemoveAnswerHintUseCase,
    private val moveToNextQuestionUseCase: MoveToNextQuestionUseCase,
    private val getUserPointUseCase: GetUserPointUseCase,
    private val getAccountIdUseCase: GetAccountIdUseCase,
    private val useHintUseCase: UseHintUseCase,
) : BaseViewModel<GuessQuestionUiState>(
    GuessQuestionUiState(
        totalQuestions = savedStateHandle.toRoute<Destinations.GuessQuestionScreen>().totalQuestions,
        timePerQuestion = savedStateHandle.toRoute<Destinations.GuessQuestionScreen>().timePerQuestion,
        pointsPerQuestion = savedStateHandle.toRoute<Destinations.GuessQuestionScreen>().pointsPerQuestion,
        gameTitle = savedStateHandle.toRoute<Destinations.GuessQuestionScreen>().questionType.name,
        session = GameSessionUi(
            level = savedStateHandle.toRoute<Destinations.GuessQuestionScreen>().gameLevel.name
        )
    )
), GuessQuestionInteractionListener {

    private val args = savedStateHandle.toRoute<Destinations.GuessQuestionScreen>()
    private val questionType = args.questionType
    private var currentSession: GameSession? = null
    private var gameStartTimeMillis: Long = 0L
    private val timePerQuestion = args.timePerQuestion

    init {
        generateSession(args.gameLevel)
    }

    private fun generateSession(level: UiGameLevel) {
        tryToExecute(
            execute = {
                updateState(screenState.value.copy(isLoading = true))
                val session = when (questionType) {
                    QuestionType.RELEASE_YEAR -> whenIsReleasedSessionUseCase.startNewSession(level.toUiLevel())
                    QuestionType.GENRE -> whichGenreSessionUseCase.startNewSession(level.toUiLevel())
                    QuestionType.ACTOR, QuestionType.POSTER -> TODO()
                }
                currentSession = session
                gameStartTimeMillis = System.currentTimeMillis()
                session
            },
            onSuccess = { session ->
                updateState(screenState.value.copy(time = timePerQuestion, isLoading = false))
                loadQuestion(session)
            },
            onError = { error ->
                Log.e("GuessQuestionVM", "Error generating session: $error")
                updateState(screenState.value.copy(error = error, isLoading = false))
            }
        )
    }

    private fun loadQuestion(session: GameSession) {
        session.getCurrentQuestion()?.let { question ->
            updateState(
                screenState.value.copy(
                    currentStep = session.currentQuestionIndex,
                    totalQuestions = session.questions.size,
                    questionUiState = session.questions.map { it.toUiQuestion(questionType) },
                    questionText = question.content,
                    answers = question.options.map { it.toUiAnswer(questionType) },
                    correctAnswer = question.options.firstOrNull { it.isCorrect }?.text,
                    remainingAnswers = question.options.map { it.toUiAnswer(questionType) },
                    selectedAnswer = question.selectedAnswer,
                    hintUsed = question.usedHint,
                    time = timePerQuestion
                )
            )
        }
    }

    override fun onAnswerSelected(answer: String) {
        val session = currentSession ?: return
        val question = session.getCurrentQuestion() ?: return
        if (question.selectedAnswer != null) return

        question.selectedAnswer = answer
        if (answer == question.correctAnswer) updateScore()

        updateState(
            screenState.value.copy(
                answers = question.options.map { it.toUiAnswer(questionType) },
                selectedAnswer = answer,
                correctAnswer = question.correctAnswer,
                hintUsed = question.usedHint,
                session = screenState.value.session?.copy(score = session.score)
            )
        )
    }

    override fun onNextClicked() {
        val session = currentSession ?: run {
            Log.e("GuessQuestionVM", "No session found when onNextClicked called")
            return
        }

        val updatedSession = moveToNextQuestionUseCase(session)
        currentSession = updatedSession

        updateState(
            screenState.value.copy(
                currentStep = updatedSession.currentQuestionIndex,
                time = timePerQuestion,
                questionUiState = updatedSession.questions.map { it.toUiQuestion(questionType) }
            )
        )

        if (updatedSession.isCompleted) {
            updatedSession.duration = calculateTotalGameTime()
            navigate(
                Destinations.FinishGameScreen(
                    totalGameTime = updatedSession.duration,
                    totalGamePoints = updatedSession.score,
                    gameType = questionType,
                    gameLevel = args.gameLevel
                )
            )
        } else {
            loadQuestion(updatedSession)
        }
    }

    override fun onHintUsed() {
        val session = currentSession ?: run {
            Log.e(TAG, "No active session when hint requested")
            return
        }

        val currentQuestion = session.getCurrentQuestion() ?: run {
            Log.e(TAG, "No current question when hint requested")
            return
        }

        if (currentQuestion.usedHint || currentQuestion.selectedAnswer != null) return

        tryToExecute(
            execute = { handleHint(session, currentQuestion) },
            onSuccess = { result -> handleHintResult(result) },
            onError = { error -> Log.e(TAG, "Error using hint: $error") }
        )
    }


    override fun onTimeFinished() = onNextClicked()

    override fun onDismissNotEnoughPointsDialog() {
        updateState(screenState.value.copy(showNotEnoughPointsDialog = false))
    }

    override fun onCancelClick() {
        navigateUp()
    }

    private fun calculateTotalGameTime(): Int =
        if (gameStartTimeMillis > 0) ((System.currentTimeMillis() - gameStartTimeMillis) / 1000).toInt() else 0

    private fun updateScore() {
        val points = when (args.gameLevel) {
            UiGameLevel.HARD -> 20
            UiGameLevel.MEDIUM -> 10
            UiGameLevel.EASY -> 5
        }
        currentSession?.let { session ->
            session.score += points
            updateState(screenState.value.copy(session = screenState.value.session?.copy(score = session.score)))
        }
    }


    private suspend fun handleHint(session: GameSession, question: Question): HintUsageResult {
        val userId = getAccountIdUseCase() ?: throw IllegalStateException("No user account found")
        val userPoints = getUserPointUseCase(userId)
        if (userPoints <= HINT_COST) return HintUsageResult.NotEnoughPoints

        return when (val hintResult =
            removeAnswerHintUseCase(session, question.usedHint, userPoints, HINT_COST)) {
            is RemoveAnswerHintUseCase.UseHintResult.Success -> {
                if (!useHintUseCase(session, userId)) return HintUsageResult.NotEnoughPoints
                val updatedQuestion = hintResult.updatedQuestion.copy(usedHint = true)
                updateSessionQuestion(session, updatedQuestion)
                HintUsageResult.Success(updatedQuestion)
            }

            RemoveAnswerHintUseCase.UseHintResult.AlreadyUsed -> HintUsageResult.AlreadyUsed
            RemoveAnswerHintUseCase.UseHintResult.NotEnoughPoints -> HintUsageResult.NotEnoughPoints
        }
    }

    private fun updateSessionQuestion(session: GameSession, updatedQuestion: Question) {
        val index = session.questions.indexOfFirst { it.id == updatedQuestion.id }
        if (index != -1) {
            session.questions =
                session.questions.toMutableList().also { it[index] = updatedQuestion }
        }
    }

    private fun handleHintResult(result: HintUsageResult) {
        when (result) {
            is HintUsageResult.Success -> {
                val updatedQuestion = result.updatedQuestion
                updateState(
                    screenState.value.copy(
                        questionText = updatedQuestion.content,
                        answers = updatedQuestion.options.map { it.toUiAnswer(questionType) },
                        remainingAnswers = updatedQuestion.options.map { it.toUiAnswer(questionType) },
                        hintUsed = true
                    )
                )
                Log.d(TAG, "Hint applied successfully. $HINT_COST points deducted.")
            }

            HintUsageResult.NotEnoughPoints -> {
                updateState(screenState.value.copy(showNotEnoughPointsDialog = true))
                Log.d(TAG, "Not enough points for hint. Required: $HINT_COST")
            }

            HintUsageResult.AlreadyUsed -> Log.d(TAG, "Hint already used")
            is HintUsageResult.Failed -> Log.e(TAG, "Hint usage failed: ${result.error}")
        }
    }

    private sealed class HintUsageResult {
        data class Success(val updatedQuestion: Question) : HintUsageResult()
        object AlreadyUsed : HintUsageResult()
        object NotEnoughPoints : HintUsageResult()
        data class Failed(val error: String) : HintUsageResult()
    }

    companion object {
        private const val HINT_COST = 10
        private const val TAG = "GuessQuestionVM"

    }
}

