package com.feature.guessGame.guessGameUi.screen.guessGameScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.components.AppIcon
import com.paris_2.aflami.designsystem.components.AppText
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun PointsBadge(points: Int, modifier: Modifier = Modifier) {
    val colors = Theme.colors.gradient.pointsOverly
    Box(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    colors = colors
                ),
                shape = RoundedCornerShape(50)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            AppText(
                text = stringResource(
                    com.feature.guessGame.guessGameUi.R.string.points_label,
                    points
                ),
                color = Theme.colors.onPrimaryColors.onPrimary,
                fontSize = 14.sp,
                style = Theme.textStyle.label.small
            )
            AppIcon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_horisontal_star),
                contentDescription = "Points",
                tint = Theme.colors.onPrimaryColors.onPrimary,
                modifier = Modifier.size(16.dp)
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PointsBadgePreview() {
    PointsBadge(points = 35)
}
