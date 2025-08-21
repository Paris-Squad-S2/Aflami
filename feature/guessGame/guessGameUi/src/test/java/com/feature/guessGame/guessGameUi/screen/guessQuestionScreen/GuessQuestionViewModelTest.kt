package com.feature.guessGame.guessGameUi.screen.guessQuestionScreen

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.feature.guessGame.guessGameUi.navigation.Destinations
import com.feature.guessGame.guessGameUi.navigation.QuestionType
import com.feature.guessGame.guessGameUi.screen.guessGameScreen.mapper.UiGameLevel
import com.paris.domain.game.entity.GameSession
import com.paris.domain.game.entity.Question
import com.paris.domain.game.usecases.GetUserPointUseCase
import com.paris.domain.game.usecases.MoveToNextQuestionUseCase
import com.paris.domain.game.usecases.RemoveAnswerHintUseCase
import com.paris.domain.game.usecases.UseHintUseCase
import com.paris.domain.game.usecases.whenIsReleased.WhenIsReleasedSessionUseCase
import com.paris.domain.game.usecases.whichGenre.WhichGenreSessionUseCase
import com.paris.domain.user.usecase.GetAccountIdUseCase
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GuessQuestionViewModelTest {

    private val whenIsReleasedSessionUseCase: WhenIsReleasedSessionUseCase = mockk()
    private val whichGenreSessionUseCase: WhichGenreSessionUseCase = mockk()
    private val removeAnswerHintUseCase: RemoveAnswerHintUseCase = mockk()
    private val moveToNextQuestionUseCase: MoveToNextQuestionUseCase = mockk()
    private val getUserPointUseCase: GetUserPointUseCase = mockk()
    private val getAccountIdUseCase: GetAccountIdUseCase = mockk()
    private val useHintUseCase: UseHintUseCase = mockk()
    private lateinit var viewModel: GuessQuestionViewModel
    private val dispatcher = StandardTestDispatcher()
    private val savedStateHandle: SavedStateHandle = mockk(relaxed = true)
    private val testArgs = Destinations.GuessQuestionScreen(
        totalQuestions = 5,
        timePerQuestion = 30,
        pointsPerQuestion = 10,
        questionType = QuestionType.RELEASE_YEAR,
        gameLevel = UiGameLevel.EASY
    )


    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        clearAllMocks()
        mockkStatic("androidx.navigation.SavedStateHandleKt")
        mockkStatic("android.util.Log")
        every { android.util.Log.e(any(), any()) } returns 0
        every { savedStateHandle.toRoute<Destinations.GuessQuestionScreen>() } returns testArgs
        viewModel = makeViewModelWithDefaultStateHandle()
    }

    @Test
    fun `generateSession sets up initial state`() = runTest {
        val session = mockk<GameSession>(relaxed = true)
        coEvery { whenIsReleasedSessionUseCase.startNewSession(any()) } returns session
        every { session.getCurrentQuestion() } returns mockk(relaxed = true)
        viewModel
        assertNotNull(viewModel.screenState.value)
    }

    @Test
    fun `onAnswerSelected updates state and score`() = runTest {
        val question = mockk<Question>(relaxed = true) {
            every { selectedAnswer } returns null
            every { correctAnswer } returns "A"
            every { options } returns listOf(
                mockk { every { text } returns "A"; every { isCorrect } returns true },
                mockk { every { text } returns "B"; every { isCorrect } returns false }
            )
        }
        val session = mockk<GameSession>(relaxed = true) {
            every { getCurrentQuestion() } returns question
            every { score } returns 0
        }
        val field = viewModel.javaClass.getDeclaredField("currentSession")
        field.isAccessible = true
        field.set(viewModel, session)
        viewModel.onAnswerSelected("A")
        assertEquals("A", viewModel.screenState.value.selectedAnswer)
    }

    @Test
    fun `onHintUsed shows not enough points dialog if insufficient points`() = runTest {
        val question = mockk<Question>(relaxed = true) {
            every { usedHint } returns false
            every { selectedAnswer } returns null
        }
        val session = mockk<GameSession>(relaxed = true) {
            every { getCurrentQuestion() } returns question
        }

        coEvery { getAccountIdUseCase() } returns 1
        coEvery { getUserPointUseCase(any()) } returns flowOf(5)
        coEvery { useHintUseCase(session, 1) } returns false

        val field = viewModel.javaClass.getDeclaredField("currentSession")
        field.isAccessible = true
        field.set(viewModel, session)

        viewModel.onHintUsed()
        dispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.screenState.value.showNotEnoughPointsDialog)
    }

    private fun makeViewModelWithDefaultStateHandle(): GuessQuestionViewModel {
        every { savedStateHandle.toRoute<Destinations.GuessQuestionScreen>() } returns Destinations.GuessQuestionScreen(
            totalQuestions = 5,
            timePerQuestion = 30,
            pointsPerQuestion = 10,
            questionType = QuestionType.RELEASE_YEAR,
            gameLevel = UiGameLevel.EASY
        )
        return GuessQuestionViewModel(
            savedStateHandle = savedStateHandle,
            whenIsReleasedSessionUseCase = whenIsReleasedSessionUseCase,
            whichGenreSessionUseCase = whichGenreSessionUseCase,
            removeAnswerHintUseCase = removeAnswerHintUseCase,
            moveToNextQuestionUseCase = moveToNextQuestionUseCase,
            getUserPointUseCase = getUserPointUseCase,
            getAccountIdUseCase = getAccountIdUseCase,
            useHintUseCase = useHintUseCase
        )
    }
}