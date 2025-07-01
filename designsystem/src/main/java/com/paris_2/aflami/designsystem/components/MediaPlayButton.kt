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
    backGroundColor: Color? = null,
    boarderColor: Color = Theme.colors.stroke,
    showBoarder: Boolean = false,
    buttonSize: Int? = null,
    iconSize: Int? = null,
    onButtonClick: () -> Unit = {},
    buttonType: MediaButtonType
) {

    val (finalButtonSize, finalIconSize) = when (buttonType) {
        MediaButtonType.BIG -> (buttonSize?.dp ?: 64.dp) to (iconSize?.dp ?: 32.dp)
        MediaButtonType.MEDIUM -> (buttonSize?.dp ?: 40.dp) to (iconSize?.dp ?: 19.dp)
    }

    val finalBackGroundColor = backGroundColor ?: when (buttonType) {
        MediaButtonType.BIG ->  Theme.colors.onPrimaryColors.onPrimary
        MediaButtonType.MEDIUM -> Theme.colors.surfaceHigh
    }


    Box(
        modifier = modifier
            .size(finalButtonSize)
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
            .background(finalBackGroundColor)
            .clickable { onButtonClick() }
    ) {
        val iconPadding = if(buttonSize == 64) 10 else 3
        Image(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(start = iconPadding.dp)
                .size(finalIconSize),
            painter = painterResource(R.drawable.play_media),
            contentDescription = "play media"
        )
    }


}

enum class MediaButtonType {
    BIG,
    MEDIUM,
}

@PreviewLightDark
@Composable
fun PreviewPlayButton() {
    AflamiTheme {
        MediaPlayButton(buttonType = MediaButtonType.BIG)
    }
}

@PreviewLightDark
@Composable
fun PreviewSmallPlayButton() {
    AflamiTheme {
        MediaPlayButton(buttonType = MediaButtonType.MEDIUM,showBoarder = true)
    }
}