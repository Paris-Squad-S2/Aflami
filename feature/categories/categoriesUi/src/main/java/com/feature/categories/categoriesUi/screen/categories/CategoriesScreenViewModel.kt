package com.feature.categories.categoriesUi.screen.categories

import com.feature.categories.categoriesUi.common.BaseViewModel
import com.paris_2.domain.media.useCase.GetMoviesByCategoryUseCase
import com.paris_2.domain.media.useCase.GetTvShowsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoriesScreenViewModel @Inject constructor(
    private val getMoviesByCategoryUseCase: GetMoviesByCategoryUseCase,
    private val getTvShowsByCategoryUseCase: GetTvShowsByCategoryUseCase
) : CategoriesScreenInteractionListener, BaseViewModel<CategoriesScreenUIState>(
    CategoriesScreenUIState()
) {

    override fun onCategoryClick(category: CategoryUiState) {
//        TODO("Not yet implemented")
    }

    override fun onRetry() {
//        TODO("Not yet implemented")
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