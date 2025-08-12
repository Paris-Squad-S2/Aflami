package com.feature.categories.categoriesUi.screen.categories

import com.feature.categories.categoriesUi.shared.CategoryUiState
import com.feature.categories.categoriesUi.shared.Status

data class CategoriesScreenUIState(
    val categoriesUIState: CategoriesUIState = CategoriesUIState(),
    val status: Status = Status.Normal
)

data class CategoriesUIState(
    val selectedTabIndex: Int = 0,
    val moviesCategories: List<CategoryUiState> = CategoryUiState.getMoviesCategories(),
    val tvShowsCategories: List<CategoryUiState> = CategoryUiState.getTvShowsCategories(),
)