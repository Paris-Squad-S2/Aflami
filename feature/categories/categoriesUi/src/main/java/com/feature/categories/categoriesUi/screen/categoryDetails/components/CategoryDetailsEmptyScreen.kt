package com.feature.categories.categoriesUi.screen.categoryDetails.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.feature.categories.categoriesUi.R
import com.paris_2.aflami.designsystem.components.PlaceholderView

@Composable
fun CategoryDetailsEmptyScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        PlaceholderView(
            image = painterResource(R.drawable.img_state_no_items_for_this_genre),
            subTitle = stringResource(R.string.no_items_for_this_genre),
            imageSize = 144.dp,
            spacer = 24.dp
        )
    }
}