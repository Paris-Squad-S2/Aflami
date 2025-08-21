package com.feature.profile.profileUi.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.feature.profile.profileUi.R
import com.paris.aflami.designsystem.theme.Theme

@Composable
fun ProfileHeader(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.colors.surface.copy(alpha = 0.5F))
    )
    {
        Image(
            painter = painterResource(R.drawable.ic_profile_bg),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .blur(2.dp),
            contentScale = ContentScale.FillWidth
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Theme.colors.surface.copy(alpha = 0.5f))
        )
        Box(
            modifier = Modifier

                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Theme.colors.surface, Theme.colors.surface.copy(alpha = 0.0F)
                        )
                    )
                )
                .align(Alignment.TopCenter)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(78.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Theme.colors.surface.copy(alpha = 0.0F),
                            Theme.colors.surface
                        )
                    )
                )
                .align(Alignment.BottomCenter)
        )

    }
}