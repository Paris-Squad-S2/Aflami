package com.feature.search.searchUi.comon.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.paris.aflami.designsystem.R
import com.paris.aflami.designsystem.components.AppIcon
import com.paris.aflami.designsystem.components.AppText
import com.paris.aflami.designsystem.theme.AflamiTheme
import com.paris.aflami.designsystem.theme.Theme

@Composable
fun RecentSearchItem(
    recentSearchTitle: String,
    onRecentSearchClick: () -> Unit,
    onDeleteRecentSearch: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.colors.surface)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onRecentSearchClick() }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AppIcon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_clock),
            contentDescription = null,
            modifier = Modifier.padding(end = 8.dp),
            tint = Theme.colors.text.hint
        )
        AppText(
            text = recentSearchTitle,
            style = Theme.textStyle.body.medium,
            color = Theme.colors.text.title,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        AppIcon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_cancel_thin),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 8.dp)
                .clickable { onDeleteRecentSearch() },
            tint = Theme.colors.text.hint
        )
    }
}

@PreviewLightDark
@Composable
fun PreviewRecentSearchItem() {
    AflamiTheme {
        RecentSearchItem(
            recentSearchTitle = "text tecent",
            onRecentSearchClick = {},
            onDeleteRecentSearch = {},
            modifier = Modifier
        )
    }

}
