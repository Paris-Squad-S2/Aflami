package com.feature.categories.categoriesUi.screen.categories

interface CategoriesScreenInteractionListener {
    fun onCategoryClick(category: CategoryUiState)
    fun onSelectTab(index: Int)
}