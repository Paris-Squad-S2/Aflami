package com.feature.mediaDetails.mediaDetailsUi.common.components

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.feature.mediaDetails.mediaDetailsUi.R
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun SeasonHeader(
    seasonNumber: Int,
    numberOfEpisodes: Int,
//    episodes: List<Episode>
) {
    var isSeasonExpanded by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Theme.colors.primaryVariant)
            .fillMaxWidth()
            .padding(start =16.dp)
    ) {
        Text(
            text = "Season $seasonNumber",
            style = Theme.textStyle.title.small,
            color = Theme.colors.text.title
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "$numberOfEpisodes episodes",
            style = Theme.textStyle.label.small,
            color = Theme.colors.text.hint,
        )

        IconButton(
            onClick = { isSeasonExpanded = !isSeasonExpanded }
        ) {
            Image(
                painter = painterResource(
                    id = if (isSeasonExpanded) {
                        R.drawable.ic_not_expanded
                    } else {
                        R.drawable.ic_expanded
                    }
                ),
                contentDescription = "Expand/Collapse"
            )
        }
    }
}


@PreviewLightDark
@Composable
fun PreviewSeasonHeader() {
    SeasonHeader(
        seasonNumber = 1,
        numberOfEpisodes = 3,
    )
}