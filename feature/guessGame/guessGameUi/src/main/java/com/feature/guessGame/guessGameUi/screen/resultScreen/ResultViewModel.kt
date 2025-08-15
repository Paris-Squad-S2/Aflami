package com.feature.guessGame.guessGameUi.screen.resultScreen

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.feature.guessGame.guessGameUi.common.BaseViewModel
import com.feature.guessGame.guessGameUi.navigation.GuessGameDestinations
import com.feature.guessGame.guessGameUi.navigation.QuestionType
import com.feature.guessGame.guessGameUi.screen.guessGameScreen.DifficultySettings
import com.feature.guessGame.guessGameUi.screen.guessGameScreen.mapper.UiGameLevel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<ResultUiState>(ResultUiState()), ResultInteractionListener {

    private val args = savedStateHandle.toRoute<GuessGameDestinations.FinishGameScreen>()

    override fun onExitClicked() {
        navigate(destination = GuessGameDestinations.GuessGameScreen)
    }

    override fun onPlayAgainClicked() {
        val settings = DifficultySettings.getDifficultySettings(difficultyId = 0)
        when (args.gameType) {
            QuestionType.ACTOR -> {
                navigate(
                    GuessGameDestinations.GuessByImageScreen(
                        questionType = QuestionType.ACTOR,
                        totalQuestions = settings.numberOfQuestions,
                        timePerQuestion = settings.timePerQuestionSec,
                        pointsPerQuestion = settings.pointsPerQuestion,
                        imageType = QuestionType.ACTOR
                    )
                )
            }

            QuestionType.POSTER -> {
                navigate(
                    GuessGameDestinations.GuessByImageScreen(
                        questionType = QuestionType.POSTER,
                        totalQuestions = settings.numberOfQuestions,
                        timePerQuestion = settings.timePerQuestionSec,
                        pointsPerQuestion = settings.pointsPerQuestion,
                        imageType = QuestionType.POSTER
                    )
                )
            }

            QuestionType.RELEASE_YEAR -> {
                navigate(
                    GuessGameDestinations.GuessQuestionScreen(
                        questionType = QuestionType.RELEASE_YEAR,
                        totalQuestions = settings.numberOfQuestions,
                        timePerQuestion = settings.timePerQuestionSec,
                        pointsPerQuestion = settings.pointsPerQuestion,
                        gameLevel = UiGameLevel.EASY
                    )
                )
            }

            QuestionType.GENRE -> {
                navigate(
                    GuessGameDestinations.GuessQuestionScreen(
                        questionType = QuestionType.GENRE,
                        totalQuestions = settings.numberOfQuestions,
                        timePerQuestion = settings.timePerQuestionSec,
                        pointsPerQuestion = settings.pointsPerQuestion,
                        gameLevel = UiGameLevel.EASY
                    )
                )
            }
        }
    }

    override fun onBackToMenuClicked() {
        navigate(destination = GuessGameDestinations.GuessGameScreen)
    }

}