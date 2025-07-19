package com.paris_2.aflami.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun EpisodeCard(
    modifier: Modifier = Modifier,
    episodeRating: Float,
    episodeNumber: String,
    episodeTitle: String,
    imageUri: String,
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
                imageUri = imageUri,
                modifier = Modifier.padding(bottom = 8.dp, end = 12.dp),
                clickable = true
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${stringResource(R.string.episode)} $episodeNumber",
                    style = Theme.textStyle.label.large,
                    color = Theme.colors.text.title
                )
                Text(
                    text = episodeTitle,
                    style = Theme.textStyle.label.small,
                    color = Theme.colors.text.hint,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                DescriptionSeparator(
                    texts = listOf(episodeDuration, episodeDate),
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
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
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
            episodeRating = 8f,
            episodeTitle = "Recovering a body",
            episodeDate = "3 Sep 2020",
            imageUri = "https://image.tmdb.org/t/p/w500//3BHWR7mney46vFhG4lQrsso3p1m.jpg",
        )
    }
}