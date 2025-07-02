package com.paris_2.aflami.designsystem.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.components.ButtonState
import com.paris_2.aflami.designsystem.components.ButtonType
import com.paris_2.aflami.designsystem.theme.Theme


@Composable
fun getBackgroundBrush(
    type: ButtonType,
    isDisabled: Boolean,
    isNegative: Boolean
): Brush {
    if (isDisabled) {
        return when (type) {
            ButtonType.TextButton -> Brush.verticalGradient(
                colors = listOf(Color.Transparent, Color.Transparent)
            )

            ButtonType.Secondary -> Brush.verticalGradient(
                colors = listOf(
                    Theme.colors.primaryVariant,
                    Theme.colors.primaryVariant,
                )
            )

            else -> Brush.verticalGradient(
                colors = listOf(
                    Theme.colors.disable,
                    Theme.colors.disable
                )
            )
        }
    } else if (isNegative) {
        return when (type) {
            ButtonType.TextButton -> Brush.verticalGradient(
                colors = listOf(Color.Transparent, Color.Transparent)
            )

            else ->
                Brush.verticalGradient(
                    colors = listOf(
                        Theme.colors.status.redVariant,
                        Theme.colors.status.redVariant,
                    )
                )
        }
    } else {
        return when (type) {
            ButtonType.Secondary -> Brush.verticalGradient(
                colors = listOf(
                    Theme.colors.primaryVariant,
                    Theme.colors.primaryVariant,
                )
            )

            ButtonType.TextButton -> Brush.verticalGradient(
                colors = listOf(Color.Transparent, Color.Transparent)
            )

            else -> Brush.verticalGradient(
                colors = listOf(
                    Theme.colors.primary,
                    Color(0xFF973A66)
                )
            )
        }
    }
    return Brush.verticalGradient(
        colors = listOf(Color.Transparent, Color.Transparent)
    )
}


@Composable
fun getContentColor(
    state: ButtonState,
    type: ButtonType,
    isNegative: Boolean
): Color {
    return when {
        state == ButtonState.Disabled -> when (type) {
            ButtonType.TextButton -> Theme.colors.disable
            else -> Theme.colors.stroke
        }

        isNegative -> Theme.colors.status.redAccent

        else -> when (type) {
            ButtonType.TextButton, ButtonType.Secondary -> Theme.colors.primary
            ButtonType.Primary, ButtonType.FloatingActionButton -> Theme.colors.onPrimaryColors.onPrimary
        }
    }
}

@Composable
fun getContentPadding(type: ButtonType): PaddingValues {
    return when (type) {
        ButtonType.TextButton -> PaddingValues(horizontal = 0.dp, vertical = 0.dp)
        ButtonType.FloatingActionButton -> PaddingValues(horizontal = 18.dp, vertical = 18.dp)
        else -> PaddingValues(horizontal = 24.dp, vertical = 16.dp)
    }
}
