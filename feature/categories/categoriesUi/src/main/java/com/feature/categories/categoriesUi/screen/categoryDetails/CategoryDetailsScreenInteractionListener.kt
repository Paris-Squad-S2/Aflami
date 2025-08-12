package com.feature.categories.categoriesUi.screen.categoryDetails

import com.feature.categories.categoriesUi.shared.CategoryUiState

interface CategoryDetailsScreenInteractionListener {
    fun onCategorySelected(category: CategoryUiState)
    fun onRetry()
    fun onMediaSelected(media: MediaUI)
}