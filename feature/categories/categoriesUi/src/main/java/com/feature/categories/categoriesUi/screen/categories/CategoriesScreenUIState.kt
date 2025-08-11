package com.feature.categories.categoriesUi.screen.categories

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.paris_2.domain.media.entity.Category

data class CategoriesScreenUIState(
    val categoriesUIState: CategoriesUIState = CategoriesUIState(),
    val status: Status = Status.Normal
)

enum class Status {
    Normal,
    Loading,
    NetworkError,
    UnknownError,
}

data class CategoriesUIState(
    val selectedTabIndex: Int = 0,
    val moviesCategories: List<CategoryUiState> = listOf(
//        CategoryUiState(
//            category = Category.Action,
//            name = R.string.action,
//            icon = R.drawable.
//        ),
    ),
    val tvShowsCategories: List<CategoryUiState> = emptyList(),
)

data class CategoryUiState(
    val category: Category,
    @StringRes val name: Int,
    @DrawableRes val icon: Int,
)