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
            val userId = runCatching { getAccountIdUseCase() }.getOrNull()
            userId?.let { id ->
                updatePointsUseCase(id, args.totalGamePoints)
            }
        }
    }

    override fun onExitClicked() {
        navigate(Destinations.Screen)
    }

    override fun onBackToMenuClicked() {
        navigate(Destinations.Screen)
    }

    override fun onPlayAgainClicked() {
        val settings = DifficultySettings.getDifficultySettings(gameLevel.toInt())
        val destination = when (args.gameType) {
            QuestionType.ACTOR, QuestionType.POSTER -> {
                Destinations.GuessByImageScreen(
                    questionType = args.gameType,
                    totalQuestions = settings.numberOfQuestions,
                    timePerQuestion = settings.timePerQuestionSec,
                    pointsPerQuestion = settings.pointsPerQuestion,
                    imageType = args.gameType,
                    gameLevel = gameLevel
                )
            }

            QuestionType.RELEASE_YEAR, QuestionType.GENRE -> {
                Destinations.GuessQuestionScreen(
                    questionType = args.gameType,
                    totalQuestions = settings.numberOfQuestions,
                    timePerQuestion = settings.timePerQuestionSec,
                    pointsPerQuestion = settings.pointsPerQuestion,
                    gameLevel = gameLevel
                )
            }
        }
        navigate(destination)
    }
}
