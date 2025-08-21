package com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.descriptionSection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.DescriptionSeparator
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.GenresChip
import com.paris.aflami.designsystem.components.AppText
import com.paris.aflami.designsystem.theme.AflamiTheme
import com.paris.aflami.designsystem.theme.Theme


@Composable
fun DescriptionSection(
    title: String,
    genres: List<Int>,
    releaseDate: String,
    runtime: String,
    country: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .background(Theme.colors.surface)
    ) {
        AppText(
            text = title,
            style = Theme.textStyle.title.large,
            color = Theme.colors.text.title.copy(alpha = .87f),
            modifier = modifier
                .padding(bottom = 12.dp)
                .padding(start = 16.dp)
        )
        if (genres.isNotEmpty()) {
            GenreChipRow(genres = genres)
            Spacer(Modifier.height(8.dp))
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding(start = 16.dp)
        ) {
            DescriptionSeparator(
                texts = listOf(releaseDate, runtime, country),
                textColor = Theme.colors.text.body
            )
        }
        if (description.isNotBlank()) {
            Description(description = description)
        }
    }

}

@Composable
private fun GenreChipRow(genres: List<Int>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(start = 16.dp)
    ) {
        items(genres.size) { index ->
            GenresChip(
                title = stringResource( genres[index]),
                isSelected = false,
                modifier = Modifier
            )
        }
    }
}

@PreviewLightDark
@Composable
fun DescriptionSectionPreview() {
    AflamiTheme {
        DescriptionSection(
            title = "The Green Mile",
            genres = emptyList(),
            releaseDate = "10-09-1999",
            runtime = "3h 9m",
            country = "USA",
            description = "In 1935, corrections officer Paul Edgecomb oversees 'The Green Mile,' the death row section of Cold Mountain Penitentiary."
        )
    }
}