package com.feature.guessGame.guessGameUi.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.components.AppIcon
import com.paris_2.aflami.designsystem.components.AppText
import com.paris_2.aflami.designsystem.theme.Theme
import com.paris_2.aflami.designsystem.utils.BasePreview

@Composable
fun OptionItem(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    isCorrect: Boolean,
    onClick: (Int) -> Unit,
) {
    val backgroundColor = when {
        selected && isCorrect -> Theme.colors.status.greenVariant
        selected && !isCorrect -> Theme.colors.status.redVariant
        else -> Theme.colors.surface
    }

    val borderColor = when {
        selected && isCorrect -> Theme.colors.status.greenAccent
        selected && !isCorrect -> Theme.colors.status.redAccent
        else -> Theme.colors.surfaceHigh
    }

    val icon = when {
        selected && isCorrect -> R.drawable.ic_radio_true
        selected && !isCorrect -> R.drawable.ic_radio_false
        else -> R.drawable.ic_radio_check
    }
    val iconTint = when {
        selected && isCorrect -> Theme.colors.status.greenAccent
        selected && !isCorrect -> Theme.colors.status.redAccent
        else -> Theme.colors.surfaceHigh
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(16.dp))
            .background(color = backgroundColor, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 12.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AppText(
            text = text,
            color = Theme.colors.text.body,
            style = Theme.textStyle.label.large,
        )
        AppIcon(
            imageVector = ImageVector.vectorResource(icon),
            contentDescription = null,
            tint = iconTint
        )

    }
}

@Preview
@Composable
private fun OptionItemCorrectPrev() {
    OptionItem(text = "Correct", selected = true, isCorrect = true, onClick = {})
}

@Preview
@Composable
private fun OptionItemFalsePrev() {
    OptionItem(text = "Wrong", selected = true, isCorrect = false, onClick = {})
}

@Preview
@Composable
private fun OptionItemNotSelectedPrev() {
    BasePreview {
        OptionItem(text = "NotSelected", selected = false, isCorrect = false, onClick = {})
    }
}