package com.paris_2.aflami.designsystem.components

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.theme.Theme
import com.paris_2.aflami.designsystem.utils.BasePreview
import com.paris_2.aflami.designsystem.utils.PreviewMultiDevices

@Composable
fun Chips(
    title: String,
    icon: Painter,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.width(70.dp)
    ) {
        Box(
            modifier = Modifier.clickable { onClick() }
                .clip(RoundedCornerShape(16.dp))
                .background(
                    if (isSelected) Theme.colors.secondary else Theme.colors.surfaceHigh
                )
                .border(
                    width = 1.dp,
                    color = if (isSelected) Theme.colors.stroke else Color.Transparent,
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.padding(16.dp),
                tint = if (isSelected) Theme.colors.onPrimaryColors.onPrimary else Theme.colors.text.hint
            )
        }

        Text(
            text = title,
            style = Theme.textStyle.label.small,
            color = if (isSelected) Theme.colors.text.body else Theme.colors.text.hint,
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
                icon = painterResource(R.drawable.ic_all),
                isSelected = true,
                onClick = {}
            )
            Chips(
                title = "Romance",
                icon = painterResource(R.drawable.ic_romance),
                isSelected = false,
                onClick = {}
            )
        }
    }
}
