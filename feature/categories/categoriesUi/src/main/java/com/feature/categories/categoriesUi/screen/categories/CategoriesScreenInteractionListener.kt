package com.feature.categories.categoriesUi.screen.categories

interface CategoriesScreenInteractionListener {
    fun onCategoryClick(category: CategoryUiState)
    fun onRetry()
    fun onSelectTab(index: Int)
}