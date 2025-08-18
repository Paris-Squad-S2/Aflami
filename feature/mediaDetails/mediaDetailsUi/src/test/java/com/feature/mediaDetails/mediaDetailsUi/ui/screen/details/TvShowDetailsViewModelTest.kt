package com.feature.mediaDetails.mediaDetailsUi.ui.screen.details

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toMedia
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toUi
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsNavigator
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details.TvShowDetailsViewModel
import com.paris_2.domain.media.entity.MediaVideo
import com.paris_2.domain.media.entity.Review
import com.paris_2.domain.media.entity.Season
import com.paris_2.domain.media.entity.TvShow
import com.paris_2.domain.media.entity.TvShowVideo
import com.paris_2.domain.media.useCase.AddWatchHistoryUseCase
import com.paris_2.domain.media.useCase.tvShows.AddRatingToTvShowUseCase
import com.paris_2.domain.media.useCase.tvShows.GetEpisodeVideoUseCase
import com.paris_2.domain.media.useCase.tvShows.GetSeasonDetailsUseCase
import com.paris_2.domain.media.useCase.tvShows.GetTvShowCastUseCase
import com.paris_2.domain.media.useCase.tvShows.GetTvShowDetailsUseCase
import com.paris_2.domain.media.useCase.tvShows.GetTvShowGalleryUseCase
import com.paris_2.domain.media.useCase.tvShows.GetTvShowRecommendationsUseCase
import com.paris_2.domain.media.useCase.tvShows.GetTvShowReviewsUseCase
import com.paris_2.domain.media.useCase.tvShows.GetTvShowVideoUseCase
import com.paris_2.domain.media.useCase.tvShows.GetTvShowsProductionCompaniesUseCase
import com.paris_2.domain.user.usecase.IsLoggedInUseCase
import com.paris_2.domain.user.usecase.SettingsUseCase
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
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TvShowDetailsViewModelTest {
    private val savedStateHandle: SavedStateHandle = mockk(relaxed = true)
    private val getTvShowDetailsUseCase: GetTvShowDetailsUseCase = mockk()
    private val getTvShowCastUseCase: GetTvShowCastUseCase = mockk()
    private val getTvShowGalleryUseCase: GetTvShowGalleryUseCase = mockk()
    private val getTvShowRecommendationsUseCase: GetTvShowRecommendationsUseCase = mockk()
    private val getTvShowReviewsUseCase: GetTvShowReviewsUseCase = mockk(relaxed = true)
    private val getTvShowProductionCompaniesUseCase: GetTvShowsProductionCompaniesUseCase = mockk()
    private val getSeasonDetailsUseCase: GetSeasonDetailsUseCase = mockk()
    private val getTvShowVideoUseCase: GetTvShowVideoUseCase = mockk()
    private val getEpisodeVideoUseCase: GetEpisodeVideoUseCase = mockk()
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI = mockk(relaxed = true)
    private val isLoggedInUseCase: IsLoggedInUseCase = mockk()
    private val addWatchHistoryUseCase: AddWatchHistoryUseCase = mockk()
    private val settingsUseCase: SettingsUseCase = mockk()
    private val addRatingToTvShowUseCase: AddRatingToTvShowUseCase = mockk()
    private lateinit var viewModel: TvShowDetailsViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val testTvShowId = 88
    private val navigator: MediaDetailsNavigator = mockk(relaxed = true)

    val mockReview: Review = Review(
        id = "1",
        name = "Test Review",
        createdAt = LocalDate(2023, 10, 1),
        avatarUrl = "https://example.com/avatar.jpg",
        username = "testuser",
        rating = 4.5,
        description = "This is a test review."
    )

    val mockTvShow = TvShow(
        id = testTvShowId,
        posterPath = "https://example.com/poster.jpg",
        voteAverage = 8.5,
        title = "Test TV Show",
        categories = emptyList(),
        releaseDate = LocalDate(2023, 10, 1),
        runtime = 45,
        country = "USA",
        description = "This is a test TV show.",
        seasons = emptyList(),
        productionCompanies = emptyList()
    )

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        clearAllMocks()
        mockkStatic("androidx.navigation.SavedStateHandleKt")
        every { savedStateHandle.toRoute<MediaDetailsDestinations.TvShowDetailsScreen>() } returns MediaDetailsDestinations.TvShowDetailsScreen(
            tvShowId = testTvShowId
        )

    }

    @Test
    fun `onShowAllCastClick triggers navigation`() = runTest {
        viewModel = makeViewModelWithDefaultStateHandle()
        viewModel.onShowAllCastClick(123)
        runCurrent()
        coVerify { navigator.navigate(MediaDetailsDestinations.TvShowCastScreen(123)) }
    }

    @Test
    fun `onHideSnackBar sets showSnackBar to false`() = runTest {
        viewModel = makeViewModelWithDefaultStateHandle()
        viewModel.updateState(viewModel.screenState.value.copy(showSnackBar = true))
        viewModel.onHideSnackBar()
        assertFalse(viewModel.screenState.value.showSnackBar)
    }

    @Test
    fun `onRatingSubmitted sets success snackbar state on success`() = runTest {
        coEvery { addRatingToTvShowUseCase(any(), any()) } returns Unit
        viewModel = makeViewModelWithDefaultStateHandle()
        viewModel.onRatingSubmitted(88, 3.7f)
        runCurrent()

        val state = viewModel.screenState.value
        assertTrue(state.showSnackBar)
        assertTrue(state.snackBarSuccess)
        assertEquals(state.snackBarMessage, state.snackBarMessage)
        assertFalse(state.showRatingDialog)
    }

    @Test
    fun `onRatingSubmitted sets error snackbar state on failure`() = runTest {
        coEvery { addRatingToTvShowUseCase(any(), any()) } throws RuntimeException("rating fail")
        viewModel = makeViewModelWithDefaultStateHandle()
        viewModel.onRatingSubmitted(88, 4.0f)
        runCurrent()

        val state = viewModel.screenState.value
        assertTrue(state.showSnackBar)
        assertFalse(state.snackBarSuccess)
        assertEquals(state.snackBarMessage, state.snackBarMessage)
        assertEquals("rating fail", state.errorMessage)
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
    fun `onRatingButtonClick when logged in shows rating dialog`() = runTest {
        coEvery { isLoggedInUseCase() } returns true
        coEvery {
            addRatingToTvShowUseCase(
                movieId = any(),
                rating = any(),
            )
        } returns Unit
        viewModel = makeViewModelWithDefaultStateHandle()
        viewModel.onRateClick()
        runCurrent()
        assertTrue(viewModel.screenState.value.showRatingDialog)
    }

    @Test
    fun `onRatingButtonClick when not logged in doesn't show rating dialog`() = runTest {
        coEvery { isLoggedInUseCase() } returns false
        viewModel = makeViewModelWithDefaultStateHandle()
        viewModel.onRateClick()
        runCurrent()
        assertFalse(viewModel.screenState.value.showRatingDialog)
    }

    @Test
    fun `onRatingButtonClick error hitting isLoggedIn sets error message`() = runTest {
        val errorMsg = "error_is_logged"
        coEvery { isLoggedInUseCase() } throws RuntimeException(errorMsg)
        viewModel = makeViewModelWithDefaultStateHandle()
        viewModel.onRateClick()
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
    fun `onClickPlayEpisodeTrailer with valid video calls use case`() = runTest {
        val testTvShowId = 123
        val testSeasonNumber = 1
        val testEpisodeNumber = 2
        val mockEpisodeVideo = mockk<MediaVideo> {
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
        val mockEpisodeVideo = mockk<MediaVideo> {
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
        val mockEpisodeVideo = mockk<MediaVideo> {
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

    @Test
    fun `loadMovieReviews updates state with review UI list on success`() = runTest {
        val domainReviews = listOf(mockReview)
        val uiReviews = listOf(mockReview.toUi())

        coEvery { getTvShowDetailsUseCase(testTvShowId) } returns mockTvShow
        coEvery { addWatchHistoryUseCase(mockTvShow.toMedia()) } returns Unit
        coEvery { getTvShowVideoUseCase(testTvShowId) } returns mockk<TvShowVideo>(relaxed = true)
        coEvery { getTvShowReviewsUseCase(testTvShowId, 1) } returns domainReviews

        viewModel = makeViewModelWithDefaultStateHandle()
        runCurrent()

        coVerify { getTvShowReviewsUseCase(any(), any()) }
        val actualReviews = viewModel.screenState.value.tvShowDetailsUiState.reviews
        assertEquals(uiReviews, actualReviews)
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
            addWatchHistoryUseCase,
            getEpisodeVideoUseCase,
            mediaDetailsFeatureAPI,
            isLoggedInUseCase,
            addRatingToTvShowUseCase,
            settingsUseCase,
            navigator
        )
    }
}
