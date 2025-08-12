package com.feature.categories.categoriesUi.screen.categoryDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.feature.categories.categoriesUi.shared.CategoryUiState
import com.paris_2.aflami.designsystem.components.AppText

@Composable
fun CategoryDetailsScreen(category: CategoryUiState) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AppText(
            text = stringResource(category.name),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}