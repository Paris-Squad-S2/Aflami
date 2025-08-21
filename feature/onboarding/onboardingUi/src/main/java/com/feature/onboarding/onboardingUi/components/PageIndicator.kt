package com.feature.onboarding.onboardingUi.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris.aflami.designsystem.theme.AflamiTheme
import com.paris.aflami.designsystem.theme.Theme

@Composable
fun PageIndicator(
    totalPages: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        repeat(totalPages) { index ->
            val boxColor by animateColorAsState(
                targetValue = if (index == currentPage)
                    Theme.colors.onPrimaryColors.onPrimary
                else
                    Theme.colors.onPrimaryColors.onPrimaryHint,
                label = "BoxColor"
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(8.dp)
                    .border(
                        1.dp, Theme.colors.stroke,
                        CircleShape
                    )
                    .clip(RoundedCornerShape(4.dp))
                    .background(boxColor)
            )
        }
    }
}

@Preview
@Composable
private fun PageIndicatorPreview() {
    AflamiTheme {
      PageIndicator(
          4,1
      )
    }
}
