package com.feature.home.homeUi.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paris.aflami.designsystem.components.AppText
import com.paris.aflami.designsystem.theme.Theme
import com.paris.aflami.designsystem.utils.BasePreview
import com.paris.aflami.designsystem.utils.PreviewMultiDevices

@Composable
fun GenresChip(
    modifier: Modifier,
    isSelected:Boolean,
    title:String
) {
    Box(modifier = modifier
        .background(
            color = if (isSelected) Theme.colors.primary else Theme.colors.surfaceHigh,
            shape = RoundedCornerShape(8.dp)
        ),
        contentAlignment = Alignment.Center
    ) {
        AppText(
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
            GenresChip(
                isSelected = false,
                title = "Drama",
                modifier = Modifier
            )
            GenresChip(
                isSelected = true,
                title = "Drama",
                modifier = Modifier
            )
        }
    }
}