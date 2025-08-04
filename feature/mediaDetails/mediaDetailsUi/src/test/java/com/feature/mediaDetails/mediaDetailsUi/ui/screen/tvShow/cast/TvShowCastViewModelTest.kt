package com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.cast

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.paris_2.domain.media.useCase.tvShows.GetTvShowCastUseCase
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
class TvShowCastViewModelTest {
    private val savedStateHandle: SavedStateHandle = mockk(relaxed = true)
    private val getTvShowCastUseCase: GetTvShowCastUseCase = mockk()
    private val navigator: MediaDetailsNavigator = mockk(relaxed = true)
    private lateinit var viewModel: TvShowCastViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val testTvShowId = 88

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        clearAllMocks()
        mockkStatic("androidx.navigation.SavedStateHandleKt")
        every { savedStateHandle.toRoute<MediaDetailsDestinations.TvShowCastScreen>() } returns MediaDetailsDestinations.TvShowCastScreen(
            tvShowId = testTvShowId
        )
    }

    @Test
    fun `init loads tv show cast`() = runTest {
        coEvery { getTvShowCastUseCase(any()) } returns emptyList()
        viewModel = makeViewModelWithDefaultStateHandle()
        runCurrent()
        coVerify { getTvShowCastUseCase(testTvShowId) }
    }

    @Test
    fun `init getTvShowCast error handling sets error`() = runTest {
        val errorMsg = "castLoadError"
        coEvery { getTvShowCastUseCase(any()) } throws RuntimeException(errorMsg)
        viewModel = makeViewModelWithDefaultStateHandle()
        runCurrent()
        assertEquals(errorMsg, viewModel.screenState.value.errorMessage)
    }

    @Test
    fun `onRetryLoadCast reloads cast and sets loading`() = runTest {
        coEvery { getTvShowCastUseCase(any()) } returns emptyList()
        viewModel = makeViewModelWithDefaultStateHandle()
        viewModel.onRetryLoadCast()
        assertEquals(true, viewModel.screenState.value.isLoading)
    }

    private fun makeViewModelWithDefaultStateHandle(): TvShowCastViewModel {
        every { savedStateHandle.toRoute<MediaDetailsDestinations.TvShowCastScreen>() } returns MediaDetailsDestinations.TvShowCastScreen(
            tvShowId = testTvShowId
        )
        return TvShowCastViewModel(
            savedStateHandle,
            getTvShowCastUseCase,
            navigator
        )
    }
}