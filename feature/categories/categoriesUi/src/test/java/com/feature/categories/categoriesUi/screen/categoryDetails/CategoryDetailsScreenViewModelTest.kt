package com.feature.categories.categoriesUi.screen.categoryDetails

import com.feature.categories.categoriesUi.shared.CategoryUiState
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.paris.domain.media.entity.Category
import com.paris.domain.media.entity.MediaType
import com.paris.domain.media.useCase.GetMoviesByCategoryUseCase
import com.paris.domain.media.useCase.GetTvShowsByCategoryUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import com.paris.aflami.designsystem.R as RDesignSystem

class CategoryDetailsScreenViewModelTest {

    private val getMoviesByCategoryUseCase = mockk<GetMoviesByCategoryUseCase>()
    private val getTvShowsByCategoryUseCase = mockk<GetTvShowsByCategoryUseCase>()
    private val mediaDetailsFeatureAPI = mockk<MediaDetailsFeatureAPI>(relaxed = true)
    lateinit var viewModel:CategoryDetailsScreenViewModel
    @BeforeEach
    fun setup(){
        viewModel = CategoryDetailsScreenViewModel(
            getMoviesByCategoryUseCase = getMoviesByCategoryUseCase,
            getTvShowsByCategoryUseCase = getTvShowsByCategoryUseCase,
            mediaDetailsFeatureAPI = mediaDetailsFeatureAPI
        )
    }


    @Test
    fun `initialCategory updates state with movies`() = runTest {
        val category = CategoryUiState(
            type = MediaType.Movie,
            category = Category.ActionAdventure,
            name = RDesignSystem.string.movies,
            icon = RDesignSystem.drawable.anime_movie
        )

        viewModel.initialCategory(category)

        val state = viewModel.screenState.value

        assertEquals(category, state.categoryDetailsUIState.selectedCategory)
    }

    @Test
    fun `initialCategory updates state with tvShows`() = runTest {
        val category = CategoryUiState(
            type = MediaType.TvShow,
            category = Category.ActionAdventure,
            name = RDesignSystem.string.tv_shows,
            icon = RDesignSystem.drawable.anime_movie
        )


        viewModel.initialCategory(category)

        val state = viewModel.screenState.value

        assertEquals(category, state.categoryDetailsUIState.selectedCategory)
    }

    @Test
    fun `onCategorySelected updates state with movies`() = runTest {
        val category = CategoryUiState(
            type = MediaType.Movie,
            category = Category.ActionAdventure,
            name = RDesignSystem.string.movies,
            icon = RDesignSystem.drawable.anime_movie
        )

        viewModel.onCategorySelected(category)

        val state = viewModel.screenState.value

        assertEquals(category, state.categoryDetailsUIState.selectedCategory)
    }

    @Test
    fun `onCategorySelected updates state with tvShows`() = runTest {
        val category = CategoryUiState(
            type = MediaType.TvShow,
            category = Category.ActionAdventure,
            name = RDesignSystem.string.tv_shows,
            icon = RDesignSystem.drawable.anime_movie
        )


        viewModel.onCategorySelected(category)

        val state = viewModel.screenState.value

        assertEquals(category, state.categoryDetailsUIState.selectedCategory)
    }

    @Test
    fun onRetry() = runTest{
        val state = viewModel.screenState.value
        viewModel.onRetry()
        val newState = viewModel.screenState.value
        assertEquals(state.categoryDetailsUIState.selectedCategory,newState.categoryDetailsUIState.selectedCategory)
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