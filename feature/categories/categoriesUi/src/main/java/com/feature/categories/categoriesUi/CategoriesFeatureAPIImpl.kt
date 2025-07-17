package com.feature.categories.categoriesUi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.feature.categories.categoriesApi.CategoriesDestination
import com.feature.categories.categoriesApi.CategoriesFeatureAPI

class CategoriesFeatureAPIImpl : CategoriesFeatureAPI {
    override fun invoke(categoriesDestination: CategoriesDestination?): @Composable (() -> Unit) {
        return {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Categories Feature",
                )
            }
        }
    }

}