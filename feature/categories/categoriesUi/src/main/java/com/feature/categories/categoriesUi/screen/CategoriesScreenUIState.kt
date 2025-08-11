package com.feature.categories.categoriesUi.screen

import com.paris_2.domain.media.entity.Category

data class CategoriesScreenUIState(
    val categoriesUIState: CategoriesUIState,
    val isLoading: Boolean,
    val errorMessage: String?
)

data class CategoriesUIState(
    val categories: List<Category>
)