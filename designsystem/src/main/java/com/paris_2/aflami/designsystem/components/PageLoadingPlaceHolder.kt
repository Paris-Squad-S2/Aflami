package com.paris_2.aflami.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun PageLoadingPlaceHolder(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(Theme.colors.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AnimatedLoadingIcon(
            modifier = Modifier
                .size(48.dp)
                .padding(bottom = 8.dp),
            color = Theme.colors.primary
        )
        Text(
            text = "Loading...",
            style = Theme.textStyle.label.medium,
            color = Theme.colors.text.body
        )
    }
}

@Composable
@PreviewLightDark
fun PageLoadingPlaceHolderPreview() {
    AflamiTheme {
        PageLoadingPlaceHolder()
    }
}

@Composable
@PreviewLightDark
fun PageLoadingPlaceHolderPreview2() {
    AflamiTheme {
        PageLoadingPlaceHolder(
            modifier = Modifier.fillMaxSize()
        )
    }
}
