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


    init {
        generateSession(args.gameLevel)
    }

    private fun generateSession(level: UiGameLevel) {
        tryToExecute(
            execute = {
                when (questionType) {
                    QuestionType.RELEASE_YEAR -> whenIsReleasedSessionUseCase.startNewSession(level.toUiLevel())
                    QuestionType.GENRE -> whichGenreSessionUseCase.startNewSession(level.toUiLevel())
                    QuestionType.ACTOR, QuestionType.POSTER -> TODO()
                }
            },
            onSuccess = { session ->
                currentSession = session
                loadQuestion(session)
            },
            onError = { error ->
                Log.e("GuessQuestionVM", "Error generating session: $error")
            }
        )
    }

    private fun loadQuestion(session: GameSession) {
        val currentQ = session.getCurrentQuestion()?.toUiQuestion()
        if (currentQ == null) return

        updateState(
            screenState.value.copy(
                session = GameSessionUi(
                    id = session.id,
                    level = session.level.name,
                    currentQuestion = currentQ.content,
                    score = session.score,
                    isCompleted = session.isCompleted
                ),
                totalQuestions = session.questions.size,
                currentStep = session.currentQuestionIndex,
                questionText = currentQ.content,
                answers = currentQ.options.map { it.text },
                correctAnswer = currentQ.options.firstOrNull { it.isCorrect }?.text,
                remainingAnswers = currentQ.options.map { it.text },
                selectedAnswer = currentQ.selectedAnswer,
                hintUsed = currentQ.hintUsed
            )
        )

        Log.d("GuessQuestionVM", "Loaded questions: ${session.questions.map { it.content }}")
    }

    override fun onAnswerSelected(answer: String) {
        currentSession?.let { session ->
            val currentQ = session.getCurrentQuestion() ?: return
            if (currentQ.selectedAnswer != null) return

            currentQ.selectedAnswer = answer
            val isCorrect = answer == currentQ.correctAnswer

            updateState(
                screenState.value.copy(
                    answers = currentQ.options.map { it.text },
                    selectedAnswer = answer,
                    correctAnswer = currentQ.correctAnswer,
                    hintUsed = currentQ.usedHint
                )
            )

            submitAnswerUseCase(session, answer)
        }
    }

    override fun onNextClicked() {
        currentSession?.let { session ->
            val updatedSession = moveToNextQuestionUseCase(session)
            currentSession = updatedSession

            if (updatedSession.isCompleted) {
                navigate(
                    GuessGameDestinations.FinishGameScreen(
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
    }

    override fun onHintUsed() {
        currentSession?.let { session ->
            val currentQ = session.getCurrentQuestion() ?: return
            val currentPoints = screenState.value.userPoints

            when (val result = removeAnswerHintUseCase(
                gameSession = session,
                usedHint = currentQ.usedHint,
                currentPoints = currentPoints
            )) {
                is RemoveAnswerHintUseCase.UseHintResult.Success -> {
                    val updatedQuestion = result.updatedQuestion
                    val index = session.currentQuestionIndex
                    session.questions = session.questions.toMutableList().also {
                        it[index] = updatedQuestion.copy(usedHint = true)
                    }

                    updateState(
                        screenState.value.copy(
                            answers = updatedQuestion.options.map { it.text },
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
            }
        }
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
}
