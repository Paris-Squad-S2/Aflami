package com.feature.mediaDetails.mediaDetailsUi.ui.comon.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.paris.aflami.designsystem.R
import com.paris.aflami.designsystem.components.AppIcon
import com.paris.aflami.designsystem.components.AppText
import com.paris.aflami.designsystem.theme.Theme
import com.paris.aflami.designsystem.utils.BasePreview
import com.paris.aflami.designsystem.utils.PreviewMultiDevices

@Composable
fun Chips(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.width(70.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .clickable { onClick() }
                .background(
                    animateColorAsState(
                        targetValue = if (isSelected) Theme.colors.secondary else Theme.colors.surfaceHigh
                    ).value
                )
                .border(
                    width = 1.dp,
                    color = animateColorAsState(
                        targetValue = if (isSelected) Theme.colors.stroke else Color.Transparent
                    ).value,
                    shape = RoundedCornerShape(16.dp)
                )
                .clip(RoundedCornerShape(16.dp))
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            AppIcon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.padding(16.dp),
                tint = animateColorAsState(
                    targetValue = if (isSelected) Theme.colors.onPrimaryColors.onPrimary else Theme.colors.text.hint
                ).value
            )
        }

        AppText(
            text = title,
            style = Theme.textStyle.label.small,
            color = animateColorAsState(
                targetValue = if (isSelected) Theme.colors.text.body else Theme.colors.text.hint
            ).value,
            minLines = 2,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@PreviewMultiDevices
@Composable
fun ChipsPreview(){
    BasePreview {
        Column {
            Chips(
                title = "All",
                icon = ImageVector.vectorResource(R.drawable.ic_all),
                isSelected = true,
                onClick = {},
                modifier = Modifier
            )
            Chips(
                title = "Romance",
                icon = ImageVector.vectorResource(R.drawable.ic_romance),
                isSelected = false,
                onClick = {},
                modifier = Modifier
            )
        }
    }
}