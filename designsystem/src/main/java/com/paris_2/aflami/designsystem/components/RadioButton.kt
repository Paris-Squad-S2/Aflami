package com.paris_2.aflami.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.theme.Theme
import com.paris_2.aflami.designsystem.utils.BasePreview
import com.paris_2.aflami.designsystem.utils.PreviewMultiDevices

@Composable
fun RadioButton(
    selected: Boolean,
    isDisable: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
) {
    if (icon == null) {
        Spacer(
            modifier = modifier
                .size(18.dp)
                .clip(CircleShape)
                .then(if (!isDisable) modifier.clickable { onClick() } else modifier)
                .border(
                    width =
                        when {
                            selected -> 6.dp
                            isDisable -> 6.dp
                            else -> 1.dp
                        },
                    color = when {
                        selected -> Theme.colors.primary
                        isDisable -> Theme.colors.disable
                        else -> Theme.colors.stroke
                    },
                    shape = CircleShape
                )
                .background(
                    color = if (selected) Color.Transparent else Theme.colors.surfaceHigh,
                    shape = CircleShape
                )
        )
    }else{
       icon()
    }
}

@PreviewMultiDevices
@Composable
fun RadioButtonPreview() {
    BasePreview {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            RadioButton(
                selected = true,
                isDisable = false,
                icon = null,
                onClick = {}
            )
            RadioButton(
                selected = false,
                isDisable = false,
                icon = null,
                onClick = {}
            )

            RadioButton(
                selected = false,
                isDisable = true,
                icon = null,
                onClick = {}
            )
        }
    }
}