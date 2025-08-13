package com.feature.guessGame.guessGameUi.screen.guessbyimage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.feature.guessGame.guessGameUi.common.components.GameTimer
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.components.AppIcon
import com.paris_2.aflami.designsystem.components.AppText
import com.paris_2.aflami.designsystem.theme.Theme
import com.paris_2.aflami.designsystem.utils.BasePreview

@Composable
fun GuessByImageScreen(modifier: Modifier = Modifier) {
    GussByImageScreenContent(modifier = modifier.fillMaxWidth())
}

@Composable
private fun GussByImageScreenContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Header()
    }
}

@Composable
private fun Header(modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        AppIcon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_cancel),
            contentDescription = null,
            tint = Theme.colors.text.title,
            modifier = Modifier
                .background(
                    color = Theme.colors.surfaceHigh,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(10.dp)
        )
        AppText(
            text = "Guess the Poster",
            style = Theme.textStyle.title.large,
            color = Theme.colors.text.title,
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)
        )
        GameTimer(
            totalSeconds = 45
        )
    }
}

@PreviewLightDark
@Composable
private fun GuessByImagePrev() {
    BasePreview {
        GuessByImageScreen()
    }
}