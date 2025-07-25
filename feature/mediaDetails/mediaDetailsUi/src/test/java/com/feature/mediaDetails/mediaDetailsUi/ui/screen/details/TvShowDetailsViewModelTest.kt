package com.feature.mediaDetails.mediaDetailsUi.ui.screen.details

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.domain.mediaDetails.model.EpisodeVideo
import com.domain.mediaDetails.model.Season
import com.domain.mediaDetails.model.TvShow
import com.domain.mediaDetails.model.TvShowVideo
import com.domain.mediaDetails.useCase.tvShows.AddRatingToTvShowUseCase
import com.domain.mediaDetails.useCase.tvShows.GetEpisodeVideoUseCase
import com.domain.mediaDetails.useCase.tvShows.GetSeasonDetailsUseCase
import com.domain.mediaDetails.useCase.tvShows.GetTvShowCastUseCase
import com.domain.mediaDetails.useCase.tvShows.GetTvShowDetailsUseCase
import com.domain.mediaDetails.useCase.tvShows.GetTvShowGalleryUseCase
import com.domain.mediaDetails.useCase.tvShows.GetTvShowRecommendationsUseCase
import com.domain.mediaDetails.useCase.tvShows.GetTvShowReviewsUseCase
import com.domain.mediaDetails.useCase.tvShows.GetTvShowVideoUseCase
import com.domain.mediaDetails.useCase.tvShows.GetTvShowsProductionCompaniesUseCase
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsNavigator
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details.TvShowDetailsViewModel
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details.TvShowVideoUi
import com.paris_2.domain.authentication.usecase.IsLoggedInUseCase
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
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

@OptIn(ExperimentalCoroutinesApi::class)
class TvShowDetailsViewModelTest {
    private val savedStateHandle: SavedStateHandle = mockk(relaxed = true)
    private val getTvShowDetailsUseCase: GetTvShowDetailsUseCase = mockk()
    private val getTvShowCastUseCase: GetTvShowCastUseCase = mockk()
    private val getTvShowGalleryUseCase: GetTvShowGalleryUseCase = mockk()
    private val getTvShowRecommendationsUseCase: GetTvShowRecommendationsUseCase = mockk()
    private val getTvShowReviewsUseCase: GetTvShowReviewsUseCase = mockk()
    private val getTvShowProductionCompaniesUseCase: GetTvShowsProductionCompaniesUseCase = mockk()
    private val getSeasonDetailsUseCase: GetSeasonDetailsUseCase = mockk()
    private val getTvShowVideoUseCase: GetTvShowVideoUseCase = mockk()
    private val getEpisodeVideoUseCase: GetEpisodeVideoUseCase = mockk()
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI = mockk(relaxed = true)
    private val isLoggedInUseCase: IsLoggedInUseCase = mockk()
    private val addRatingToTvShowUseCase: AddRatingToTvShowUseCase = mockk()
    private lateinit var viewModel: TvShowDetailsViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val testTvShowId = 88

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        clearAllMocks()
        mockkStatic("androidx.navigation.SavedStateHandleKt")
        every { savedStateHandle.toRoute<MediaDetailsDestinations.TvShowDetailsScreen>() } returns MediaDetailsDestinations.TvShowDetailsScreen(
            tvShowId = testTvShowId
        )
        stopKoin()
        startKoin {
            modules(
                module {
                    single<MediaDetailsNavigator> { mockk(relaxed = true) }
                }
            )
        }
    }

    @Test
    fun `init loads tv show details and video info`() = runTest {
        coEvery { getTvShowDetailsUseCase(any()) } returns mockk<TvShow>(relaxed = true)
        coEvery { getTvShowVideoUseCase(any()) } returns mockk<TvShowVideo>(relaxed = true)
        viewModel = makeViewModelWithDefaultStateHandle()
        runCurrent()
        coVerify { getTvShowDetailsUseCase(testTvShowId) }
        coVerify { getTvShowVideoUseCase(testTvShowId) }
    }

    @Test
    fun `init getInformationVideoTvShow error handling sets error`() = runTest {
        val errorMsg = "videoFail"
        coEvery { getTvShowDetailsUseCase(any()) } returns mockk<TvShow>(relaxed = true)
        coEvery { getTvShowVideoUseCase(any()) } throws RuntimeException(errorMsg)
        viewModel = makeViewModelWithDefaultStateHandle()
        runCurrent()
        assertEquals(errorMsg, viewModel.screenState.value.errorMessage)
    }

    @Test
    fun `onFavouriteClick when logged in shows rating dialog`() = runTest {
        coEvery { isLoggedInUseCase() } returns true
        coEvery { addRatingToTvShowUseCase() } returns Unit
        viewModel = makeViewModelWithDefaultStateHandle()
        viewModel.onFavouriteClick(testTvShowId)
        runCurrent()
        assertTrue(viewModel.screenState.value.showRatingDialog)
    }

    @Test
    fun `onFavouriteClick when not logged in doesn't show rating dialog`() = runTest {
        coEvery { isLoggedInUseCase() } returns false
        viewModel = makeViewModelWithDefaultStateHandle()
        viewModel.onFavouriteClick(testTvShowId)
        runCurrent()
        assertFalse(viewModel.screenState.value.showRatingDialog)
    }

    @Test
    fun `onFavouriteClick error hitting isLoggedIn sets error message`() = runTest {
        val errorMsg = "error_is_logged"
        coEvery { isLoggedInUseCase() } throws RuntimeException(errorMsg)
        viewModel = makeViewModelWithDefaultStateHandle()
        viewModel.onFavouriteClick(testTvShowId)
        runCurrent()
        assertEquals(errorMsg, viewModel.screenState.value.errorMessage)
    }

    @Test
    fun `onClickOnSeason expands season and loads episodes on success`() = runTest {
        val mockEpisodesResult = mockk<Season>(relaxed = true)
        coEvery { getSeasonDetailsUseCase(any(), any()) } returns mockEpisodesResult
        viewModel = makeViewModelWithDefaultStateHandle()
        val stateWithSeasons = viewModel.screenState.value.copy(
            tvShowDetailsUiState = viewModel.screenState.value.tvShowDetailsUiState.copy(
                tvShowUi = viewModel.screenState.value.tvShowDetailsUiState.tvShowUi.copy(
                    seasons = listOf(mockk(relaxed = true) { every { seasonNumber } returns 5; every { isExpanded } returns false; every { episodes } returns emptyList() })
                )
            )
        )
        viewModel.updateState(stateWithSeasons)
        viewModel.onClickOnSeason(5)
        runCurrent()
        assertFalse(viewModel.screenState.value.seasonsLoadingStates.containsKey(5))
    }

    @Test
    fun `onRetryLoadTvShowDetails reloads details and sets loading`() = runTest {
        coEvery { getTvShowDetailsUseCase(any()) } returns mockk<TvShow>(relaxed = true)
        viewModel = makeViewModelWithDefaultStateHandle()
        viewModel.onRetryLoadTvShowDetails()
        assertTrue(viewModel.screenState.value.isLoading)
    }

    @Test
    fun `onDismissRatingDialog hides dialog`() = runTest {
        viewModel = makeViewModelWithDefaultStateHandle()
        viewModel.updateState(viewModel.screenState.value.copy(showRatingDialog = true))
        viewModel.onDismissRatingDialog()
        assertFalse(viewModel.screenState.value.showRatingDialog)
    }

    @Test
    fun `onSimilarTvShowClick triggers navigation`() = runTest {
        viewModel = makeViewModelWithDefaultStateHandle()
        viewModel.onSimilarTvShowClick(77)
    }

    @Test
    fun `onClickPlayTvShowTrailer with valid video data navigates to video screen`() = runTest {
        val expectedSite = "YouTube"
        val expectedKey = "abc123"
        val mockVideoUi = TvShowVideoUi(site = expectedSite, key = expectedKey, name = "Test Video")
        viewModel = makeViewModelWithDefaultStateHandle()

        viewModel.updateState(
            viewModel.screenState.value.copy(
                tvShowDetailsUiState = viewModel.screenState.value.tvShowDetailsUiState.copy(
                    tvShowVideoUi = mockVideoUi
                )
            )
        )

        viewModel.onClickPlayTvShowTrailer()
        runCurrent()

    }

    @Test
    fun `onClickPlayEpisodeTrailer with valid video calls use case`() = runTest {
        val testTvShowId = 123
        val testSeasonNumber = 1
        val testEpisodeNumber = 2
        val mockEpisodeVideo = mockk<EpisodeVideo> {
            every { site } returns "YouTube"
            every { key } returns "abc123"
            every { name } returns "Test Episode"
        }
        coEvery { getEpisodeVideoUseCase(any(), any(), any()) } returns mockEpisodeVideo
        viewModel = makeViewModelWithDefaultStateHandle()

        viewModel.onClickPlayEpisodeTrailer(testTvShowId, testSeasonNumber, testEpisodeNumber)
        runCurrent()

        coVerify { getEpisodeVideoUseCase(testTvShowId, testSeasonNumber, testEpisodeNumber) }
    }

    @Test
    fun `onClickPlayEpisodeTrailer with valid video does not show snackbar`() = runTest {
        val testTvShowId = 123
        val testSeasonNumber = 1
        val testEpisodeNumber = 2
        val mockEpisodeVideo = mockk<EpisodeVideo> {
            every { site } returns "YouTube"
            every { key } returns "abc123"
            every { name } returns "Test Episode"
        }
        coEvery { getEpisodeVideoUseCase(any(), any(), any()) } returns mockEpisodeVideo
        viewModel = makeViewModelWithDefaultStateHandle()

        viewModel.onClickPlayEpisodeTrailer(testTvShowId, testSeasonNumber, testEpisodeNumber)
        runCurrent()

        assertFalse(viewModel.screenState.value.showSnackBar)
    }

    @Test
    fun `onClickPlayEpisodeTrailer with empty video calls use case`() = runTest {
        val testTvShowId = 123
        val testSeasonNumber = 1
        val testEpisodeNumber = 2
        val mockEpisodeVideo = mockk<EpisodeVideo> {
            every { site } returns ""
            every { key } returns ""
            every { name } returns "Test Episode"
        }
        coEvery { getEpisodeVideoUseCase(any(), any(), any()) } returns mockEpisodeVideo
        viewModel = makeViewModelWithDefaultStateHandle()

        viewModel.onClickPlayEpisodeTrailer(testTvShowId, testSeasonNumber, testEpisodeNumber)
        runCurrent()

        coVerify { getEpisodeVideoUseCase(testTvShowId, testSeasonNumber, testEpisodeNumber) }
    }

    @Test
    fun `onClickPlayEpisodeTrailer with empty video shows snackbar`() = runTest {
        val testTvShowId = 123
        val testSeasonNumber = 1
        val testEpisodeNumber = 2
        val mockEpisodeVideo = mockk<EpisodeVideo> {
            every { site } returns ""
            every { key } returns ""
            every { name } returns "Test Episode"
        }
        coEvery { getEpisodeVideoUseCase(any(), any(), any()) } returns mockEpisodeVideo
        viewModel = makeViewModelWithDefaultStateHandle()

        viewModel.onClickPlayEpisodeTrailer(testTvShowId, testSeasonNumber, testEpisodeNumber)
        runCurrent()

        assertTrue(viewModel.screenState.value.showSnackBar)
    }

    @Test
    fun `onClickPlayEpisodeTrailer error calls use case`() = runTest {
        val testTvShowId = 123
        val testSeasonNumber = 1
        val testEpisodeNumber = 2
        coEvery { getEpisodeVideoUseCase(any(), any(), any()) } throws RuntimeException("Error")
        viewModel = makeViewModelWithDefaultStateHandle()

        viewModel.onClickPlayEpisodeTrailer(testTvShowId, testSeasonNumber, testEpisodeNumber)
        runCurrent()

        coVerify { getEpisodeVideoUseCase(testTvShowId, testSeasonNumber, testEpisodeNumber) }
    }
    
    private fun makeViewModelWithDefaultStateHandle(): TvShowDetailsViewModel {
        every { savedStateHandle.toRoute<MediaDetailsDestinations.TvShowDetailsScreen>() } returns MediaDetailsDestinations.TvShowDetailsScreen(
            tvShowId = testTvShowId
        )
        return TvShowDetailsViewModel(
            savedStateHandle,
            getTvShowDetailsUseCase,
            getTvShowCastUseCase,
            getTvShowGalleryUseCase,
            getTvShowRecommendationsUseCase,
            getTvShowReviewsUseCase,
            getTvShowProductionCompaniesUseCase,
            getSeasonDetailsUseCase,
            getTvShowVideoUseCase,
            getEpisodeVideoUseCase,
            mediaDetailsFeatureAPI,
            isLoggedInUseCase,
            addRatingToTvShowUseCase
        )
    }
}
