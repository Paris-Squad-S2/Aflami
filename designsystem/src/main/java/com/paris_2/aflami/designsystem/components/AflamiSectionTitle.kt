package com.paris_2.aflami.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun AflamiSectionTitle(
    title: String,
    modifier: Modifier = Modifier,
    painter: Painter? = null,
    contentDescription: String? = null,
    iconColor: Color = Theme.colors.iconBackground,
    hasViewAll: Boolean = false,
    onClickViewAll: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(30.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = Theme.textStyle.headline.small,
            color = Theme.colors.text.title,
        )
        if (painter != null) {
            Icon(
                painter = painter,
                contentDescription = contentDescription,
                modifier = Modifier.padding(start = 8.dp),
                tint = iconColor
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        if (hasViewAll) {
            Text(
                text = stringResource(R.string.all),
                style = Theme.textStyle.label.medium,
                color = Theme.colors.primary,
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = null,
                    onClick = onClickViewAll
                )
            )
        }
    }

}

@Preview(showBackground = false, showSystemUi = true)
@Composable
fun AflamiSectionTitlePreview() {
    AflamiTheme {
        AflamiSectionTitle(
            title = "Home",
            hasViewAll = true,
            painter = painterResource(R.drawable.ic_home),
        )
    }
}