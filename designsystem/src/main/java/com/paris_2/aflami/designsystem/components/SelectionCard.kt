package com.paris_2.aflami.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.theme.Theme
import com.paris_2.aflami.designsystem.utils.BasePreview
import com.paris_2.aflami.designsystem.utils.PreviewMultiDevices

@Composable
fun SelectionCard(
    optionTitle: Int,
    modifier: Modifier = Modifier,
    optionDescription: Int? = null,
    isSelected: Boolean,
    isCorrect: Boolean? = null,
    icon: Int? = null,
    onClick: () -> Unit
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

    val radioIcon = when {
        isSelected && optionDescription != null -> R.drawable.ic_radio_true
        !isSelected && optionDescription != null -> R.drawable.ic_radio_add
        isCorrect == true && isSelected -> R.drawable.ic_radio_true
        isCorrect == false && isSelected -> R.drawable.ic_radio_false
        isCorrect == null && isSelected -> null
        else -> null
    }

    val radioIconTint = when {
        isSelected && optionDescription!=null-> Theme.colors.primary
        !isSelected && optionDescription!=null-> Theme.colors.text.hint
        else -> Color.Unspecified
    }

    val iconTint = if (isSelected) Theme.colors.primary else Theme.colors.text.body

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
        if (icon !=null){
            Icon(
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = stringResource( optionTitle),
                tint = iconTint,
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 8.dp)
            )
        }
        Column (modifier = Modifier.weight(1f)){
            Text(
                text = stringResource(optionTitle),
                color = Theme.colors.text.body,
                style = Theme.textStyle.label.large,
            )
            if (optionDescription != null){
                Text(
                    text = stringResource(optionDescription),
                    color = Theme.colors.text.hint,
                    style = Theme.textStyle.label.small,
                )
            }
        }

        AflamiRadioButton(
            selected = isSelected,
            isDisable = false,
            icon = radioIcon,
            iconTint = radioIconTint,
            onClick = onClick
        )
    }
}

@PreviewMultiDevices
@Composable
fun ThemeOptionSelectorPreview(){
    BasePreview {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SelectionCard(
                optionTitle = R.string.light,
                isSelected = true,
                icon = R.drawable.ic_light_theme,
                onClick = { },
            )
            SelectionCard(
                optionTitle = R.string.dark,
                isSelected = false,
                icon = R.drawable.ic_dark_theme,
                onClick = { }
            )
        }
    }
}

@PreviewMultiDevices
@Composable
fun LanguageSelectorPreview(){
    BasePreview {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SelectionCard(
                optionTitle = R.string.english,
                isSelected = false,
                icon = R.drawable.ic_english_language,
                onClick = { },
            )
            SelectionCard(
                optionTitle = R.string.arabic,
                isSelected = true,
                icon = R.drawable.ic_arabic_language,
                onClick = { }
            )
        }
    }
}

@PreviewMultiDevices
@Composable
fun SelectAnswerPreview(){
    BasePreview {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SelectionCard(
                optionTitle = R.string.arabic,
                isSelected = false,
                isCorrect = null,
                onClick = { },
            )
            SelectionCard(
                optionTitle = R.string.arabic,
                isSelected = true,
                isCorrect = true,
                onClick = { },
            )
            SelectionCard(
                optionTitle = R.string.arabic,
                isSelected = true,
                isCorrect = false,
                onClick = { }
            )
        }
    }
}

@PreviewMultiDevices
@Composable
fun AddToListPreview(){
    BasePreview {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SelectionCard(
                optionTitle = R.string.arabic,
                optionDescription = R.string._11_item,
                isSelected = false,
                onClick = { },
            )
            SelectionCard(
                optionTitle = R.string.arabic,
                optionDescription = R.string._11_item,
                isSelected = true,
                onClick = { }
            )
        }
    }
}