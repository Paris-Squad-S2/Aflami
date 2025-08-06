package com.feature.onboarding.onboardingUi.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.theme.Theme
import com.paris_2.aflami.designsystem.utils.BasePreview

@Composable
fun PageIndicator(totalPages: Int, currentPage: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(totalPages) { index ->
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .clip(CircleShape)
                    .border(1.dp,Theme.colors.stroke,
                        CircleShape)
                    .background(if (index == currentPage) Theme.colors.onPrimaryColors.onPrimary else Theme.colors.onPrimaryColors.onPrimaryBody,
                        CircleShape)
            )
        }
    }
}

@Preview
@Composable
private fun PageIndicatorPreview() {
    BasePreview{
      PageIndicator(
          4,1
      )
    }
}
