package com.feature.mediaDetails.mediaDetailsUi.ui.screen.video

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsDestinations.VideosScreen
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
class VideoWebViewViewModelTest {
    private lateinit var viewModel: VideoWebViewViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        savedStateHandle = mockk(relaxed = true) {
            every { get<String>("site") } returns "example"
            every { get<String>("key") } returns "12345"
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should have empty video URL before initialization`() {
        // Given
        val initialState = VideoWebUIState()

        // Then
        assertEquals("", initialState.videoUrl)
    }

    @Test
    fun `site and key should be retrieved as strings`() {
        // Given
        val site = "example"
        val key = "12345"

        every { savedStateHandle.get<String>("site") } returns site
        every { savedStateHandle.get<String>("key") } returns key

        val mockRoute = VideosScreen(site = site, key = key)
        mockkStatic("androidx.navigation.SavedStateHandleKt")
        every { savedStateHandle.toRoute<VideosScreen>() } returns mockRoute

        // When
        viewModel = VideoWebViewViewModel(savedStateHandle)

        // Then
        assertEquals("https://www.example.com/watch?v=$key", viewModel.screenState.value.videoUrl)
    }


}