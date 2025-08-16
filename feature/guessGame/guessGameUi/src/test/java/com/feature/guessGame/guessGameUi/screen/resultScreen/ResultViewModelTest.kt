package com.feature.guessGame.guessGameUi.screen.resultScreen

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.feature.guessGame.guessGameUi.navigation.Destinations
import com.feature.guessGame.guessGameUi.navigation.GuessGameNavigator
import com.feature.guessGame.guessGameUi.navigation.QuestionType
import com.feature.guessGame.guessGameUi.screen.guessGameScreen.DifficultySettings
import com.feature.guessGame.guessGameUi.screen.guessGameScreen.mapper.UiGameLevel
import com.paris_2.domain.game.usecases.UpdatePointsUseCase
import com.paris_2.domain.user.usecase.GetAccountIdUseCase
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ResultViewModelTest {

    private val savedStateHandle: SavedStateHandle = mockk(relaxed = true)
    private val navigator: GuessGameNavigator = mockk(relaxed = true)

    private val getAccountIdUseCase: GetAccountIdUseCase = mockk(relaxed = true)
    private val updatePointsUseCase: UpdatePointsUseCase = mockk(relaxed = true)

    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockkStatic("androidx.navigation.SavedStateHandleKt")
    }

    @Test
    fun `onExitClicked navigates to GuessGameScreen`() = runTest {
        every { savedStateHandle.toRoute<Destinations.FinishGameScreen>() } returns Destinations.FinishGameScreen(
            totalGameTime = 100,
            totalGamePoints = 200,
            gameType = QuestionType.ACTOR,
            gameLevel = UiGameLevel.EASY
        )

        val viewModel = ResultViewModel(
            savedStateHandle,
            getAccountIdUseCase,
            updatePointsUseCase
        )
        viewModel.navigator = navigator

        viewModel.onExitClicked()
        runCurrent()

        coVerify { navigator.navigate(Destinations.Screen, null) }
    }

    @Test
    fun `onBackToMenuClicked navigates to GuessGameScreen`() = runTest {
        every { savedStateHandle.toRoute<Destinations.FinishGameScreen>() } returns Destinations.FinishGameScreen(
            totalGameTime = 100,
            totalGamePoints = 200,
            gameType = QuestionType.GENRE,
            gameLevel = UiGameLevel.EASY
        )

        val viewModel = ResultViewModel(
            savedStateHandle,
            getAccountIdUseCase,
            updatePointsUseCase
        )
        viewModel.navigator = navigator

        viewModel.onBackToMenuClicked()
        runCurrent()

        coVerify { navigator.navigate(Destinations.Screen, null) }
    }

    @Test
    fun `onPlayAgainClicked with ACTOR navigates to GuessByImageScreen with EASY defaults`() = runTest {
        every { savedStateHandle.toRoute<Destinations.FinishGameScreen>() } returns Destinations.FinishGameScreen(
            totalGameTime = 90,
            totalGamePoints = 150,
            gameType = QuestionType.ACTOR,
            gameLevel = UiGameLevel.EASY
        )

        val viewModel = ResultViewModel(
            savedStateHandle,
            getAccountIdUseCase,
            updatePointsUseCase
        )
        viewModel.navigator = navigator

        val settings = DifficultySettings.getDifficultySettings(difficultyId = 0)

        viewModel.onPlayAgainClicked()
        runCurrent()

        coVerify {
            navigator.navigate(
                withArg { destination ->
                    assert(destination is Destinations.GuessByImageScreen)
                    val d = destination as Destinations.GuessByImageScreen
                    assert(d.questionType == QuestionType.ACTOR)
                    assert(d.imageType == QuestionType.ACTOR)
                    assert(d.totalQuestions == settings.numberOfQuestions)
                    assert(d.timePerQuestion == settings.timePerQuestionSec)
                    assert(d.pointsPerQuestion == settings.pointsPerQuestion)
                },
                null
            )
        }
    }

    @Test
    fun `onPlayAgainClicked with GENRE navigates to GuessQuestionScreen with EASY defaults`() = runTest {
        every { savedStateHandle.toRoute<Destinations.FinishGameScreen>() } returns Destinations.FinishGameScreen(
            totalGameTime = 120,
            totalGamePoints = 250,
            gameType = QuestionType.GENRE,
            gameLevel = UiGameLevel.EASY
        )

        val viewModel = ResultViewModel(
            savedStateHandle,
            getAccountIdUseCase,
            updatePointsUseCase
        )
        viewModel.navigator = navigator

        val settings = DifficultySettings.getDifficultySettings(difficultyId = 0)

        viewModel.onPlayAgainClicked()
        runCurrent()

        coVerify {
            navigator.navigate(
                withArg { destination ->
                    assert(destination is Destinations.GuessQuestionScreen)
                    val d = destination as Destinations.GuessQuestionScreen
                    assert(d.questionType == QuestionType.GENRE)
                    assert(d.totalQuestions == settings.numberOfQuestions)
                    assert(d.timePerQuestion == settings.timePerQuestionSec)
                    assert(d.pointsPerQuestion == settings.pointsPerQuestion)
                    assert(d.gameLevel == UiGameLevel.EASY)
                },
                null
            )
        }
    }
}
