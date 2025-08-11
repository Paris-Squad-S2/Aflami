package com.feature.categories.categoriesUi.screen.categories

import com.feature.categories.categoriesUi.common.BaseViewModel
import com.feature.categories.categoriesUi.navigation.CategoriesDestinations
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class CategoriesScreenViewModel @Inject constructor() : CategoriesScreenInteractionListener,
    BaseViewModel<CategoriesScreenUIState>(
        CategoriesScreenUIState()
    ) {

    override fun onCategoryClick(category: CategoryUiState) {
        navigate(
            CategoriesDestinations.CategoryDetailsScreen(
                category = category.category.name
            )
        )
    }

    override fun onSelectTab(index: Int) {
        updateState(
            screenState.value.copy(
                categoriesUIState = screenState.value.categoriesUIState.copy(
                    selectedTabIndex = index
                )
            )
        )
    }
}