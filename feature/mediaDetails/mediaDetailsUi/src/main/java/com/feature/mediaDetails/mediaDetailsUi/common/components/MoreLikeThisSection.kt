package com.feature.mediaDetails.mediaDetailsUi.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.feature.mediaDetails.mediaDetailsUi.screen.MediaUi
import com.feature.mediaDetails.mediaDetailsUi.screen.movie.MovieUi
import com.paris_2.aflami.designsystem.components.AflamiMediaCard
import com.paris_2.aflami.designsystem.components.MediaCardType
import com.paris_2.aflami.designsystem.theme.AflamiTheme

@Composable
fun MoreLikeThisSection(
    mediaList: List<MediaUi>,
    onClick: () -> Unit,
    mediaType: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        mediaList.forEach { media ->
            AflamiMediaCard(
                imageUri = media.posterUrl,
                rating = media.rating.toFloat(),
                movieName = media.title,
                mediaType = mediaType,
                year = media.releaseDate.takeLast(4),
                mediaCardType = MediaCardType.UP_COMING,
                showGradientFilter = true,
                clickable = true,
                onClick = onClick
            )
        }
    }
}


@PreviewLightDark
@Composable
fun PreviewMoreLikeThisSection() {
    AflamiTheme {
        MoreLikeThisSection(
            mediaList = listOf(
                MovieUi(
                    posterUrl = "https://xl.movieposterdb.com/12_03/1999/120689/xl_120689_c927b987.jpg",
                    rating = "8.5",
                    title = "The Green Mile",
                    genres = listOf("Drama", "Fantasy"),
                    releaseDate = "10-09-1999",
                    runtime = "3h 9m",
                    country = "USA",
                    description = "",
                    productionCompanies = emptyList()
                ),
                MovieUi(
                    posterUrl = "https://xl.movieposterdb.com/07_10/2001/266543/xl_266543_fcf33950.jpg",
                    rating = "7.9",
                    title = "A Beautiful Mind",
                    genres = listOf("Biography", "Drama"),
                    releaseDate = "10-09-1999",
                    runtime = "2h 15m",
                    country = "USA",
                    description = "",
                    productionCompanies = emptyList()
                )
            ),
            mediaType = "Movie",
            onClick = {}
        )
    }
}

