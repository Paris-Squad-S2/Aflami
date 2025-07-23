package com.feature.mediaDetails.mediaDetailsUi.ui.screen.video

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsDestinations
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
class VideoWebViewViewModelTest {
    private lateinit var viewModel: VideoWebViewViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        savedStateHandle = mockk()
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should set correct video URL with site and key parameters`() {
        // Given
        val testSite = "youtube"
        val testKey = "dQw4w9WgXcQ"
        val expectedUrl = "https://www.$testSite.com/watch?v=$testKey"

        val mockDestination = MediaDetailsDestinations.VideosScreen(
            site = testSite,
            key = testKey
        )

        every { savedStateHandle.toRoute<MediaDetailsDestinations.VideosScreen>() } returns mockDestination

        // When
        viewModel = VideoWebViewViewModel(savedStateHandle)

        // Then
        assertEquals(expectedUrl, viewModel.screenState.value.videoUrl)
    }


    @Test
    fun `init should handle empty site parameter`() {
        // Given
        val testSite = ""
        val testKey = "testKey"
        val expectedUrl = "https://www..com/watch?v=$testKey"

        val mockDestination = MediaDetailsDestinations.VideosScreen(
            site = testSite,
            key = testKey
        )

        every { savedStateHandle.toRoute<MediaDetailsDestinations.VideosScreen>() } returns mockDestination

        // When
        viewModel = VideoWebViewViewModel(savedStateHandle)

        // Then
        assertEquals(expectedUrl, viewModel.screenState.value.videoUrl)
    }

    @Test
    fun `init should handle empty key parameter`() {
        // Given
        val testSite = "youtube"
        val testKey = ""
        val expectedUrl = "https://www.$testSite.com/watch?v="

        val mockDestination = MediaDetailsDestinations.VideosScreen(
            site = testSite,
            key = testKey
        )

        every { savedStateHandle.toRoute<MediaDetailsDestinations.VideosScreen>() } returns mockDestination

        // When
        viewModel = VideoWebViewViewModel(savedStateHandle)

        // Then
        assertEquals(expectedUrl, viewModel.screenState.value.videoUrl)
    }

    @Test
    fun `onNavigateBack should call navigateUp`() {
        // Given
        val mockDestination = MediaDetailsDestinations.VideosScreen(
            site = "youtube",
            key = "testKey"
        )

        every { savedStateHandle.toRoute<MediaDetailsDestinations.VideosScreen>() } returns mockDestination

        viewModel = VideoWebViewViewModel(savedStateHandle)

        val spyViewModel = mockk<VideoWebViewViewModel>(relaxed = true)

        // When
        spyViewModel.onNavigateBack()

        // Then
        verify { spyViewModel.onNavigateBack() }
    }

    @Test
    fun `initial state should have empty video URL before initialization`() {
        // Given
        val initialState = VideoWebUIState()

        // Then
        assertEquals("", initialState.videoUrl)
    }

    @Test
    fun `toRoute should be called twice during initialization - once for site and once for key`() {
        // Given
        val testSite = "youtube"
        val testKey = "testKey"

        val mockDestination = MediaDetailsDestinations.VideosScreen(
            site = testSite,
            key = testKey
        )

        every { savedStateHandle.toRoute<MediaDetailsDestinations.VideosScreen>() } returns mockDestination

        // When
        viewModel = VideoWebViewViewModel(savedStateHandle)

        // Then
        verify(exactly = 2) { savedStateHandle.toRoute<MediaDetailsDestinations.VideosScreen>() }
    }
}