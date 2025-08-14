package com.feature.guessGame.guessGameUi.screen.guessbyimage

import com.feature.guessGame.guessGameUi.common.BaseViewModel
import com.paris_2.domain.game.entity.GameSession
import com.paris_2.domain.game.usecases.guessActor.GuessActorSessionUseCase
import javax.inject.Inject

class GuessByImageViewModel @Inject constructor(
    private val guessActorSessionUseCase: GuessActorSessionUseCase,
) : BaseViewModel<GuessCharacterUIState>(GuessCharacterUIState()) {

    init {
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

}