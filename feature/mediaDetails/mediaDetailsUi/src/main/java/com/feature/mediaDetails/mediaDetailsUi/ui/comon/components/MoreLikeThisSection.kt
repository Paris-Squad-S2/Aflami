package com.feature.mediaDetails.mediaDetailsUi.ui.comon.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.SimilarMediaUI
import com.paris_2.aflami.designsystem.components.AflamiMediaCard
import com.paris_2.aflami.designsystem.components.MediaCardType
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun MoreLikeThisSection(
    mediaList: LazyPagingItems<SimilarMediaUI>,
    onClick: () -> Unit,
    mediaType: String,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(mediaList.itemCount) { mediaIndex ->
            mediaList[mediaIndex]?.let {media ->
                AflamiMediaCard(
                    modifier = modifier.fillMaxWidth(),
                    imageUri = media.posterPath,
                    rating = media.voteAverage.toFloat(),
                    movieName = media.title,
                    mediaType = mediaType,
                    year = media.releaseDate.takeLast(4),
                    mediaCardType = MediaCardType.UP_COMING,
                    showGradientFilter = true,
                    clickable = true,
                    onClick = onClick,
                    cardWidth = null
                )
            }
        }
    }
}


@PreviewLightDark
@Composable
fun PreviewMoreLikeThisSection() {
    AflamiTheme {
        MoreLikeThisSection(
            mediaList = flowOf(PagingData.from(
                listOf(
                    SimilarMediaUI(
                        id =123,
                        posterPath = "https://xl.movieposterdb.com/12_03/1999/120689/xl_120689_c927b987.jpg",
                        voteAverage = 8.5,
                        title = "The Green Mile",
                        releaseDate = "10-09-1999",

                        ),
                    SimilarMediaUI(
                        id = 258,
                        posterPath = "https://xl.movieposterdb.com/07_10/2001/266543/xl_266543_fcf33950.jpg",
                        voteAverage = 7.9,
                        title = "A Beautiful Mind",
                        releaseDate = "10-09-1999",
                    )
                )
            )).collectAsLazyPagingItems(),
            mediaType = "Movie",
            onClick = {}
        )
    }
}

