package com.feature.mediaDetails.mediaDetailsUi.ui.comon.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.components.DescriptionSeparator
import com.paris_2.aflami.designsystem.components.GenresChip
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme


@Composable
fun DescriptionSection(
    title: String,
    genres: List<String>,
    releaseDate: String,
    runtime: String,
    country: String,
    description: String,
    modifier: Modifier = Modifier
){
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .background(Theme.colors.surface)
    ) {
        Text(
            text = title,
            style = Theme.textStyle.title.large,
            color = Theme.colors.text.title.copy(alpha = .87f),
            modifier = modifier
                .padding(bottom = 12.dp)
                .padding(start = 16.dp)
        )
        GenreChipRow(genres = genres)
        Spacer(Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding(start = 16.dp)
        ) {
            DescriptionSeparator(
                firstText = releaseDate,
                secondText = runtime,
                textColor = Theme.colors.text.body
            )
            DescriptionSeparator(
                firstText = "",
                secondText = country,
                textColor = Theme.colors.text.body
            )
        }
        Description(description = description)
    }

}

@Composable
private fun GenreChipRow(genres: List<String>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(start = 16.dp)
    ) {
        items(genres.size) { index ->
            GenresChip(
                title = genres[index],
                isSelected = false
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
            genres = listOf("Drama", "Fantasy", "Crime"),
            releaseDate = "10-09-1999",
            runtime = "3h 9m",
            country = "USA",
            description = "In 1935, corrections officer Paul Edgecomb oversees 'The Green Mile,' the death row section of Cold Mountain Penitentiary."
        )
    }
}