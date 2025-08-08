package com.paris_2.aflami.designsystem.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.paris_2.aflami.designsystem.theme.Theme

data class IconItem(
    val icon: ImageVector,
    val onClick: (() -> Unit)? = null,
    val backgroundColor: Color,
    val tint: Color,
)

@Composable
fun iconItemWithDefaults(
    icon: ImageVector,
    onClick: (() -> Unit)? = null,
    backgroundColor: Color = Theme.colors.surfaceHigh,
    tint: Color = Theme.colors.text.title,
): IconItem {
    return IconItem(
        icon = icon,
        onClick = onClick,
        backgroundColor = backgroundColor,
        tint = tint
    )
}