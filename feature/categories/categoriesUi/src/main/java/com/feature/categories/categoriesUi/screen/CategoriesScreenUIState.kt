package com.feature.categories.categoriesUi.screen

import com.paris_2.domain.media.entity.Category

data class CategoriesScreenUIState(
    val categoriesUIState: CategoriesUIState = CategoriesUIState(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class CategoriesUIState(
    val categories: List<Category> = emptyList()
)