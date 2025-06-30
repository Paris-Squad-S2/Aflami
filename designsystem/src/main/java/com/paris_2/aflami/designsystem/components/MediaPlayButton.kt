package com.paris_2.aflami.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun MediaPlayButton(
    modifier: Modifier = Modifier,
    backGroundColor: Color = Theme.colors.onPrimaryColors.onPrimary,
    boarderColor: Color = Theme.colors.stroke,
    showBoarder: Boolean = false,
    buttonSize: Int = 64,
    iconSize: Int = 32,
    onButtonClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .size(buttonSize.dp)
            .then(
                if (showBoarder) {
                    Modifier.border(
                        width = 1.dp,
                        color = boarderColor,
                        shape = CircleShape
                    )
                } else Modifier
            )
            .clip(CircleShape)
            .background(backGroundColor)
            .clickable { onButtonClick() }
    ) {
        val iconPadding = if(buttonSize == 64) 10 else 3
        Image(
            modifier = Modifier
                .align(Alignment.Center).padding(start = iconPadding.dp)
                .size(iconSize.dp),
            painter = painterResource(R.drawable.play_media),
            contentDescription = "play media"
        )
    }


}

@PreviewLightDark
@Composable
fun PreviewPlayButton() {
    AflamiTheme {
        MediaPlayButton(backGroundColor = Theme.colors.onPrimaryColors.onPrimary)
    }
}

@PreviewLightDark
@Composable
fun PreviewSmallPlayButton() {
    AflamiTheme {
        MediaPlayButton(buttonSize = 40, iconSize = 19, backGroundColor = Theme.colors.surfaceHigh, showBoarder = true)
    }
}