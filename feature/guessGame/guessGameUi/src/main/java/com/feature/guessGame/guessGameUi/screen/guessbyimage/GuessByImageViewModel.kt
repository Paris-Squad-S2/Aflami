package com.feature.guessGame.guessGameUi.screen.guessbyimage

import android.util.Log
import com.feature.guessGame.guessGameUi.common.BaseViewModel
import com.paris_2.domain.game.entity.GameSession
import com.paris_2.domain.game.usecases.guessActor.GuessActorSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GuessByImageViewModel @Inject constructor(
    private val guessActorSessionUseCase: GuessActorSessionUseCase,
) : BaseViewModel<GuessCharacterUIState>(GuessCharacterUIState()), GuessByImageInteractionListener {

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
                        question = uiQuestions
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

    override fun onHintClicked() {
        TODO("Not yet implemented")
    }

    override fun onGuessClicked(guess: String) {
        TODO("Not yet implemented")
    }

    override fun onNextClicked() {
        TODO("Not yet implemented")
    }

    override fun onBackClicked() {
        TODO("Not yet implemented")
    }

}