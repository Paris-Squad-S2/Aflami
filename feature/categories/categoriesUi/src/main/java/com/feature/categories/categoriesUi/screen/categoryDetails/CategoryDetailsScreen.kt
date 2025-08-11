package com.feature.categories.categoriesUi.screen.categoryDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.paris_2.aflami.designsystem.components.AppText

@Composable
fun CategoryDetailsScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AppText(
            text = "CategoryDetails",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}