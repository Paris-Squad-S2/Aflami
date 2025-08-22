package com.feature.categories.categoriesUi.screen.categoryDetails

import com.feature.categories.categoriesUi.shared.CategoryUiState
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.google.common.truth.Truth.assertThat
import com.paris.domain.media.entity.Category
import com.paris.domain.media.entity.MediaType
import com.paris.domain.media.useCase.movie.GetMoviesByCategoryUseCase
import com.paris.domain.media.useCase.tvShows.GetTvShowsByCategoryUseCase
import com.paris.domain.user.usecase.ManageSettingsUseCase
import com.paris.aflami.designsystem.R as RDesignSystem
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CategoryDetailsScreenViewModelTest {

    private val getMoviesByCategoryUseCase = mockk<GetMoviesByCategoryUseCase>()
    private val getTvShowsByCategoryUseCase = mockk<GetTvShowsByCategoryUseCase>()
    private val mediaDetailsFeatureAPI = mockk<MediaDetailsFeatureAPI>(relaxed = true)
    private val manageSettingsUseCase = mockk<ManageSettingsUseCase>()
    
    private lateinit var viewModel: CategoryDetailsScreenViewModel
    private val testDispatcher = StandardTestDispatcher()
    
    private val category = CategoryUiState(
        type = MediaType.Movie,
        category = Category.Action,
        name = RDesignSystem.string.movies,
        icon = RDesignSystem.drawable.anime_movie
    )

    @BeforeEach
    fun setup() {
        clearAllMocks()
        Dispatchers.setMain(testDispatcher)
        coEvery { manageSettingsUseCase.getRestriction() } returns "Off"
        viewModel = CategoryDetailsScreenViewModel(
            getMoviesByCategoryUseCase = getMoviesByCategoryUseCase,
            getTvShowsByCategoryUseCase = getTvShowsByCategoryUseCase,
            manageSettingsUseCase = manageSettingsUseCase,
            mediaDetailsFeatureAPI = mediaDetailsFeatureAPI
        )
    }

    @Test
    fun `initialCategory updates state with movies`() = runTest {
        viewModel.initialCategory(category)

        val state = viewModel.screenState.value

        assertThat(state.categoryDetailsUIState.selectedCategory).isEqualTo(category)
    }

    @Test
    fun `initialCategory updates state with tvShows`() = runTest {
        val tvCategory = CategoryUiState(
            type = MediaType.TvShow,
            category = Category.Action,
            name = RDesignSystem.string.tv_shows,
            icon = RDesignSystem.drawable.anime_movie
        )

        viewModel.initialCategory(tvCategory)

        val state = viewModel.screenState.value

        assertThat(state.categoryDetailsUIState.selectedCategory).isEqualTo(tvCategory)
    }

    @Test
    fun `getRestriction loads restriction and updates thresholds`() = runTest {
        coEvery { manageSettingsUseCase.getRestriction() } returns "Strict"
        viewModel = CategoryDetailsScreenViewModel(
            getMoviesByCategoryUseCase = getMoviesByCategoryUseCase,
            getTvShowsByCategoryUseCase = getTvShowsByCategoryUseCase,
            manageSettingsUseCase = manageSettingsUseCase,
            mediaDetailsFeatureAPI = mediaDetailsFeatureAPI
        )
        viewModel.initialCategory(category)
        runCurrent()
        
        assertThat(viewModel.screenState.value.categoryDetailsUIState.contentRestriction).isEqualTo(
            com.feature.categories.categoriesUi.screen.categoryDetails.ContentRestriction.Strict
        )
        assertThat(viewModel.screenState.value.categoryDetailsUIState.nsfwThreshold).isEqualTo(0.8f)
        assertThat(viewModel.screenState.value.categoryDetailsUIState.genderThreshold).isEqualTo(0.6f)
    }
    
    @Test
    fun `getRestriction handles Moderate restriction`() = runTest {
        coEvery { manageSettingsUseCase.getRestriction() } returns "Moderate"
        viewModel = CategoryDetailsScreenViewModel(
            getMoviesByCategoryUseCase = getMoviesByCategoryUseCase,
            getTvShowsByCategoryUseCase = getTvShowsByCategoryUseCase,
            manageSettingsUseCase = manageSettingsUseCase,
            mediaDetailsFeatureAPI = mediaDetailsFeatureAPI
        )
        viewModel.initialCategory(category)
        runCurrent()
        
        assertThat(viewModel.screenState.value.categoryDetailsUIState.contentRestriction).isEqualTo(
            com.feature.categories.categoriesUi.screen.categoryDetails.ContentRestriction.Moderate
        )
        assertThat(viewModel.screenState.value.categoryDetailsUIState.nsfwThreshold).isEqualTo(0.4f)
        assertThat(viewModel.screenState.value.categoryDetailsUIState.genderThreshold).isEqualTo(0.6f)
    }
    
    @Test
    fun `getRestriction handles Off restriction`() = runTest {
        coEvery { manageSettingsUseCase.getRestriction() } returns "Off"
        viewModel = CategoryDetailsScreenViewModel(
            getMoviesByCategoryUseCase = getMoviesByCategoryUseCase,
            getTvShowsByCategoryUseCase = getTvShowsByCategoryUseCase,
            manageSettingsUseCase = manageSettingsUseCase,
            mediaDetailsFeatureAPI = mediaDetailsFeatureAPI
        )
        viewModel.initialCategory(category)
        runCurrent()
        
        assertThat(viewModel.screenState.value.categoryDetailsUIState.contentRestriction).isEqualTo(
            com.feature.categories.categoriesUi.screen.categoryDetails.ContentRestriction.Off
        )
        assertThat(viewModel.screenState.value.categoryDetailsUIState.nsfwThreshold).isEqualTo(0f)
        assertThat(viewModel.screenState.value.categoryDetailsUIState.genderThreshold).isEqualTo(0f)
    }

    @Test
    fun `onCategorySelected updates state with movies`() = runTest {
        viewModel.onCategorySelected(category)

        val state = viewModel.screenState.value

        assertThat(state.categoryDetailsUIState.selectedCategory).isEqualTo(category)
    }

    @Test
    fun `onCategorySelected updates state with tvShows`() = runTest {
        val tvCategory = CategoryUiState(
            type = MediaType.TvShow,
            category = Category.Action,
            name = RDesignSystem.string.tv_shows,
            icon = RDesignSystem.drawable.anime_movie
        )

        viewModel.onCategorySelected(tvCategory)

        val state = viewModel.screenState.value

        assertThat(state.categoryDetailsUIState.selectedCategory).isEqualTo(tvCategory)
    }

    @Test
    fun onRetry() = runTest {
        val state = viewModel.screenState.value
        viewModel.onRetry()
        val newState = viewModel.screenState.value
        assertThat(newState.categoryDetailsUIState.selectedCategory).isEqualTo(state.categoryDetailsUIState.selectedCategory)
    }

    @Test
    fun `onMediaSelected should navigate to movie details when media is movie`() = runTest {
        val media = MediaUI(
            type = MediaType.Movie,
            title = "",
            voteAverage = 2.0,
            posterPath = "",
            id = 12,
            releaseDate = "",
        )
        viewModel.onMediaSelected(media)

        coVerify { mediaDetailsFeatureAPI.startMovieDetails(any()) }
    }

    @Test
    fun `onMediaSelected should navigate to tvShow details when media is tv show`() {
        val media = MediaUI(
            type = MediaType.TvShow,
            title = "",
            voteAverage = 2.0,
            posterPath = "",
            id = 12,
            releaseDate = "",
        )
        viewModel.onMediaSelected(media)

        coVerify { mediaDetailsFeatureAPI.startTvShowDetails(any()) }
    }

}