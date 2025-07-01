package com.paris_2.aflami.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.theme.Theme
import com.paris_2.aflami.designsystem.utils.BasePreview
import com.paris_2.aflami.designsystem.utils.PreviewMultiDevices

@Composable
fun GenersChip(
    isSelected:Boolean,
    title:String
) {
    Box(modifier = Modifier
            .background(
                color = if (isSelected) Theme.colors.primary else Theme.colors.surfaceHigh,
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
            text = title,
            style = Theme.textStyle.label.small,
            color = if (isSelected) Theme.colors.onPrimaryColors.onPrimary else Theme.colors.primary
        )
    }
}

@PreviewMultiDevices
@Composable
fun GenersChipPreview(){
    BasePreview {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            GenersChip(
                isSelected = false,
                title = "Drama"
            )
            GenersChip(
                isSelected = true,
                title = "Drama"
            )
        }
    }
}