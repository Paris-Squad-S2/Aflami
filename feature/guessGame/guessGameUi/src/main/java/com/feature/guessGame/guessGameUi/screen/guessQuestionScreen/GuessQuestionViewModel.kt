package com.feature.guessGame.guessGameUi.screen.guessQuestionScreen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.feature.guessGame.guessGameUi.common.BaseViewModel
import com.feature.guessGame.guessGameUi.navigation.GuessGameDestinations
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
    private val whenIsReleasedSessionUseCase: WhenIsReleasedSessionUseCase,
    private val whichGenreSessionUseCase: WhichGenreSessionUseCase,
    private val removeAnswerHintUseCase: RemoveAnswerHintUseCase,
    private val moveToNextQuestionUseCase: MoveToNextQuestionUseCase,
    private val getUserPointUseCase: GetUserPointUseCase,
    private val getAccountIdUseCase: GetAccountIdUseCase,
    private val useHintUseCase: UseHintUseCase,
) : BaseViewModel<GuessQuestionUiState>(
    GuessQuestionUiState()
), GuessQuestionInteractionListener {

    private var questionType = QuestionType.RELEASE_YEAR
    private var currentSession: GameSession? = null
    private var gameStartTimeMillis: Long = 0L
    private var timePerQuestion = 45
    private var level: UiGameLevel = UiGameLevel.EASY

    fun initialization(gameLevel: UiGameLevel, questionType: QuestionType, timePerQuestion: Int) {
        level = gameLevel
        Log.d("TAG", "initialization: ${questionType}")
        this.questionType = questionType
        this.timePerQuestion = timePerQuestion
        generateSession(this.level)
        updateState(
            screenState.value.copy(
                screenTitle = this.questionType.getTitleResId()
            )
        )
        currentSession?.let { session ->
            loadQuestion(session)
        }
    }


//    init {
//        generateSession(args.gameLevel)
//    }

    private fun generateSession(gameLevel: UiGameLevel) {
        val time = when (level) {
            UiGameLevel.HARD -> 10
            UiGameLevel.MEDIUM -> 30
            UiGameLevel.EASY -> 45
        }

        tryToExecute(execute = {
            updateState(screenState.value.copy(isLoading = true))
            val session =
                if (questionType == QuestionType.RELEASE_YEAR) whenIsReleasedSessionUseCase.startNewSession(
                    gameLevel.toUiLevel()
                )
                else whichGenreSessionUseCase.startNewSession(gameLevel.toUiLevel())
            currentSession = session
            gameStartTimeMillis = System.currentTimeMillis()
            session
        }, onSuccess = { session ->
            loadQuestion(session)
            updateState(screenState.value.copy(time = time, isLoading = false))
        }, onError = { error ->
            updateState(screenState.value.copy(error = error, isLoading = false))
        })
    }

    private fun loadQuestion(session: GameSession) {
        session.getCurrentQuestion()?.let { question ->
            updateState(
                screenState.value.copy(
                    currentStep = session.currentQuestionIndex,
                    totalQuestions = session.questions.size,
                    questionUiState = session.questions.map { it.toUiQuestion() },
                    questionText = question.content,
                    answers = question.options.map { it.toUiAnswer() },
                    correctAnswer = question.options.firstOrNull { it.isCorrect }?.text,
                    remainingAnswers = question.options.map { it.text },
                    selectedAnswer = question.selectedAnswer,
                    hintUsed = question.usedHint,
                    time = timePerQuestion,
                )
            )
        }
    }

    override fun onAnswerSelected(answer: String) {
        val session = currentSession ?: return
        val question = session.getCurrentQuestion() ?: return
        if (question.selectedAnswer != null) return

        question.selectedAnswer = answer
        val wasCorrect = answer == question.correctAnswer

        if (wasCorrect) updateScore()

        updateState(
            screenState.value.copy(
                answers = question.options.map { it.toUiAnswer() },
                selectedAnswer = answer,
                correctAnswer = question.correctAnswer,
                hintUsed = question.usedHint,
                session = screenState.value.session?.copy(score = session.score)
            )
        )
        Log.d(
            "GuessQuestionVM",
            "Answered Question ${session.currentQuestionIndex + 1} Correct: $wasCorrect, Score now: ${session.score}"
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
                questionUiState = updatedSession.questions.map { it.toUiQuestion() })
        )

        if (updatedSession.isCompleted) {
            updatedSession.duration = calculateTotalGameTime()
            navigate(
                GuessGameDestinations.FinishGameScreen(
                    totalGameTime = updatedSession.duration,
                    totalGamePoints = updatedSession.score,
                    gameType = questionType,
                    gameLevel = level
                )
            )
        } else {
            loadQuestion(updatedSession)
        }
    }

    override fun onHintUsed() {
        val session = currentSession ?: run {
            Log.e("GuessQuestionVM", "No active session when hint requested")
            return
        }

        val currentQuestion = session.getCurrentQuestion() ?: run {
            Log.e("GuessQuestionVM", "No current question when hint requested")
            return
        }

        if (currentQuestion.usedHint) {
            Log.d("GuessQuestionVM", "Hint already used for this question")
            return
        }

        if (currentQuestion.selectedAnswer != null) {
            Log.d("GuessQuestionVM", "Cannot use hint after answering")
            return
        }

        tryToExecute(execute = {
            val userId =
                getAccountIdUseCase() ?: throw IllegalStateException("No user account found")

            val hintUsed = useHintUseCase(session, userId)

            if (hintUsed) {
                val hintResult =
                    removeAnswerHintUseCase(session, false, getUserPointUseCase(userId))

                if (hintResult is RemoveAnswerHintUseCase.UseHintResult.Success) {
                    return@tryToExecute HintUsageResult.Success(hintResult.updatedQuestion)
                } else {
                    return@tryToExecute HintUsageResult.Failed("Failed to remove wrong answer")
                }
            } else {
                return@tryToExecute HintUsageResult.NotEnoughPoints
            }
        }, onSuccess = { result ->
            when (result) {
                is HintUsageResult.Success -> {
                    applyHint(result.updatedQuestion)
                    Log.d("GuessQuestionVM", "Hint applied successfully. 10 points deducted.")
                }

                HintUsageResult.NotEnoughPoints -> {
                    updateState(screenState.value.copy(showNotEnoughPointsDialog = true))
                    Log.d("GuessQuestionVM", "Not enough points for hint. Required: $HINT_COST")
                }

                is HintUsageResult.Failed -> {
                    Log.e("GuessQuestionVM", "Hint usage failed: ${result.error}")
                }

                HintUsageResult.AlreadyUsed -> {
                    Log.d("GuessQuestionVM", "Hint already used")
                }
            }
        }, onError = { error ->
            Log.e("GuessQuestionVM", "Error using hint: $error")
        })
    }

    private fun applyHint(updatedQuestion: Question) {
        val session = currentSession ?: return

        val questionIndex = session.questions.indexOfFirst { it.id == updatedQuestion.id }
        if (questionIndex != -1) {
            session.questions = session.questions.toMutableList().also { questions ->
                questions[questionIndex] = updatedQuestion.copy(usedHint = true)
            }
        }

        updateState(
            screenState.value.copy(
                questionText = updatedQuestion.content,
                answers = updatedQuestion.options.map { it.toUiAnswer() },
                remainingAnswers = updatedQuestion.options.map { it.text },
                hintUsed = true
            )
        )

        Log.d(
            "GuessQuestionVM", "Hint applied: ${updatedQuestion.options.size} options remaining"
        )
    }

    override fun onTimeFinished() = onNextClicked()

    override fun onDismissNotEnoughPointsDialog() =
        updateState(screenState.value.copy(showNotEnoughPointsDialog = false))

    override fun onCancelClick() {
        navigateUp()
    }

    private fun calculateTotalGameTime(): Int {
        return if (gameStartTimeMillis > 0) {
            ((System.currentTimeMillis() - gameStartTimeMillis) / 1000).toInt()
        } else {
            Log.w("GuessQuestionVM", "Game start time not set, returning 0")
            0
        }
    }

    private fun updateScore() {
        val points = when (level) {
            UiGameLevel.HARD -> 20
            UiGameLevel.MEDIUM -> 10
            UiGameLevel.EASY -> 5
        }

        currentSession?.let { session ->
            val oldScore = session.score
            session.score += points
            updateState(
                screenState.value.copy(
                    session = screenState.value.session?.copy(score = session.score)
                )
            )
            Log.d(
                "GuessQuestionVM",
                "Answered Question ${session.currentQuestionIndex + 1} correctly, Points added: $points, Score now: ${session.score}"
            )
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
    }
}
