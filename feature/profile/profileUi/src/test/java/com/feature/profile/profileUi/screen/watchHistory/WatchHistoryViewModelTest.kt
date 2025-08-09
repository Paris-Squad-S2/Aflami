package com.feature.profile.profileUi.screen.watchHistory

import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.profile.profileUi.common.BaseViewModel
import com.feature.profile.profileUi.navigation.ProfileNavigator
import com.google.common.truth.Truth.assertThat
import com.paris_2.domain.media.useCase.FilterWatchHistoryUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.paris_2.domain.media.entity.Media as DomainMedia
import com.paris_2.domain.media.entity.MediaType as DomainMediaType

@OptIn(ExperimentalCoroutinesApi::class)
class WatchHistoryViewModelTest {

    private val filterWatchHistoryUseCase: FilterWatchHistoryUseCase = mockk()
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI = mockk(relaxed = true)
    private lateinit var viewModel: WatchHistoryViewModel
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loading state should be true before data is returned`() = runTest {
        coEvery { filterWatchHistoryUseCase(any()) } coAnswers {
            assertThat(viewModel.screenState.value.isLoading).isTrue()
            fakeMediaList.map { it.toDomain() }
        }
        viewModel = WatchHistoryViewModel(filterWatchHistoryUseCase, mediaDetailsFeatureAPI)
        advanceUntilIdle()
    }

    @Test
    fun `onTabSelected should update selectedMediaType and reload data`() = runTest {
        coEvery { filterWatchHistoryUseCase(DomainMediaType.TVSHOW) } returns fakeMediaList.map { it.toDomain() }

        viewModel = WatchHistoryViewModel(filterWatchHistoryUseCase, mediaDetailsFeatureAPI)
        advanceUntilIdle()

        viewModel.onTabSelected(MediaTypeUi.TVSHOW)
        advanceUntilIdle()

        coVerify { filterWatchHistoryUseCase(DomainMediaType.TVSHOW) }
        assertThat(viewModel.screenState.value.watchHistoryMedia).hasSize(2)
    }

    @Test
    fun `initial load should fetch data for MOVIE by default`() = runTest {
        coEvery { filterWatchHistoryUseCase(DomainMediaType.MOVIE) } returns fakeMediaList.map { it.toDomain() }

        viewModel = WatchHistoryViewModel(filterWatchHistoryUseCase, mediaDetailsFeatureAPI)
        advanceUntilIdle()

        coVerify { filterWatchHistoryUseCase(DomainMediaType.MOVIE) }
        assertThat(viewModel.screenState.value.watchHistoryMedia).hasSize(2)
    }

    @Test
    fun `onMediaCardClick for TVSHOW triggers navigation`() = runTest {
        coEvery { filterWatchHistoryUseCase(any()) } returns fakeMediaList.map { it.toDomain() }
        viewModel = WatchHistoryViewModel(filterWatchHistoryUseCase, mediaDetailsFeatureAPI)
        advanceUntilIdle()

        viewModel.onMediaCardClick(fakeMediaList[1])
        advanceUntilIdle()

        coVerify { mediaDetailsFeatureAPI.startTvShowDetails(2) }
    }

    @Test
    fun `onBackClick should trigger navigateUp`() = runTest {
        // Arrange
        coEvery { filterWatchHistoryUseCase(any()) } returns fakeMediaList.map { it.toDomain() }
        val mockNavigator: ProfileNavigator = mockk(relaxed = true)

        viewModel = WatchHistoryViewModel(filterWatchHistoryUseCase, mediaDetailsFeatureAPI)

        val field = BaseViewModel::class.java.getDeclaredField("navigator")
        field.isAccessible = true
        field.set(viewModel, mockNavigator)

        viewModel.onBackClick()
        advanceUntilIdle()

        // Assert
        coVerify { mockNavigator.navigateUp() }
    }

    @Test
    fun `onRetry should reload data with last selected type`() = runTest {
        coEvery { filterWatchHistoryUseCase(DomainMediaType.MOVIE) } returns fakeMediaList.map { it.toDomain() }
        viewModel = WatchHistoryViewModel(filterWatchHistoryUseCase, mediaDetailsFeatureAPI)
        advanceUntilIdle()

        viewModel.onRetry()
        advanceUntilIdle()

        coVerify(exactly = 2) { filterWatchHistoryUseCase(DomainMediaType.MOVIE) }
    }

    @Test
    fun `onMediaCardClick for MOVIE triggers navigation`() = runTest {
        coEvery { filterWatchHistoryUseCase(any()) } returns fakeMediaList.map { it.toDomain() }
        viewModel = WatchHistoryViewModel(filterWatchHistoryUseCase, mediaDetailsFeatureAPI)
        advanceUntilIdle()

        viewModel.onMediaCardClick(fakeMediaList[0])
        advanceUntilIdle()

        coVerify { mediaDetailsFeatureAPI.startMovieDetails(1) }
    }

    @Test
    fun `onTabSelected should not reload when selecting same tab`() = runTest {
        coEvery { filterWatchHistoryUseCase(DomainMediaType.MOVIE) } returns fakeMediaList.map { it.toDomain() }

        viewModel = WatchHistoryViewModel(filterWatchHistoryUseCase, mediaDetailsFeatureAPI)
        advanceUntilIdle()

        viewModel.onTabSelected(MediaTypeUi.MOVIE)
        advanceUntilIdle()

        coVerify(exactly = 1) { filterWatchHistoryUseCase(DomainMediaType.MOVIE) }
    }

    @Test
    fun `onRetry should reload with TV shows when TV show tab is selected`() = runTest {
        coEvery { filterWatchHistoryUseCase(any()) } returns fakeMediaList.map { it.toDomain() }

        viewModel = WatchHistoryViewModel(filterWatchHistoryUseCase, mediaDetailsFeatureAPI)
        advanceUntilIdle()

        viewModel.onTabSelected(MediaTypeUi.TVSHOW)
        advanceUntilIdle()

        viewModel.onRetry()
        advanceUntilIdle()

        coVerify(atLeast = 2) { filterWatchHistoryUseCase(DomainMediaType.TVSHOW) }
    }

    private val fakeMediaList = listOf(
        MediaUiState(
            id = 1,
            imageUri = "img/1",
            title = "Test Movie",
            type = MediaTypeUi.MOVIE,
            yearOfRelease = LocalDate(2022, 1, 1),
            rating = 7.0
        ),
        MediaUiState(
            id = 2,
            imageUri = "img/2",
            title = "Test TV",
            type = MediaTypeUi.TVSHOW,
            yearOfRelease = LocalDate(2023, 2, 2),
            rating = 8.0
        )
    )

    private fun MediaUiState.toDomain() = DomainMedia(
        id = id,
        title = title,
        imageUri = imageUri,
        yearOfRelease = yearOfRelease,
        rating = rating,
        categoryIds = listOf(1),
        type = when (type) {
            MediaTypeUi.MOVIE -> DomainMediaType.MOVIE
            MediaTypeUi.TVSHOW -> DomainMediaType.TVSHOW
        }
    )
}