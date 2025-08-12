package com.feature.categories.categoriesUi.screen.categoryDetails

import com.feature.categories.categoriesUi.shared.BaseViewModel
import com.feature.categories.categoriesUi.shared.CategoryUiState
import com.feature.categories.categoriesUi.shared.Status
import com.paris_2.domain.media.entity.MediaType
import com.paris_2.domain.media.useCase.GetMoviesByCategoryUseCase
import com.paris_2.domain.media.useCase.GetTvShowsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import com.paris_2.aflami.designsystem.R as RDesignSystem

@HiltViewModel
class CategoryDetailsScreenViewModel @Inject constructor(
    private val getMoviesByCategoryUseCase: GetMoviesByCategoryUseCase,
    private val getTvShowsByCategoryUseCase: GetTvShowsByCategoryUseCase,
) :
    CategoryDetailsScreenInteractionListener,
    BaseViewModel<CategoryDetailsScreenUIState>(CategoryDetailsScreenUIState()) {

    override fun onCategorySelected(category: CategoryUiState) {
        updateState(
            screenState.value.copy(
                categoryDetailsUIState = screenState.value.categoryDetailsUIState.copy(
                    selectedCategory = category,
                ),
            )
        )
    }

    fun initialCategory(category: CategoryUiState) {
        updateState(
            screenState.value.copy(
                categoryDetailsUIState = CategoryDetailsUIState(
                    mediaVisibility = false
                )
            )
        )
        updateState(
            screenState.value.copy(
                categoryDetailsUIState = screenState.value.categoryDetailsUIState.copy(
                    selectedCategory = category,
                    title = when (category.type) {
                        MediaType.TvShow -> RDesignSystem.string.tv_shows
                        MediaType.Movie -> RDesignSystem.string.movies
                    },
                    categories = when (category.type) {
                        MediaType.TvShow -> CategoryUiState.getTvShowsCategories()
                        MediaType.Movie -> CategoryUiState.getMoviesCategories()
                    },
                    mediaVisibility = true
                ),
            )
        )
    }
}