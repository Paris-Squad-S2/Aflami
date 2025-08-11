package com.feature.categories.categoriesUi.screen.categoryDetails

import com.paris_2.domain.media.entity.Category

data class CategoryDetailsScreenUIState(
    val categoryDetailsUIState: CategoryDetailsUIState = CategoryDetailsUIState(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class CategoryDetailsUIState(
    val categoryDetails: List<Category> = emptyList()
)