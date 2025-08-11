package com.feature.categories.categoriesUi.screen.categoryDetails

import com.paris_2.domain.media.entity.Category

interface CategoryDetailsScreenInteractionListener {
    fun onCategoryClick(category: Category)
}