package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.cast

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.domain.mediaDetails.useCase.movie.GetMovieCastUseCase
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsNavigator
import io.mockk.clearAllMocks
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

@OptIn(ExperimentalCoroutinesApi::class)
class MovieCastViewModelTest {
    private val savedStateHandle: SavedStateHandle = mockk(relaxed = true)
    private val getMovieCastUseCase: GetMovieCastUseCase = mockk()
    private val navigator: MediaDetailsNavigator = mockk(relaxed = true)
    private lateinit var viewModel: MovieCastViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val testMovieId = 88

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        clearAllMocks()
        mockkStatic("androidx.navigation.SavedStateHandleKt")
        every { savedStateHandle.toRoute<MediaDetailsDestinations.MovieCastScreen>() } returns MediaDetailsDestinations.MovieCastScreen(
            movieId = testMovieId
        )
    }

    @Test
    fun `init loads movie cast`() = runTest {
        coEvery { getMovieCastUseCase(any()) } returns emptyList()
        viewModel = makeViewModelWithDefaultStateHandle()
        runCurrent()
        coVerify { getMovieCastUseCase(testMovieId) }
    }

    @Test
    fun `init getMovieCast error handling sets error`() = runTest {
        val errorMsg = "castLoadError"
        coEvery { getMovieCastUseCase(any()) } throws RuntimeException(errorMsg)
        viewModel = makeViewModelWithDefaultStateHandle()
        runCurrent()
        assertEquals(errorMsg, viewModel.screenState.value.errorMessage)
    }

    @Test
    fun `onRetryLoadCast reloads cast and sets loading`() = runTest {
        coEvery { getMovieCastUseCase(any()) } returns emptyList()
        viewModel = makeViewModelWithDefaultStateHandle()
        viewModel.onRetryLoadCast()
        assertEquals(true, viewModel.screenState.value.isLoading)
    }

    private fun makeViewModelWithDefaultStateHandle(): MovieCastViewModel {
        every { savedStateHandle.toRoute<MediaDetailsDestinations.MovieCastScreen>() } returns MediaDetailsDestinations.MovieCastScreen(
            movieId = testMovieId
        )
        return MovieCastViewModel(
            savedStateHandle,
            getMovieCastUseCase,
            navigator
        )
    }
}