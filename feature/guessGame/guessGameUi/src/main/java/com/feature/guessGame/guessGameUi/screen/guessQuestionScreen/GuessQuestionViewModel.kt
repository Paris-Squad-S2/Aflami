package com.feature.guessGame.guessGameUi.screen.guessQuestionScreen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.feature.guessGame.guessGameUi.common.BaseViewModel
import com.feature.guessGame.guessGameUi.navigation.GuessGameDestinations
import com.feature.guessGame.guessGameUi.navigation.QuestionType
import com.feature.guessGame.guessGameUi.screen.guessGameScreen.mapper.UiGameLevel
import com.feature.guessGame.guessGameUi.screen.guessQuestionScreen.mapper.toUiLevel
import com.paris_2.domain.game.entity.GameSession
import com.paris_2.domain.game.usecases.GetUserPointUseCase
import com.paris_2.domain.game.usecases.MoveToNextQuestionUseCase
import com.paris_2.domain.game.usecases.RemoveAnswerHintUseCase
import com.paris_2.domain.game.usecases.SubmitAnswerUseCase
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

    ) : BaseViewModel<GuessQuestionUiState>(
    GuessQuestionUiState(
        totalQuestions = savedStateHandle.toRoute<GuessGameDestinations.GuessQuestionScreen>().totalQuestions,
        timePerQuestion = savedStateHandle.toRoute<GuessGameDestinations.GuessQuestionScreen>().timePerQuestion,
        pointsPerQuestion = savedStateHandle.toRoute<GuessGameDestinations.GuessQuestionScreen>().pointsPerQuestion,
        gameTitle = savedStateHandle.toRoute<GuessGameDestinations.GuessQuestionScreen>().questionType.name
    )
), GuessQuestionInteractionListener {

    private val args = savedStateHandle.toRoute<GuessGameDestinations.GuessQuestionScreen>()
    private val questionType = args.questionType
    private var currentSession: GameSession? = null
    private var gameStartTimeMillis: Long = 0L
    private val timePerQuestion = args.timePerQuestion

    init {
        generateSession(args.gameLevel)
        currentSession?.let { session ->
            loadQuestion(session)
        } ?: Log.e("QuestionViewModel", "Error: currentSession is null")
    }

    private fun generateSession(level: UiGameLevel) {
        tryToExecute(
            execute = {
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
                updateState(screenState.value.copy(time = timePerQuestion))
                loadQuestion(session)
            },
            onError = { error ->
                Log.e("GuessQuestionVM", "Error generating session: $error")
                updateState(screenState.value.copy(error = error))
            }
        )
    }

    private fun loadQuestion(session: GameSession) {
        val currentIndex = session.currentQuestionIndex
        val currentQuestion = session.getCurrentQuestion() ?: return

        updateState(
            screenState.value.copy(
                currentStep = currentIndex,
                totalQuestions = session.questions.size,
                questionUiState = session.questions.map { it.toUiQuestion() },
                questionText = currentQuestion.content,
                answers = currentQuestion.options.map { it.toUiAnswer() },
                correctAnswer = currentQuestion.options.firstOrNull { it.isCorrect }?.text,
                remainingAnswers = currentQuestion.options.map { it.text },
                selectedAnswer = currentQuestion.selectedAnswer,
                hintUsed = currentQuestion.usedHint,
                time = timePerQuestion,
            )
        )

        Log.d("GuessQuestionVM", "Loaded question ${currentIndex + 1}: ${currentQuestion.content}")
    }


    override fun onAnswerSelected(answer: String) {
        currentSession?.let { session ->
            val currentQuestion = session.getCurrentQuestion() ?: return
            if (currentQuestion.selectedAnswer != null) return

            currentQuestion.selectedAnswer = answer
            val questionNumber = session.currentQuestionIndex + 1

            val wasCorrect = answer == currentQuestion.correctAnswer
            if (wasCorrect) {
                val scoreBefore = session.score
                updateScore()
                Log.d(
                    "GuessQuestionVM",
                    "Answered Question $questionNumber Correct: true, Points added: ${session.score - scoreBefore}, " +
                            "Score before: $scoreBefore, Score now: ${session.score}"
                )
            } else {
                Log.d(
                    "GuessQuestionVM",
                    "Answered Question $questionNumber Correct: false, Points added: 0, Score before: ${session.score}, Score now: ${session.score}"
                )
            }

            updateState(
                screenState.value.copy(
                    answers = currentQuestion.options.map { it.toUiAnswer() },
                    selectedAnswer = answer,
                    correctAnswer = currentQuestion.correctAnswer,
                    hintUsed = currentQuestion.usedHint,
                    session = screenState.value.session?.copy(score = session.score)
                )
            )
        }
    }


    override fun onNextClicked() {
        currentSession?.let { session ->
            val updatedSession = moveToNextQuestionUseCase(session)
            currentSession = updatedSession

            updateState(
                screenState.value.copy(
                    currentStep = updatedSession.currentQuestionIndex,
                    time = timePerQuestion,
                    questionUiState = updatedSession.questions.map { it.toUiQuestion() }
                )
            )

            if (updatedSession.isCompleted) {
                val totalGameTimeSeconds = calculateTotalGameTime()
                updatedSession.duration = totalGameTimeSeconds
                navigate(
                    GuessGameDestinations.FinishGameScreen(
                        totalGameTime = totalGameTimeSeconds,
                        totalGamePoints = updatedSession.score,
                        gameType = questionType,
                        gameLevel = args.gameLevel
                    )
                )
            } else {
                loadQuestion(updatedSession)
            }
        } ?: Log.e("GuessQuestionVM", "No session found when onNextClicked called")
    }




    override fun onHintUsed() {
        tryToExecute(
            execute = {
                val userId = getAccountIdUseCase() ?: 0
                val currentPoints = getUserPointUseCase(userId)

                if (currentPoints < 10) {
                    return@tryToExecute RemoveAnswerHintUseCase.UseHintResult.NotEnoughPoints
                }

                currentSession?.let { session ->
                    val currentQ = session.getCurrentQuestion() ?: return@tryToExecute null
                    removeAnswerHintUseCase(
                        gameSession = session,
                        usedHint = currentQ.usedHint,
                        currentPoints = currentPoints
                    )
                }
            },
            onSuccess = { result ->
                when (result) {
                    is RemoveAnswerHintUseCase.UseHintResult.Success -> {
                        val updatedQuestion = result.updatedQuestion
                        val session = currentSession ?: return@tryToExecute
                        val index = session.questions.indexOfFirst { it.id == updatedQuestion.id }
                        if (index != -1) {
                            session.questions = session.questions.toMutableList().also {
                                it[index] = updatedQuestion.copy(usedHint = true)
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
                    }

                    RemoveAnswerHintUseCase.UseHintResult.AlreadyUsed -> {
                        Log.d("GuessQuestionVM", "Hint already used for this question")
                    }

                    RemoveAnswerHintUseCase.UseHintResult.NotEnoughPoints -> {
                        updateState(screenState.value.copy(showNotEnoughPointsDialog = true))
                    }

                    null -> Unit
                }
            },
            onError = { error ->
                Log.e("GuessQuestionVM", "Error using hint: $error")
            }
        )
    }



    override fun onTimeFinished() {
        onNextClicked()
    }

    override fun onDismissNotEnoughPointsDialog() {
        updateState(screenState.value.copy(showNotEnoughPointsDialog = false))
    }

    override fun onCancelClick() {
        navigateUp()
    }

    private fun calculateTotalGameTime(): Int {
        return if (gameStartTimeMillis > 0) {
            val currentTimeMillis = System.currentTimeMillis()
            val totalTimeMillis = currentTimeMillis - gameStartTimeMillis
            (totalTimeMillis / 1000).toInt()
        } else {
            Log.w("GuessQuestionVM", "Game start time not set, returning 0")
            0
        }
    }

    private fun updateScore() {
        val points = when (args.gameLevel) {
            UiGameLevel.HARD -> 20
            UiGameLevel.MEDIUM -> 10
            UiGameLevel.EASY -> 5
        }

        currentSession?.let { session ->
            val oldScore = session.score
            session.score += points

            Log.d(
                "GuessQuestionVM",
                "Answered Question ${session.currentQuestionIndex + 1} correctly, " +
                        "Points added: $points, Score before: $oldScore, Score now: ${session.score}"
            )

            updateState(
                screenState.value.copy(
                    session = screenState.value.session?.copy(
                        score = session.score
                    )
                )
            )
        }
    }
}