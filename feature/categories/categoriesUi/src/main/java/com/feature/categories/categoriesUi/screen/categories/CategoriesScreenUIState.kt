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
    val categories: List<CategoryUiState> = emptyList()
)

data class CategoryUiState(
    @StringRes val name: Int,
    @DrawableRes val icon: Int,
)