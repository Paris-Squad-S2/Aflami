package com.feature.categories.categoriesUi.screen.categoryDetails

import com.feature.categories.categoriesUi.shared.CategoryUiState
import com.paris_2.domain.media.entity.Media

interface CategoryDetailsScreenInteractionListener {
    fun onCategorySelected(category: CategoryUiState)
    fun onRetry()
    fun onMediaSelected(media: Media)
}