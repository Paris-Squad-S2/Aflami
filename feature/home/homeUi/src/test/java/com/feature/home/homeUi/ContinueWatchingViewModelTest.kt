package com.feature.home.homeUi

import com.domain.home.usecase.GetMediaFromLocalUseCase
import com.feature.home.homeUi.screen.continueWatching.ContinueWatchingViewModel
import com.feature.home.homeUi.screen.home.MediaTypeUi
import com.feature.home.homeUi.screen.home.MediaUiState
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.google.common.truth.Truth.assertThat
import com.paris_2.aflami.appnavigation.AppNavigator
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.domain.home.model.Media as DomainMedia
import com.domain.home.model.MediaType as DomainMediaType

@OptIn(ExperimentalCoroutinesApi::class)
class ContinueWatchingViewModelTest {
    private val getMediaFromLocalUseCase: GetMediaFromLocalUseCase = mockk()
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI = mockk(relaxed = true)
    private lateinit var viewModel: ContinueWatchingViewModel
    private val testDispatcher = StandardTestDispatcher()

    private val fakeMediaList = listOf(
        MediaUiState(
            5,
            "img/5",
            "Continue Test 1",
            MediaTypeUi.TVSHOW,
            listOf("Drama"),
            LocalDate(2023, 9, 9),
            5.8
        ),
        MediaUiState(
            6,
            "img/6",
            "Continue Test 2",
            MediaTypeUi.MOVIE,
            listOf("Action"),
            LocalDate(2022, 2, 2),
            7.1
        )
    )

    private fun MediaUiState.toMedia() = DomainMedia(
        id = id,
        title = title,
        voteAverage = rating,
        posterPath = imageUri,
        yearOfRelease = yearOfRelease,
        genreIds = listOf(1),
        type = when (type) {
            MediaTypeUi.MOVIE -> DomainMediaType.MOVIE
            MediaTypeUi.TVSHOW -> DomainMediaType.TV_SHOW
        }
    )

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `init loads media and updates state`() = runTest {
        coEvery { getMediaFromLocalUseCase() } returns fakeMediaList.map { it.toMedia() }
        viewModel = ContinueWatchingViewModel(getMediaFromLocalUseCase,mediaDetailsFeatureAPI)
        runCurrent()
        val state = viewModel.screenState.value
        assertThat(state.continueWatchingMediaList.map { it.title }).isEqualTo(fakeMediaList.map { it.title })
        assertThat(state.isLoading).isFalse()
        assertThat(state.errorMessage).isNull()
    }

    @Test
    fun `error from useCase updates errorMessage and sets isLoading false`() = runTest {
        coEvery { getMediaFromLocalUseCase() } throws RuntimeException("Failed to load")
        viewModel = ContinueWatchingViewModel(getMediaFromLocalUseCase,mediaDetailsFeatureAPI)
        runCurrent()
        val state = viewModel.screenState.value
        assertThat(state.errorMessage).isEqualTo("Failed to load")
        assertThat(state.isLoading).isFalse()
        assertThat(state.continueWatchingMediaList.isEmpty()).isTrue()
    }

    @Test
    fun `loading state is set true before media list returns`() = runTest {
        coEvery { getMediaFromLocalUseCase() } coAnswers {
            assertThat(viewModel.screenState.value.isLoading).isTrue()
            fakeMediaList.map { it.toMedia() }
        }
        viewModel = ContinueWatchingViewModel(getMediaFromLocalUseCase,mediaDetailsFeatureAPI)
        runCurrent()
    }

    @Test
    fun `onMediaCardClick for tv show triggers correct navigation`() = runTest {
        coEvery { getMediaFromLocalUseCase.invoke() } returns fakeMediaList.map { it.toMedia() }
        viewModel = ContinueWatchingViewModel(getMediaFromLocalUseCase, mediaDetailsFeatureAPI)
        runCurrent()

        val tvShow = fakeMediaList[0]
        viewModel.onMediaCardClick(tvShow)
        runCurrent()

        coVerify { mediaDetailsFeatureAPI.startTvShowDetails(tvShow.id) }
    }

    @Test
    fun `onMediaCardClick for movie triggers correct navigation`() = runTest {
        coEvery { getMediaFromLocalUseCase.invoke() } returns fakeMediaList.map { it.toMedia() }
        viewModel = ContinueWatchingViewModel(getMediaFromLocalUseCase, mediaDetailsFeatureAPI)
        runCurrent()

        val movie = fakeMediaList[1]
        viewModel.onMediaCardClick(movie)
        runCurrent()

        coVerify { mediaDetailsFeatureAPI.startMovieDetails(movie.id) }
    }

    @Test
    fun `onBackButtonClick triggers navigateUp`() = runTest {

        viewModel = ContinueWatchingViewModel(getMediaFromLocalUseCase, mediaDetailsFeatureAPI)
        viewModel.onBackButtonClick()
        advanceUntilIdle()

        coVerify(exactly = 1) { navigateUp() }
    }
}
