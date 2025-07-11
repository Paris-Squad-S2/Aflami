package com.paris_2.aflami.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme
import dropShadow


@Composable
fun GameCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    backgroundColors: List<Color>,
    trailingImages: List<Painter>,
    onPlayClick: () -> Unit,
    isPlayButtonLocked: Boolean,
    pointsToUnlock: Int = 0
) {

    Column(
        modifier = modifier
            .height(140.dp)
            .dropShadow(
                shape = RoundedCornerShape(16.dp),
                color = backgroundColors.first().copy(alpha = 0.12f),
                blur = 12.dp,
                offsetY = 4.dp,
                offsetX = 0.dp,
                spread = 0.dp
            )
            .background(color = Theme.colors.surfaceHigh, shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(15.dp))
            .border(
                width = 1.dp, brush = Brush.linearGradient(
                    colorStops = arrayOf(
                        0.0f to backgroundColors.first().copy(alpha = 0.50f),
                        0.75f to Theme.colors.surfaceHigh.copy(alpha = 0.02f),
                        1f to Theme.colors.surfaceHigh.copy(alpha = 0.02f),
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(1000f, 0f)
                ),
                shape = RoundedCornerShape(16.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (isPlayButtonLocked)
            Text(
                text = pointsToUnlock.toString() + "\t" + stringResource(R.string.pts_to_unlock),
                style = Theme.textStyle.label.small,
                color = Theme.colors.status.yellowAccent,
                modifier = Modifier.padding(vertical = 4.dp)
            )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colorStops = arrayOf(
                            0.0f to backgroundColors.first(),
                            0.75f to backgroundColors.first(),
                            1.0f to backgroundColors.last().copy(alpha = 0.50f)
                        )
                    )
                )
        ) {

            Column(
                modifier = Modifier
                    .padding(vertical = if (isPlayButtonLocked) 4.dp else 8.dp)
                    .fillMaxSize()
                    .padding(start = 12.dp, end = LocalConfiguration.current.screenWidthDp.dp / 4),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = title,
                    color = Theme.colors.text.title,
                    style = Theme.textStyle.title.small,
                    maxLines = 1,
                )
                Text(
                    text = description,
                    color = Theme.colors.text.body,
                    style = Theme.textStyle.body.small,
                    maxLines = 2,
                )
                Surface(
                    onClick = onPlayClick,
                    enabled = !isPlayButtonLocked,
                    color = Theme.colors.onPrimaryColors.onPrimaryButton,
                    contentColor = Theme.colors.text.title,
                    border = BorderStroke(width = 0.5.dp, brush = playButtonBorderColor),
                    shape = if (!isPlayButtonLocked) RoundedCornerShape(16.dp) else CircleShape,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .offset(x = if (isPlayButtonLocked) (-10).dp else 0.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.6.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        if (!isPlayButtonLocked) {
                            Text(
                                text = stringResource(R.string.play_now),
                                style = Theme.textStyle.label.small,
                                color = Theme.colors.text.title
                            )
                            Icon(
                                painter = painterResource(R.drawable.ic_play_circle),
                                contentDescription = "icon play",
                                tint = Theme.colors.text.title
                            )
                        } else
                            Icon(
                                painter = painterResource(R.drawable.ic_locked),
                                contentDescription = "icon lock",
                                tint = Theme.colors.text.title
                            )
                    }
                }
            }



            Box(
                modifier = Modifier
                    .fillMaxSize()

            ) {

                Image(
                    painter = painterResource(R.drawable.linear_light),
                    contentDescription = "linear light",
                    modifier = Modifier
                        .align(Alignment.TopEnd),

                )


                Image(
                    painter = painterResource(R.drawable.linear_light_small),
                    contentDescription = "linear small ",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .align(Alignment.BottomEnd)
                )

            }


            when (trailingImages.size) {
                1 -> {
                    Image(
                        modifier = Modifier
                            .align(Alignment.BottomEnd),
                        painter = trailingImages.first(),
                        contentScale = ContentScale.FillHeight,
                        contentDescription = "leading game icon"
                    )
                }

                3 -> {

                    Image(
                        modifier = Modifier
                            .padding(end = 60.dp, top = 10.dp)
                            .align(Alignment.BottomEnd)
                            .offset(y = 70.dp)
                            .rotate(-20.62f)
                            .clip(RoundedCornerShape(12.dp)),
                        painter = trailingImages.first(),
                        contentDescription = "trailing game icon",
                    )

                    Image(
                        modifier = Modifier
                            .padding(end = 25.dp, top = 40.dp)
                            .align(Alignment.BottomEnd)
                            .offset(y = 20.dp)
                            .rotate(-15.32f)
                            .clip(RoundedCornerShape(12.dp)),
                        painter = trailingImages[1],
                        contentDescription = "center game icon"
                    )

                    Image(
                        modifier = Modifier
                            .padding(end = 2.dp, top = 35.dp)
                            .align(Alignment.BottomEnd)
                            .offset(y = 10.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        painter = trailingImages.last(),
                        contentDescription = "leading game icon"
                    )

                }

            }


        }
    }
}

private val playButtonBorderColor = Brush.linearGradient(
    colorStops = arrayOf(
        0.0f to Color.White.copy(alpha = 0.08f),
        0.8f to Color.White.copy(alpha = 0.08f),
        1.0f to Color.White.copy(alpha = 0.24f)
    )
)

@PreviewLightDark
@Composable
private fun GameCardCalenderPreview() {
    AflamiTheme(isDarkTheme = true) {
        GameCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            title = "When Was It Released?",
            description = "Pick the right release year",
            backgroundColors = listOf(
                Theme.colors.status.navyCard,
                Theme.colors.status.darkBlue
            ),
            trailingImages = listOf(painterResource(R.drawable.ic_purpl_calendar)),
            onPlayClick = {},
            isPlayButtonLocked = false,
        )
    }
}

@PreviewLightDark
@Composable
private fun GameCardCalenderLockedPreview() {
    AflamiTheme {
        GameCard(
            modifier = Modifier
                .width(328.dp)
                .height(140.dp),
            title = "When Was It Released?",
            description = "Pick the right release year",
            backgroundColors = listOf(Theme.colors.status.navyCard, Theme.colors.status.darkBlue),
            trailingImages = listOf(painterResource(R.drawable.ic_purpl_calendar)),
            onPlayClick = {},
            isPlayButtonLocked = true,
            pointsToUnlock = 400,
        )
    }
}


@PreviewLightDark
@Composable
private fun GameCardChairPreview() {
    AflamiTheme(isDarkTheme = true) {
        GameCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            title = "Which Genre?",
            description = "Which one is the real action movie?",
            backgroundColors = listOf(Theme.colors.status.yellowCard, Theme.colors.status.yellowAccent),
            trailingImages = listOf(painterResource(R.drawable.image_chair)),
            onPlayClick = {},
            isPlayButtonLocked = false,
        )
    }
}

@PreviewLightDark
@Composable
private fun GameCardChairLockedPreview() {
    AflamiTheme {
        GameCard(
            modifier = Modifier
                .width(328.dp)
                .height(140.dp),
            title = "Which Genre?",
            description = "Which one is the real action movie?",
            backgroundColors = listOf(Theme.colors.status.yellowCard, Theme.colors.status.yellowAccent),
            trailingImages = listOf(painterResource(R.drawable.image_chair)),
            onPlayClick = {},
            isPlayButtonLocked = true,
            pointsToUnlock = 400,
        )
    }
}


@PreviewLightDark
@Composable
private fun GameCardClownPreview() {
    AflamiTheme(isDarkTheme = true) {
        GameCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            title = "Guess the Character",
            description = "Can you tell who this characher?",
            backgroundColors = listOf(Theme.colors.primaryVariant, Theme.colors.primary),
            trailingImages = listOf(painterResource(R.drawable.image_clown)),
            onPlayClick = {},
            isPlayButtonLocked = false,
        )
    }
}

@PreviewLightDark
@Composable
private fun GameCardClownLockedPreview() {
    AflamiTheme {
        GameCard(
            modifier = Modifier
                .width(328.dp)
                .height(140.dp),
            title = "Guess the Character",
            description = "Can you tell who this characher?",
            backgroundColors = listOf(Theme.colors.primaryVariant, Theme.colors.primary),
            trailingImages = listOf(painterResource(R.drawable.image_clown)),
            onPlayClick = {},
            isPlayButtonLocked = true,
            pointsToUnlock = 400,
        )
    }
}


@PreviewLightDark
@Composable
private fun GameCardThreeImagesPreview() {
    AflamiTheme {
        GameCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            title = "Guess the Movie by Poster",
            description = "Match the poster with the right title!",
            backgroundColors = listOf(Theme.colors.status.blueCard, Theme.colors.status.blueAccent),
            trailingImages = listOf(
                painterResource(R.drawable.image_game2),
                painterResource(R.drawable.image_game2),
                painterResource(R.drawable.image_game2)
            ),
            onPlayClick = {},
            isPlayButtonLocked = false,
            pointsToUnlock = 400
        )
    }

}

@PreviewLightDark
@Composable
private fun GameCardThreeImagesLockedPreview() {
    AflamiTheme {
        GameCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            title = "Guess the Movie by Poster",
            description = "Match the poster with the right title!",
            backgroundColors = listOf(Theme.colors.status.blueCard, Theme.colors.status.blueAccent),
            trailingImages = listOf(
                painterResource(R.drawable.image_game2),
                painterResource(R.drawable.image_game2),
                painterResource(R.drawable.image_game2)
            ),
            onPlayClick = {},
            isPlayButtonLocked = true,
            pointsToUnlock = 400
        )
    }

}


