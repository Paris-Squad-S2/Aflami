package com.feature.guessGame.guessGameUi.screen.guessGameScreen

import com.feature.guessGame.guessGameUi.navigation.Destinations
import com.feature.guessGame.guessGameUi.navigation.GuessGameNavigator
import com.feature.guessGame.guessGameUi.navigation.QuestionType
import io.mockk.coEvery
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
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import android.util.Log

@OptIn(ExperimentalCoroutinesApi::class)
class GuessGameScreenViewModelTest {

    private val getUserPointUseCase: com.paris_2.domain.game.usecases.GetUserPointUseCase = mockk()
    private val getAccountIdUseCase: com.paris_2.domain.user.usecase.GetAccountIdUseCase = mockk()
    private val navigator: GuessGameNavigator = mockk(relaxed = true)
    private lateinit var viewModel: GuessGameScreenViewModel
    private val dispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
        viewModel = GuessGameScreenViewModel(getUserPointUseCase, getAccountIdUseCase)
        viewModel.navigator = navigator
    }

    @Test
    fun `loadUserPoints sets userPoints`() = runTest {
        coEvery { getAccountIdUseCase() } returns 42
        coEvery { getUserPointUseCase(42) } returns 123

        viewModel.loadUserPoints()
        runCurrent()

        assertEquals(123, viewModel.screenState.value.userPoints)
    }

    @Test
    fun `onGamePlayClicked sets selected game and shows dialog`() = runTest {
        viewModel.onGamePlayClicked(GuessGameScreenViewModel.GAME_ID_ACTOR)
        assertEquals(
            GuessGameScreenViewModel.GAME_ID_ACTOR,
            viewModel.screenState.value.selectedGameId
        )
        assertEquals(true, viewModel.screenState.value.showDifficultyDialog)
    }

    @Test
    fun `onSelectDifficulty updates selectedDifficulty`() {
        viewModel.onSelectDifficulty(1)
        assertEquals(1, viewModel.screenState.value.selectedDifficulty)
    }

    @Test
    fun `onStartGame with ACTOR navigates to GuessByImageScreen with expected args`() = runTest {
        // prepare state
        viewModel.onGamePlayClicked(GuessGameScreenViewModel.GAME_ID_ACTOR)
        viewModel.onSelectDifficulty(0) // EASY
        // Pass a mock or test context if required by DifficultySettings.getDifficultySettings
        val context = mockk<android.content.Context>(relaxed = true)

        viewModel.onStartGame(context)
        runCurrent()

        val settings = DifficultySettings.getDifficultySettings(0)

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
    fun `onStartGame with GENRE navigates to GuessQuestionScreen with expected args`() = runTest {
        viewModel.onGamePlayClicked(GuessGameScreenViewModel.GAME_ID_GENRE)
        viewModel.onSelectDifficulty(0) // EASY
        val context = mockk<android.content.Context>(relaxed = true)


        viewModel.onStartGame(context)
        runCurrent()

        val settings = DifficultySettings.getDifficultySettings(0)

        coVerify {
            navigator.navigate(
                withArg { destination ->
                    assert(destination is Destinations.GuessQuestionScreen)
                    val d = destination as Destinations.GuessQuestionScreen
                    assert(d.questionType == QuestionType.GENRE)
                    assert(d.totalQuestions == settings.numberOfQuestions)
                    assert(d.timePerQuestion == settings.timePerQuestionSec)
                    assert(d.pointsPerQuestion == settings.pointsPerQuestion)
                },
                null
            )
        }
    }

    @Test
    fun `onDismissDifficultyDialog hides the dialog`() {
        viewModel.onGamePlayClicked(GuessGameScreenViewModel.GAME_ID_ACTOR)
        viewModel.onDismissDifficultyDialog()
        assertEquals(false, viewModel.screenState.value.showDifficultyDialog)
    }
}