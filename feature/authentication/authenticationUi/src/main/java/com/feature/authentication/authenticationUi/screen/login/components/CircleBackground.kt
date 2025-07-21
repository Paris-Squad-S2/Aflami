package com.feature.authentication.authenticationUi.screen.login.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun CircleBackground(xPercentage: Float, yPercentage: Float,size: Dp, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(size)
            .offset(
                x = with(LocalConfiguration.current) { (screenWidthDp * xPercentage).dp },
                y = with(LocalConfiguration.current) { (screenHeightDp * yPercentage).dp })

            .background(
                color = Theme.colors.status.backgroundCircles,
                shape = CircleShape
            )
    )
}