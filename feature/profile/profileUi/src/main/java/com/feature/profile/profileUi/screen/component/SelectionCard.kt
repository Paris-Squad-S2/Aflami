package com.feature.profile.profileUi.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.components.AppIcon
import com.paris_2.aflami.designsystem.components.AppText
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun SelectionCard(
    optionTitle: String,
    modifier: Modifier = Modifier,
    optionDescription: String? = null,
    isSelected: Boolean,
    isCorrect: Boolean? = null,
    icon: Int,
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



    val iconTint = when {
        isSelected && optionDescription != null -> Theme.colors.primary
        !isSelected && optionDescription != null -> Theme.colors.text.hint
        else -> Color.Unspecified
    }

    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = if (optionDescription != null) 8.dp else 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AppIcon(
            imageVector = ImageVector.vectorResource(icon),
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier
                .size(24.dp)
                .padding(end = 8.dp)
        )
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

    }
}