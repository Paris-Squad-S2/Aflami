package com.feature.profile.profileUi.screen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.feature.profile.profileUi.R
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun ProfileBackGround(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
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

        Text(
            text = stringResource(R.string.profile),
            color = Theme.colors.text.title,
            style = Theme.textStyle.title.large,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 54.dp)
        )
    }
}