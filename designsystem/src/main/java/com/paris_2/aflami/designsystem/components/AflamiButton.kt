package com.paris_2.aflami.designsystem.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.modifiers.innerShadow
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme
import com.paris_2.aflami.designsystem.utils.getBackgroundBrush
import com.paris_2.aflami.designsystem.utils.getContentColor
import com.paris_2.aflami.designsystem.utils.getContentPadding

// TODO: I will refactor it later

enum class ButtonState {
    Normal,
    Disabled,
    Loading
}

sealed class ButtonType {
    object Primary : ButtonType()
    object Secondary : ButtonType()
    object TextButton : ButtonType()
    object FloatingActionButton : ButtonType()
}

@Composable
fun AflamiButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    state: ButtonState = ButtonState.Normal,
    type: ButtonType,
    isNegative: Boolean = false,
    text: String? = null,
    icon: @Composable (() -> Unit)? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isLoading = (state == ButtonState.Loading)
    val enabled = (state == ButtonState.Normal)
    val spacing = 8.dp

    val backgroundColorBrush = getBackgroundBrush(
        type,
        isDisabled = (state == ButtonState.Disabled),
        isNegative = isNegative,
    )
    val buttonContentColor = getContentColor(state, type, isNegative)
    val buttonElevation = when (type) {
        ButtonType.FloatingActionButton -> 6.dp
        else -> 0.dp
    }
    val iShadow =
        if ((type == ButtonType.FloatingActionButton || type == ButtonType.Primary) && state != ButtonState.Disabled && !isNegative) {
            Modifier.innerShadow(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFFFFFFF).copy(alpha = 0.5f),
                blur = 8.dp,
                offsetY = 4.dp,
                offsetX = 0.dp,
            )
        } else {
            Modifier
        }

    Surface(
        modifier = modifier
            .then(iShadow)
            .shadow(elevation = buttonElevation, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        color = Color.Transparent,
        contentColor = buttonContentColor,
        onClick = { if (enabled) onClick() },
        enabled = enabled,
        interactionSource = interactionSource,
    ) {
        Row(
            modifier = Modifier
                .background(backgroundColorBrush)
                .padding(getContentPadding(type)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            text?.let {
                Text(
                    it,
                    style = Theme.textStyle.label.large,
                    color = buttonContentColor
                )
            }

            if (isLoading) {
                if (text != null) Spacer(modifier = Modifier.width(spacing))
                AnimatedVisibility(visible = true, enter = fadeIn()) {
                    AnimatedLoadingIcon(
                        modifier = Modifier.size(20.dp),
                        color = buttonContentColor
                    )
                }
            } else {
                icon?.let {
                    if (text != null) Spacer(modifier = Modifier.width(spacing))
                    it()
                }
            }
        }
    }
}


//======================================= Previews ========================================

@Composable
@PreviewLightDark
fun PrimaryButton() {
    AflamiTheme {
        Surface(color = Theme.colors.surface)
        {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AflamiButton(
                    onClick = { }, text = "Submit",
                    type = ButtonType.Primary,
                )
                AflamiButton(
                    onClick = { }, text = "Submit",
                    type = ButtonType.Primary,
                    state = ButtonState.Disabled
                )
                AflamiButton(
                    onClick = { }, text = "Submit",
                    type = ButtonType.Primary,
                    state = ButtonState.Loading
                )
                AflamiButton(
                    onClick = { }, text = "Submit",
                    type = ButtonType.Primary,
                    state = ButtonState.Normal,
                    icon = {
                        Icon(Icons.Default.Add, null)
                    },
                )
            }
        }
    }
}


@Composable
@PreviewLightDark
fun SecondaryButton() {
    AflamiTheme {
        Surface(color = Theme.colors.surface)
        {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AflamiButton(
                    onClick = { }, text = "Submit",
                    type = ButtonType.Secondary,
                )
                AflamiButton(
                    onClick = { }, text = "Submit",
                    type = ButtonType.Secondary,
                    state = ButtonState.Loading
                )
                AflamiButton(
                    onClick = { },
                    type = ButtonType.Secondary,
                    state = ButtonState.Loading
                )
                AflamiButton(
                    onClick = { }, text = "Submit",
                    type = ButtonType.Secondary,
                    state = ButtonState.Disabled
                )
                AflamiButton(
                    onClick = { }, text = "Submit",
                    type = ButtonType.Secondary,
                    state = ButtonState.Normal,
                    icon = {
                        Icon(Icons.Default.Add, null)
                    },
                )
            }
        }
    }
}

@Composable
@PreviewLightDark
fun TextButton() {
    AflamiTheme {
        Surface(color = Theme.colors.surface)
        {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AflamiButton(
                    onClick = { }, text = "Cancel",
                    type = ButtonType.TextButton,
                )
                AflamiButton(
                    onClick = { }, text = "Cancel",
                    type = ButtonType.TextButton,
                    state = ButtonState.Disabled
                )
                AflamiButton(
                    onClick = { }, text = "Cancel",
                    type = ButtonType.TextButton,
                    state = ButtonState.Loading
                )
            }
        }
    }
}

@Composable
@PreviewLightDark
fun FABButton() {
    AflamiTheme {
        Surface(color = Theme.colors.surface)
        {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AflamiButton(
                    modifier = Modifier.size(64.dp),
                    onClick = { },
                    icon = {
                        Icon(Icons.Default.Add, null)
                    },
                    type = ButtonType.FloatingActionButton,
                )
                AflamiButton(
                    modifier = Modifier.size(64.dp),
                    onClick = { },
                    icon = { Icon(Icons.Default.Add, null) },
                    type = ButtonType.FloatingActionButton,
                    state = ButtonState.Loading
                )
                AflamiButton(
                    modifier = Modifier.size(64.dp),
                    onClick = { },
                    icon = {
                        Icon(Icons.Default.Add, null)
                    },
                    type = ButtonType.FloatingActionButton,
                    state = ButtonState.Disabled
                )
            }
        }
    }
}

@Composable
@PreviewLightDark
fun PrimaryNegativeButton() {
    AflamiTheme {
        Surface(color = Theme.colors.surface)
        {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AflamiButton(
                    onClick = { }, text = "Submit",
                    type = ButtonType.Primary,
                    isNegative = true
                )
                AflamiButton(
                    onClick = { }, text = "Submit",
                    type = ButtonType.Primary,
                    isNegative = true,
                    state = ButtonState.Loading
                )
                AflamiButton(
                    onClick = { }, text = "Submit",
                    type = ButtonType.Primary,
                    isNegative = true,
                    state = ButtonState.Disabled
                )
                AflamiButton(
                    onClick = { }, text = "Submit",
                    type = ButtonType.Primary,
                    isNegative = true,
                    state = ButtonState.Normal,
                    icon = {
                        Icon(Icons.Default.Add, null)
                    },
                )
            }
        }
    }
}

@Composable
@PreviewLightDark
fun TextNegativeButton() {
    AflamiTheme {
        Surface(color = Theme.colors.surface)
        {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AflamiButton(
                    onClick = { }, text = "Cancel",
                    type = ButtonType.TextButton,
                    isNegative = true
                )
                AflamiButton(
                    onClick = { }, text = "Cancel",
                    type = ButtonType.TextButton,
                    isNegative = true,
                    state = ButtonState.Disabled
                )
                AflamiButton(
                    onClick = { }, text = "Cancel",
                    type = ButtonType.TextButton,
                    isNegative = true,
                    state = ButtonState.Loading
                )
            }
        }
    }
}