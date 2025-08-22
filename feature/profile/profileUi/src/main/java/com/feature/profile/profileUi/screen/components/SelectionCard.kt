package com.feature.profile.profileUi.screen.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris.aflami.designsystem.components.AppIcon
import com.paris.aflami.designsystem.components.AppRadioButton
import com.paris.aflami.designsystem.components.AppText
import com.paris.aflami.designsystem.theme.Theme
import com.paris.aflami.designsystem.utils.PreviewMultiDevices

@Composable
fun SelectionCard(
    optionTitle: String,
    modifier: Modifier = Modifier,
    optionDescription: String? = null,
    isSelected: Boolean,
    isCorrect: Boolean? = null,
    icon: Int? = null,
    hasRadioButton: Boolean? = null,
    onClick: () -> Unit,
) {
    val backgroundColor = when {
        isCorrect == true && isSelected -> Theme.colors.status.greenVariant
        isCorrect == false && isSelected -> Theme.colors.status.redVariant
        isCorrect == null && isSelected -> Theme.colors.primaryVariant
        isCorrect == null && !isSelected -> Theme.colors.surface
        else -> Theme.colors.surface
    }

    val borderColor = when {
        isCorrect == true && isSelected -> Theme.colors.status.greenAccent
        isCorrect == false && isSelected -> Theme.colors.status.redAccent
        isCorrect == null && isSelected -> Color.Transparent
        isCorrect == null && !isSelected -> Theme.colors.stroke
        else -> Color.Gray
    }

    val iconTint =
        animateColorAsState(if (isSelected) Theme.colors.primary else Theme.colors.text.body).value

    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            )
            .padding(horizontal = 12.dp, vertical = if (optionDescription != null) 8.dp else 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        icon?.let {
            AppIcon(
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 8.dp)
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            AppText(
                text = optionTitle,
                color = Theme.colors.text.body,
                style = Theme.textStyle.label.large,
            )
            if (optionDescription != null) {
                AppText(
                    text = optionDescription,
                    color = Theme.colors.text.hint,
                    style = Theme.textStyle.label.small,
                )
            }
        }

        hasRadioButton?.let {
            Spacer(Modifier.weight(1f))
            AppRadioButton(
                selected = isSelected,
                isDisable = false,
                onClick = onClick,
            )
        }


    }
}

@Preview
@PreviewMultiDevices
@Composable
private fun SelectionCardPreview() {
    SelectionCard(
        optionTitle = "Option 1",
        optionDescription = "This is a description for option 1.",
        isSelected = true,
        isCorrect = true,
        icon = com.paris.aflami.designsystem.R.drawable.ic_all,
        onClick = {}
    )
}