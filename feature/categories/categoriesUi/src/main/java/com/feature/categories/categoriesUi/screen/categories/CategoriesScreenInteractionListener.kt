package com.feature.categories.categoriesUi.screen.categories

import com.paris_2.domain.media.entity.Category

interface CategoriesScreenInteractionListener {
    fun onCategoryClick(category: Category)
}