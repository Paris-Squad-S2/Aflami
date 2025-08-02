package com.feature.mediaDetails.mediaDetailsUi.ui.screen.details

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.domain.media.entity.EpisodeVideo
import com.domain.media.entity.Review
import com.domain.media.entity.Season
import com.domain.media.entity.TvShow
import com.domain.media.entity.TvShowVideo
import com.domain.media.useCase.tvShows.AddRatingToTvShowUseCase
import com.domain.media.useCase.tvShows.GetEpisodeVideoUseCase
import com.domain.media.useCase.tvShows.GetSeasonDetailsUseCase
import com.domain.media.useCase.tvShows.GetTvShowCastUseCase
import com.domain.media.useCase.tvShows.GetTvShowDetailsUseCase
import com.domain.media.useCase.tvShows.GetTvShowGalleryUseCase
import com.domain.media.useCase.tvShows.GetTvShowRecommendationsUseCase
import com.domain.media.useCase.tvShows.GetTvShowReviewsUseCase
import com.domain.media.useCase.tvShows.GetTvShowVideoUseCase
import com.domain.media.useCase.tvShows.GetTvShowsProductionCompaniesUseCase
import com.domain.user.usecase.IsLoggedInUseCase
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfReviewUi
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toUi
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsNavigator
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.ReviewUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details.TvShowDetailsViewModel
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details.TvShowUi
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
    private val navigator: MediaDetailsNavigator = mockk(relaxed = true)

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
    fun `onAddToListClick updates state to show AddToListDialog when user logged in`() = runTest {
        coEvery { isLoggedInUseCase() } returns true
        viewModel = makeViewModelWithDefaultStateHandle()
        viewModel.onAddToListClick()
        runCurrent()
        assertTrue(viewModel.screenState.value.showAddToListDialog)
    }

    @Test
    fun `onAddToListClick doesn't show AddToListDialog when user not logged in`() = runTest {
        coEvery { isLoggedInUseCase() } returns false
        viewModel = makeViewModelWithDefaultStateHandle()
        viewModel.onAddToListClick()
        runCurrent()
        assertFalse(viewModel.screenState.value.showAddToListDialog)
    }

    @Test
    fun `onAddToListClick updates state to show error when isLoggedInUseCase fails`() = runTest {
        coEvery { isLoggedInUseCase() } throws Exception("error")
        viewModel = makeViewModelWithDefaultStateHandle()
        viewModel.onAddToListClick()
        runCurrent()
        assertEquals(viewModel.screenState.value.errorMessage, "error")
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
        // Arrange
        val domainReviews = listOf(mockk<Review>())
        val uiReviews = listOf(mockk<ReviewUi>())

        val domainTvShow = mockk<TvShow>(relaxed = true)
        val tvShowUi = mockk<TvShowUi>()

        coEvery { getTvShowDetailsUseCase(testTvShowId) } returns domainTvShow
        coEvery { getTvShowVideoUseCase(testTvShowId) } returns mockk<TvShowVideo>(relaxed = true)
        coEvery { getTvShowReviewsUseCase(testTvShowId, 1) } returns domainReviews

        mockkStatic("com.feature.mediaDetails.mediaDetailsUi.ui.mapper.UiMapperKt")
        mockkStatic("com.feature.mediaDetails.mediaDetailsUi.ui.mapper.UiMapperKt")
        every { domainTvShow.toUi() } returns tvShowUi
        every { domainReviews.toListOfReviewUi() } returns uiReviews

        viewModel = makeViewModelWithDefaultStateHandle()
        runCurrent()


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
            getEpisodeVideoUseCase,
            mediaDetailsFeatureAPI,
            isLoggedInUseCase,
            addRatingToTvShowUseCase,
            navigator
        )
    }
}
