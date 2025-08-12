package com.feature.categories.categoriesUi

import androidx.compose.runtime.Composable
import com.feature.categories.categoriesApi.CategoriesFeatureAPI
import com.feature.categories.categoriesUi.screen.categories.CategoriesScreen

class CategoriesFeatureAPIImpl : CategoriesFeatureAPI {
    override fun invoke(): @Composable (() -> Unit) {
        return {
            CategoriesScreen()
        }
    }
}