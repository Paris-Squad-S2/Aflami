package com.paris_2.aflami.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.theme.Theme
import com.paris_2.aflami.designsystem.utils.BasePreview
import com.paris_2.aflami.designsystem.utils.PreviewMultiDevices

@Composable
fun ListCard(
    title: String,
    count: Int,
    onCardClick: () -> Unit
){
    Box(
        modifier = Modifier
            .clickable {
                onCardClick()
            }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_list_card),
            contentDescription = stringResource(R.string.list_card),
            tint = Theme.colors.surfaceHigh
        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp, bottom = 21.dp)
                .align(Alignment.BottomStart)
        ) {
            Text(
                text = title,
                color = Theme.colors.text.title,
                style = Theme.textStyle.title.medium
            )
            Text(
                text = stringResource(R.string.item, count),
                color = Theme.colors.text.hint,
                style = Theme.textStyle.label.large
            )
        }
    }
}


@PreviewMultiDevices
@Composable
fun ListCardPreview(){
    BasePreview {
        ListCard(title = "My favourite", count = 12, {})
    }
}