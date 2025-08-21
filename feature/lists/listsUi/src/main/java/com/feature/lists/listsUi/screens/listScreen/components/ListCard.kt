package com.feature.lists.listsUi.screens.listScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.paris.aflami.designsystem.R
import com.paris.aflami.designsystem.components.AppIcon
import com.paris.aflami.designsystem.components.AppText
import com.paris.aflami.designsystem.theme.Theme
import com.paris.aflami.designsystem.utils.BasePreview
import com.paris.aflami.designsystem.utils.PreviewMultiDevices

@Composable
fun ListCard(
    title: String,
    count: Int,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(width = 160.dp, height = 147.dp)

    ) {
        AppIcon(
            modifier = Modifier.clickable {
                onCardClick()
            },
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_list_card),
            contentDescription = stringResource(R.string.list_card),
            tint = Theme.colors.surfaceHigh
        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp, bottom = 21.dp)
                .align(Alignment.BottomStart)
        ) {
            AppText(
                text = title,
                color = Theme.colors.text.title,
                style = Theme.textStyle.title.medium
            )
            AppText(
                text = stringResource(R.string.item, count),
                color = Theme.colors.text.hint,
                style = Theme.textStyle.label.large
            )
        }
    }
}


@PreviewMultiDevices
@Composable
fun ListCardPreview() {
    BasePreview {
        ListCard(title = "My favourite", count = 12, {})
    }
}