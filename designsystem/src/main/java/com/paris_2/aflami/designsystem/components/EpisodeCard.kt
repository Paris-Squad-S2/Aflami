package com.paris_2.aflami.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun EpisodeCard(
    modifier: Modifier = Modifier,
    episodeRating: String,
    episodeNumber: String,
    episodeTitle: String,
    episodeDuration: String,
    episodeDate: String,
    episodeDescription: String,
) {
    Column(
        modifier = modifier
            .background(Theme.colors.surface)
            .width(360.dp)
            .padding(horizontal = 16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            AflamiMediaCard(
                rating = episodeRating,
                mediaCardType = MediaCardType.EPISODE,
                imageId = R.drawable.attack_on_titan,
                modifier = Modifier.padding(bottom = 8.dp, end = 12.dp),
                clickable = true
            )
            Column {
                Text(
                    text = "${stringResource(R.string.episode)} $episodeNumber",
                    style = Theme.textStyle.label.large,
                    color = Theme.colors.text.title
                )
                Text(
                    text = episodeTitle,
                    style = Theme.textStyle.label.small,
                    color = Theme.colors.text.hint
                )
                DescriptionSeparator(
                    firstText = episodeDuration,
                    secondText = episodeDate,
                    textColor = Theme.colors.text.hint,
                    separatorColor = Theme.colors.stroke
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            MediaPlayButton(buttonType = MediaButtonType.MEDIUM, showBoarder = true)

        }
        Text(
            text = episodeDescription,
            style = Theme.textStyle.label.small,
            color = Theme.colors.text.hint,
            modifier = Modifier.fillMaxWidth()
        )

    }

}

@PreviewLightDark
@Composable
fun PreviewEpisodeCard() {
    AflamiTheme {
        EpisodeCard(
            episodeDescription = "In 1935, corrections officer Paul Edgecomb oversees The Green Mile, the death row...",
            episodeDuration = "58 m",
            episodeNumber = "1",
            episodeRating = "8",
            episodeTitle = "Recovering a body",
            episodeDate = "3 Sep 2020"
        )
    }
}