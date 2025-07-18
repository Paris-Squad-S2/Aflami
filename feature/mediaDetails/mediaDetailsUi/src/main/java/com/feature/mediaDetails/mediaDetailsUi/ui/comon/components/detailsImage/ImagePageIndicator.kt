package com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.detailsImage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun ImagePageIndicator(
    modifier: Modifier = Modifier,
    pageSize: Int,
    currentPage: Int,
    selectedColor: Color = Theme.colors.primary,
    unSelectedColor: Color = Theme.colors.text.hint
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .background(Theme.colors.primaryVariant)
            .padding(vertical = 4.dp, horizontal = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            repeat(pageSize) { pageIndex ->
                val isSelected = pageIndex == currentPage
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .height(if (isSelected) 24.dp else 4.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(
                            color = if (isSelected) selectedColor else unSelectedColor
                        )
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun ImagePageIndicatorPreview() {
    AflamiTheme {
        ImagePageIndicator(
            pageSize = 3,
            currentPage = 0,
            modifier = Modifier
                .padding(16.dp)
        )
    }
}