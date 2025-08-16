package com.feature.guessGame.guessGameUi.screen.guessGameScreen

import android.content.Context
import com.feature.guessGame.guessGameUi.common.BaseViewModel
import com.feature.guessGame.guessGameUi.navigation.Destinations.GuessByImageScreen
import com.feature.guessGame.guessGameUi.navigation.Destinations.GuessQuestionScreen
import com.feature.guessGame.guessGameUi.navigation.QuestionType
import com.feature.guessGame.guessGameUi.navigation.navigateToGame
import com.feature.guessGame.guessGameUi.screen.guessGameScreen.mapper.toUiGameLevel
import com.paris_2.domain.game.usecases.GetUserPointUseCase
import com.paris_2.domain.user.usecase.GetAccountIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GuessGameScreenViewModel @Inject constructor(
    private val getUserPointUseCase: GetUserPointUseCase,
    private val getAccountIdUseCase: GetAccountIdUseCase,
) :
    GuessGameScreenInteractionListener,
    BaseViewModel<GuessGameScreenUiState>(GuessGameScreenUiState()) {


    init {
        loadUserPoints()
    }

    fun loadUserPoints() {
        tryToExecute(
            onSuccess = { points ->
                updateState(screenState.value.copy(userPoints = points))
            },
            onError = { error ->
            },
            execute = {
                val userId = getAccountIdUseCase() ?: 0
                getUserPointUseCase(userId)
            }
        )
    }


    override fun onGamePlayClicked(gameId: String) {
        updateState(
            screenState.value.copy(
                selectedGameId = gameId,
                showDifficultyDialog = true
            )
        )
    }

    override fun onSelectDifficulty(difficultyId: Int) {
        updateState(screenState.value.copy(selectedDifficulty = difficultyId))
    }

    override fun onStartGame(context: Context) {
        val settings =
            DifficultySettings.getDifficultySettings(screenState.value.selectedDifficulty)
        val questionType = gameIdToQuestionType[screenState.value.selectedGameId]
        val uiLevel = screenState.value.selectedDifficulty.toUiGameLevel()

        updateState(screenState.value.copy(showDifficultyDialog = false))
        when (questionType) {
            QuestionType.ACTOR -> {
                navigateToGame(
                    context,
                    gameDestination = GuessByImageScreen(
                        questionType = questionType,
                        totalQuestions = settings.numberOfQuestions,
                        timePerQuestion = settings.timePerQuestionSec,
                        pointsPerQuestion = settings.pointsPerQuestion,
                        imageType = QuestionType.ACTOR,
                        gameLevel = uiLevel
                    )
                )
            }

            QuestionType.POSTER -> {
                navigateToGame(
                    context,
                    gameDestination = GuessByImageScreen(
                        questionType = questionType,
                        totalQuestions = settings.numberOfQuestions,
                        timePerQuestion = settings.timePerQuestionSec,
                        pointsPerQuestion = settings.pointsPerQuestion,
                        imageType = QuestionType.POSTER,
                        gameLevel = uiLevel
                    )
                )
            }

            QuestionType.RELEASE_YEAR -> {
                navigateToGame(
                    context,
                    gameDestination = GuessQuestionScreen(
                        questionType = questionType,
                        totalQuestions = settings.numberOfQuestions,
                        timePerQuestion = settings.timePerQuestionSec,
                        pointsPerQuestion = settings.pointsPerQuestion,
                        gameLevel = uiLevel
                    )
                )

            }

            QuestionType.GENRE -> {
                navigateToGame(
                    context,
                    gameDestination = GuessQuestionScreen(
                        questionType = questionType,
                        totalQuestions = settings.numberOfQuestions,
                        timePerQuestion = settings.timePerQuestionSec,
                        pointsPerQuestion = settings.pointsPerQuestion,
                        gameLevel = uiLevel
                    )
                )
            }

            null -> {
            }
        }
    }

    override fun onDismissDifficultyDialog() {
        updateState(screenState.value.copy(showDifficultyDialog = false))
    }

    companion object {
        const val GAME_ID_RELEASE_YEAR = "guess_release_year"
        const val GAME_ID_GENRE = "guess_genre"
        const val GAME_ID_ACTOR = "guess_character"
        const val GAME_ID_POSTER = "guess_movie"

        private val gameIdToQuestionType = mapOf(
            GAME_ID_RELEASE_YEAR to QuestionType.RELEASE_YEAR,
            GAME_ID_GENRE to QuestionType.GENRE,
            GAME_ID_ACTOR to QuestionType.ACTOR,
            GAME_ID_POSTER to QuestionType.POSTER
        )
    }
}
