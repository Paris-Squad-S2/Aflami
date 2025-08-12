package com.feature.categories.categoriesUi.screen.categoryDetails

import com.feature.categories.categoriesUi.shared.CategoryUiState
import com.feature.categories.categoriesUi.shared.Status
import com.paris_2.aflami.designsystem.R as RDesignSystem

data class CategoryDetailsScreenUIState(
    val categoryDetailsUIState: CategoryDetailsUIState = CategoryDetailsUIState(),
    val status: Status = Status.Loading
)

data class CategoryDetailsUIState(
    val selectedTabIndex: Int = 0,
    val mediaVisibility: Boolean = false,
    val categories: List<CategoryUiState> = CategoryUiState.getMoviesCategories(),
    val title: Int = RDesignSystem.string.movies,
    val selectedCategory: CategoryUiState = CategoryUiState.getDefault()
)