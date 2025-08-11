package com.feature.categories.categoriesUi.screen.categoryDetails

import com.feature.categories.categoriesUi.common.BaseViewModel
import com.paris_2.domain.media.entity.Category
import com.paris_2.domain.media.useCase.GetMoviesByCategoryUseCase
import com.paris_2.domain.media.useCase.GetTvShowsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryDetailsScreenViewModel @Inject constructor (
    private val getMoviesByCategoryUseCase: GetMoviesByCategoryUseCase,
    private val getTvShowsByCategoryUseCase: GetTvShowsByCategoryUseCase
) : CategoryDetailsScreenInteractionListener, BaseViewModel<CategoryDetailsScreenUIState>(
    CategoryDetailsScreenUIState()
    )
{

    override fun onCategoryClick(category: Category) {
        TODO("Not yet implemented")
    }
}