package com.feature.authentication.authenticationUi.screen.login.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun HeaderIconLogin(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()

    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = Theme.colors.primaryVariant,
                    shape = RoundedCornerShape(12.dp)
                )
                .border(
                    width = 1.dp,
                    color = Theme.colors.stroke,
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {

            IconLogin(
                modifier = Modifier
                    .graphicsLayer {
                        this.alpha = 1f
                        this.rotationZ = 0f
                    }
            )
            IconLogin(
                modifier = Modifier
                    .offset(x = (-3).dp, y = (-2).dp)
                    .graphicsLayer {
                        this.alpha = 0.6f
                        this.rotationZ = -15f
                    }
            )

            IconLogin(
                modifier = Modifier
                    .offset(x = (-6).dp, y = (-4).dp)
                    .graphicsLayer {
                        this.alpha = 0.24f
                        this.rotationZ = -25f
                    }
            )


        }
    }
}
