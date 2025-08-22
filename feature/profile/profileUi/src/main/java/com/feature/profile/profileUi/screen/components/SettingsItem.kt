package com.feature.profile.profileUi.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.paris.aflami.designsystem.components.AppIcon
import com.paris.aflami.designsystem.components.AppText
import com.paris.aflami.designsystem.theme.Theme

@Composable
fun SettingsItem(
    modifier: Modifier = Modifier,
    icon: Int,
    tint : Color = Theme.colors.text.body,
    title: String,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .clickable(enabled = true, onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp, color = Theme.colors.stroke,
                    shape = RoundedCornerShape(12.dp)
                )
                .background(
                    color = Theme.colors.surfaceHigh,
                    shape = RoundedCornerShape(12.dp)
                )

                .padding(8.dp)
        ) {
            AppIcon(
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = null,
                tint = tint,
            )
        }
        AppText(
            text = title,
            style = Theme.textStyle.label.large,
            color = Theme.colors.text.title,
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(1F)
        )
        content()

    }
}