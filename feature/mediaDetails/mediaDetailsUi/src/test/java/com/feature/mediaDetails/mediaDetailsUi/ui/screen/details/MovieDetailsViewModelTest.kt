package com.feature.mediaDetails.mediaDetailsUi.ui.screen.details

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.domain.media.entity.Movie
import com.domain.media.entity.MovieVideo
import com.domain.media.entity.Review
import com.domain.media.useCase.movie.AddRatingToMovieUseCase
import com.domain.media.useCase.movie.GetMovieCastUseCase
import com.domain.media.useCase.movie.GetMovieDetailsUseCase
import com.domain.media.useCase.movie.GetMovieGalleryUseCase
import com.domain.media.useCase.movie.GetMovieRecommendationsUseCase
import com.domain.media.useCase.movie.GetMovieReviewsUseCase
import com.domain.media.useCase.movie.GetMovieVideoUseCase
import com.domain.media.useCase.movie.GetMoviesProductionCompaniesUseCase
import com.paris_2.domain.user.usecase.GetSessionIdUseCase
import com.paris_2.domain.user.usecase.IsLoggedInUseCase
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.mediaDetails.mediaDetailsUi.R
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfReviewUi
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toUi
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsNavigator
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.MovieDetailsViewModel
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.MovieUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.ReviewUi
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
class MovieDetailsViewModelTest {
    private val savedStateHandle: SavedStateHandle = mockk(relaxed = true)
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase = mockk()
    private val getMovieCastUseCase: GetMovieCastUseCase = mockk()
    private val getMovieGalleryUseCase: GetMovieGalleryUseCase = mockk()
    private val getMovieRecommendationsUseCase: GetMovieRecommendationsUseCase = mockk()
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase = mockk()
    private val getMovieProductionCompaniesUseCase: GetMoviesProductionCompaniesUseCase = mockk()
    private val getMovieVideoUseCase: GetMovieVideoUseCase = mockk()
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI = mockk(relaxed = true)
    private val isLoggedInUseCase: IsLoggedInUseCase = mockk()
    private val addRatingToMovieUseCase: AddRatingToMovieUseCase = mockk()
    private val getSessionIdUseCase: GetSessionIdUseCase = mockk()
    private lateinit var viewModel: MovieDetailsViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val testMovieId = 42
    private val mediaDetailsNavigator: MediaDetailsNavigator = mockk(relaxed = true)

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        clearAllMocks()
        mockkStatic("androidx.navigation.SavedStateHandleKt")
        every { savedStateHandle.toRoute<MediaDetailsDestinations.MovieDetailsScreen>() } returns MediaDetailsDestinations.MovieDetailsScreen(
            movieId = testMovieId
        )
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
    fun `onDismissAddToListDialog hides dialog`() = runTest {
        viewModel = makeViewModelWithDefaultStateHandle()
        viewModel.updateState(viewModel.screenState.value.copy(showAddToListDialog = true))
        viewModel.onDismissAddToListDialog()
        assertFalse(viewModel.screenState.value.showAddToListDialog)
    }

    @Test
    fun `onRatingSubmitted updates state on success`() = runTest {
        coEvery { addRatingToMovieUseCase(any(), any()) } returns Unit
        viewModel = makeViewModelWithDefaultStateHandle()
        viewModel.onRatingSubmitted(testMovieId, 4.7f)
        runCurrent()
        val state = viewModel.screenState.value
        assertTrue(state.showSnackBar)
        assertTrue(state.snackBarSuccess)
        assertEquals(R.string.rating_submit_successfully, state.snackBarMessage)
        assertFalse(state.showRatingDialog)
    }

    @Test
    fun `onSimilarMovieClick starts new movie details`() = runTest {
        viewModel = makeViewModelWithDefaultStateHandle()
        viewModel.onSimilarMovieClick(99)
        coVerify { mediaDetailsFeatureAPI.startMovieDetails(99) }
    }
    @Test
    fun `init loads movie details and video info`() = runTest {
        coEvery { getMovieDetailsUseCase(any()) } returns mockk<Movie>(relaxed = true)
        coEvery { getMovieVideoUseCase(any()) } returns mockk<MovieVideo>(relaxed = true)
        viewModel = makeViewModelWithDefaultStateHandle()
        runCurrent()
        coVerify { getMovieDetailsUseCase(testMovieId) }
        coVerify { getMovieVideoUseCase(testMovieId) }
    }

    @Test
    fun `init getInformationVideoMovie error handling sets error`() = runTest {
        val errorMsg = "videoFail"
        coEvery { getMovieDetailsUseCase(any()) } returns mockk<Movie>(relaxed = true)
        coEvery { getMovieVideoUseCase(any()) } throws RuntimeException(errorMsg)
        viewModel = makeViewModelWithDefaultStateHandle()
        runCurrent()
        assertEquals(errorMsg, viewModel.screenState.value.errorMessage)
    }

    @Test
    fun `onRatingButtonClick when logged in shows rating dialog`() = runTest {
        coEvery { isLoggedInUseCase() } returns true
        coEvery { getSessionIdUseCase() } returns "session_id_123"
        coEvery {
            addRatingToMovieUseCase(
                movieId = testMovieId,
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
    fun `onFavouriteClick error hitting isLoggedIn sets error message`() = runTest {
        val errorMsg = "error_is_logged"
        coEvery { isLoggedInUseCase() } throws RuntimeException(errorMsg)
        viewModel = makeViewModelWithDefaultStateHandle()
        viewModel.onRateClick()
        runCurrent()
        assertEquals(errorMsg, viewModel.screenState.value.errorMessage)
    }

    @Test
    fun `onDismissRatingDialog hides dialog`() = runTest {
        viewModel = makeViewModelWithDefaultStateHandle()
        viewModel.updateState(viewModel.screenState.value.copy(showRatingDialog = true))
        viewModel.onDismissRatingDialog()
        assertFalse(viewModel.screenState.value.showRatingDialog)
    }

    @Test
    fun `onRetryLoadMovieDetails reloads details and sets loading`() = runTest {
        coEvery { getMovieDetailsUseCase(any()) } returns mockk<Movie>(relaxed = true)
        viewModel = makeViewModelWithDefaultStateHandle()
        viewModel.onRetryLoadMovieDetails()
        assertTrue(viewModel.screenState.value.isLoading)
    }

    @Test
    fun `loadMovieReviews updates state with review UI list on success`() = runTest {
        // Arrange
        val domainReviews = listOf(mockk<Review>())
        val uiReviews = listOf(mockk<ReviewUi>())

        val domainMovie = mockk<Movie>(relaxed = true)
        val movieUi = mockk<MovieUi>()

        coEvery { getMovieDetailsUseCase(testMovieId) } returns domainMovie
        coEvery { getMovieVideoUseCase(testMovieId) } returns mockk<MovieVideo>(relaxed = true)
        coEvery { getMovieReviewsUseCase(testMovieId, 1) } returns domainReviews

        mockkStatic("com.feature.mediaDetails.mediaDetailsUi.ui.mapper.UiMapperKt")
        mockkStatic("com.feature.mediaDetails.mediaDetailsUi.ui.mapper.UiMapperKt")
        every { domainMovie.toUi() } returns movieUi
        every { domainReviews.toListOfReviewUi() } returns uiReviews

        viewModel = makeViewModelWithDefaultStateHandle()
        runCurrent()


        val actualReviews = viewModel.screenState.value.movieDetailsUiState.reviews
        assertEquals(uiReviews, actualReviews)
    }


    private fun makeViewModelWithDefaultStateHandle(): MovieDetailsViewModel {
        every { savedStateHandle.toRoute<MediaDetailsDestinations.MovieDetailsScreen>() } returns MediaDetailsDestinations.MovieDetailsScreen(
            movieId = testMovieId
        )
        return MovieDetailsViewModel(
            savedStateHandle,
            getMovieDetailsUseCase,
            getMovieCastUseCase,
            getMovieGalleryUseCase,
            getMovieRecommendationsUseCase,
            getMovieReviewsUseCase,
            getMovieProductionCompaniesUseCase,
            getMovieVideoUseCase,
            mediaDetailsFeatureAPI,
            isLoggedInUseCase,
            addRatingToMovieUseCase,
            mediaDetailsNavigator
        )
    }
}
