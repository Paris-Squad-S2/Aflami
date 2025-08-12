package com.feature.guessGame.guessGameUi.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.feature.guessGame.guessGameUi.R
import com.paris_2.aflami.designsystem.components.AppText
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun GameResultCard(
    value: Int,
    isPoint: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(190.dp)
            .width(160.dp)
    ) {
        Box(
            modifier = modifier
                .height(172.dp)
                .width(160.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Theme.colors.surface)
                .align(Alignment.BottomCenter)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 1.dp)
                        .height(92.dp)
                        .width(156.dp)
                        .border(
                            width = 1.dp,
                            color = Theme.colors.stroke,
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_light_glow),
                        contentDescription = if (isPoint) stringResource(R.string.points_achieved)
                        else stringResource(R.string.total_time),
                    )
                }
                AppText(
                    text = if (isPoint) stringResource(R.string.points_achieved) else stringResource(
                        R.string.total_time
                    ),
                    color = Theme.colors.text.hint,
                    style = Theme.textStyle.label.medium,
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 4.dp)
                )
                AppText(
                    text = if (isPoint) stringResource(R.string.pts, value) else
                        stringResource(R.string.sec, value),
                    color = Theme.colors.text.title,
                    style = Theme.textStyle.headline.medium
                )
            }
        }
        Image(
            painter = if (isPoint)
                painterResource(R.drawable.ic_star_point)
            else
                painterResource(R.drawable.ic_timer),
            contentDescription = if (isPoint) stringResource(R.string.points_achieved)
            else stringResource(R.string.total_time),
            modifier = Modifier
                .size(70.dp)
                .align(Alignment.TopCenter)
        )
    }

}

@PreviewLightDark
@Composable
private fun Preview() {
    AflamiTheme {
        GameResultCard(
            value = 100,
            isPoint = true,
        )
    }
}
