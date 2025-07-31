package com.paris_2.aflami.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun SectionTitle(
    title: String,
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    hasViewAll: Boolean = false,
    onClickViewAll: () -> Unit = {},
    shimmerModifier:Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(30.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = shimmerModifier,
            text = title,
            style = Theme.textStyle.headline.small,
            color = Theme.colors.text.title,
        )
        icon?.invoke()
        Spacer(modifier = Modifier.weight(1f))
        if (hasViewAll) {
            Text(
                text = stringResource(R.string.all),
                style = Theme.textStyle.label.medium,
                color = Theme.colors.primary,
                modifier = shimmerModifier.clickable(
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
        SectionTitle(
            title = "Home",
            hasViewAll = true,
            icon ={Icon(
               imageVector = ImageVector.vectorResource(R.drawable.ic_home),
                contentDescription = "",
                modifier = Modifier.padding(start = 8.dp),
                tint = Theme.colors.iconBackground,
            )},
        )
    }
}