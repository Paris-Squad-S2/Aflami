package com.feature.guessGame.guessGameUi.screen.resultScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import androidx.navigation.toRoute
import com.feature.guessGame.guessGameUi.common.BaseViewModel
import com.feature.guessGame.guessGameUi.navigation.Destinations
import com.feature.guessGame.guessGameUi.navigation.QuestionType
import com.feature.guessGame.guessGameUi.screen.guessGameScreen.DifficultySettings
import com.feature.guessGame.guessGameUi.screen.guessGameScreen.mapper.toInt
import com.paris.domain.game.usecases.UpdatePointsUseCase
import com.paris.domain.user.usecase.auth.GetAccountIdUseCase
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
            val userId = getAccountIdUseCase() ?: -1
            updatePointsUseCase(userId, args.totalGamePoints)
        }
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
        navigate(
            destination,
            NavOptions.Builder()
                .setPopUpTo(0, true)
                .build()
        )
    }
}
