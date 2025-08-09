package com.feature.categories.categoriesUi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.feature.categories.categoriesApi.CategoriesFeatureAPI
import com.paris_2.aflami.designsystem.components.AppText

class CategoriesFeatureAPIImpl : CategoriesFeatureAPI {
    override fun invoke(): @Composable (() -> Unit) {
        return {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                AppText(
                    text = "Categories Feature",
                )
            }
        }
    }

}