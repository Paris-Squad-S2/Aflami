package com.feature.guessGame.guessGameUi.screen.resultScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.feature.guessGame.guessGameUi.common.BaseViewModel
import com.feature.guessGame.guessGameUi.navigation.Destinations
import com.feature.guessGame.guessGameUi.navigation.QuestionType
import com.feature.guessGame.guessGameUi.screen.guessGameScreen.DifficultySettings
import com.feature.guessGame.guessGameUi.screen.guessGameScreen.mapper.toInt
import com.paris_2.domain.game.usecases.UpdatePointsUseCase
import com.paris_2.domain.user.usecase.GetAccountIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAccountIdUseCase: GetAccountIdUseCase,
    private val updatePointsUseCase: UpdatePointsUseCase,

    ) : BaseViewModel<ResultUiState>(ResultUiState()), ResultInteractionListener {

    private val args = savedStateHandle.toRoute<Destinations.FinishGameScreen>()
    private val gameLevel = args.gameLevel

    init {
        viewModelScope.launch {
            val userId = getAccountIdUseCase.invoke()
            updatePointsUseCase.invoke(userId!!, args.totalGamePoints)
        }
    }

    override fun onExitClicked() {
        navigate(destination = Destinations.Screen)
    }

    override fun onPlayAgainClicked() {
        val settings = DifficultySettings.getDifficultySettings(args.gameLevel.toInt())
        when (args.gameType) {
            QuestionType.ACTOR -> {
                navigate(
                    Destinations.GuessByImageScreen(
                        questionType = QuestionType.ACTOR,
                        totalQuestions = settings.numberOfQuestions,
                        timePerQuestion = settings.timePerQuestionSec,
                        pointsPerQuestion = settings.pointsPerQuestion,
                        imageType = QuestionType.ACTOR,
                        gameLevel = gameLevel
                    )
                )
            }

            QuestionType.POSTER -> {
                navigate(
                    Destinations.GuessByImageScreen(
                        questionType = QuestionType.POSTER,
                        totalQuestions = settings.numberOfQuestions,
                        timePerQuestion = settings.timePerQuestionSec,
                        pointsPerQuestion = settings.pointsPerQuestion,
                        imageType = QuestionType.POSTER,
                        gameLevel = gameLevel
                    )
                )
            }

            QuestionType.RELEASE_YEAR -> {
                navigate(
                    Destinations.GuessQuestionScreen(
                        questionType = QuestionType.RELEASE_YEAR,
                        totalQuestions = settings.numberOfQuestions,
                        timePerQuestion = settings.timePerQuestionSec,
                        pointsPerQuestion = settings.pointsPerQuestion,
                        gameLevel = gameLevel
                    )
                )
            }

            QuestionType.GENRE -> {
                navigate(
                    Destinations.GuessQuestionScreen(
                        questionType = QuestionType.GENRE,
                        totalQuestions = settings.numberOfQuestions,
                        timePerQuestion = settings.timePerQuestionSec,
                        pointsPerQuestion = settings.pointsPerQuestion,
                        gameLevel = gameLevel
                    )
                )
            }
        }
    }

    override fun onBackToMenuClicked() {
        navigate(destination = Destinations.Screen)
    }

}