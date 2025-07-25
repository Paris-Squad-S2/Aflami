package com.feature.mediaDetails.mediaDetailsUi.ui.screen.details

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.domain.mediaDetails.model.Movie
import com.domain.mediaDetails.model.MovieVideo
import com.domain.mediaDetails.useCase.movie.AddRatingToMovieUseCase
import com.domain.mediaDetails.useCase.movie.GetMovieCastUseCase
import com.domain.mediaDetails.useCase.movie.GetMovieDetailsUseCase
import com.domain.mediaDetails.useCase.movie.GetMovieGalleryUseCase
import com.domain.mediaDetails.useCase.movie.GetMovieRecommendationsUseCase
import com.domain.mediaDetails.useCase.movie.GetMovieReviewsUseCase
import com.domain.mediaDetails.useCase.movie.GetMoviesProductionCompaniesUseCase
import com.domain.mediaDetails.useCases.movie.GetMovieVideoUseCase
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsNavigator
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.MovieDetailsViewModel
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
    private lateinit var viewModel: MovieDetailsViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val testMovieId = 42

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        clearAllMocks()
        mockkStatic("androidx.navigation.SavedStateHandleKt")
        every { savedStateHandle.toRoute<MediaDetailsDestinations.MovieDetailsScreen>() } returns MediaDetailsDestinations.MovieDetailsScreen(
            movieId = testMovieId
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
    fun `onFavouriteClick when logged in shows rating dialog`() = runTest {
        coEvery { isLoggedInUseCase() } returns true
        coEvery { addRatingToMovieUseCase() } returns Unit
        viewModel = makeViewModelWithDefaultStateHandle()
        viewModel.onFavouriteClick(testMovieId)
        runCurrent()
        assertTrue(viewModel.screenState.value.showRatingDialog)
    }

    @Test
    fun `onFavouriteClick when not logged in doesn't show rating dialog`() = runTest {
        coEvery { isLoggedInUseCase() } returns false
        viewModel = makeViewModelWithDefaultStateHandle()
        viewModel.onFavouriteClick(testMovieId)
        runCurrent()
        assertFalse(viewModel.screenState.value.showRatingDialog)
    }

    @Test
    fun `onFavouriteClick error hitting isLoggedIn sets error message`() = runTest {
        val errorMsg = "error_is_logged"
        coEvery { isLoggedInUseCase() } throws RuntimeException(errorMsg)
        viewModel = makeViewModelWithDefaultStateHandle()
        viewModel.onFavouriteClick(testMovieId)
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
            addRatingToMovieUseCase
        )
    }
}
