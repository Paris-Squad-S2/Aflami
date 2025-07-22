package com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.seasonSection

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.feature.mediaDetails.mediaDetailsUi.R
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun SeasonHeader(
    modifier: Modifier = Modifier,
    seasonNumber: Int,
    numberOfEpisodes: Int,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.colors.surface)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = stringResource(R.string.season)+" "+seasonNumber,
            style = Theme.textStyle.title.small,
            color = Theme.colors.text.title
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "$numberOfEpisodes "+ stringResource(R.string.episodes),
            style = Theme.textStyle.label.small,
            color = Theme.colors.text.hint
        )

        IconButton(onClick = onToggleExpand) {
            Image(
                painter = painterResource(
                    id = if (isExpanded) R.drawable.ic_not_expanded else R.drawable.ic_expanded
                ),
                contentDescription = "Expand/Collapse",
                alignment = Alignment.Center,
                colorFilter = ColorFilter.tint(Theme.colors.text.title)
            )
        }
    }
}


@Composable
fun PreviewableSeasonHeader(initiallyExpanded: Boolean) {
    var expanded by remember { mutableStateOf(initiallyExpanded) }

    SeasonHeader(
        seasonNumber = 1,
        numberOfEpisodes = 2,
        isExpanded = expanded,
        onToggleExpand = { expanded = !expanded },
    )
}

@Preview(name = "Collapsed", showBackground = true)
@Composable
fun PreviewSeasonHeaderCollapsed() {
    PreviewableSeasonHeader(initiallyExpanded = false)
}

@Preview(name = "Expanded", showBackground = true)
@Composable
fun PreviewSeasonHeaderExpanded() {
    PreviewableSeasonHeader(initiallyExpanded = true)
}
