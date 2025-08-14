package com.feature.guessGame.guessGameUi.screen.guessbyimage

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.feature.guessGame.guessGameUi.common.BaseViewModel
import com.feature.guessGame.guessGameUi.navigation.GuessGameDestinations
import com.paris_2.domain.game.entity.GameSession
import com.paris_2.domain.game.usecases.guessActor.GuessActorSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GuessByImageViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val guessActorSessionUseCase: GuessActorSessionUseCase,
) : BaseViewModel<GuessCharacterUIState>(GuessCharacterUIState()), GuessByImageInteractionListener {

    private val args = savedStateHandle.toRoute<GuessGameDestinations.GuessByImageScreen>()

    init {
        Log.d("navTest", "GuessByImageViewModel")
        loadQuestionSession()
    }

    fun loadQuestionSession() {
        tryToExecute(
            execute = { guessActorSessionUseCase.startNewSession(GameSession.GameLevel.EASY) },
            onSuccess = { session ->
                val uiQuestions = session.questions.map { q ->
                    Question(
                        image = q.content,
                        answers = q.options.map { it.selectedAnswer },
                        isCorrect = false,
                        isSelected = false
                    )
                }

                updateState(
                    newState = screenState.value.copy(
                        question = uiQuestions,
                        screenTitle = args.questionType.name
                    )
                )
            },
            onError = { error ->
                updateState(
                    newState = screenState.value.copy(
                        error = error
                    )
                )
            }
        )
    }

    override fun onAnswerSelected(answer: String) {

    }

    override fun onHintUsed() {

    }

    override fun onNextClicked() {

    }

    override fun onTimeFinished() {

    }

    override fun onDismissNotEnoughPointsDialog() {

    }

    override fun onCancelClick() {
        navigateUp()
    }


}