package com.feature.mediaDetails.mediaDetailsUi.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.components.AflamiMediaCard
import com.paris_2.aflami.designsystem.components.MediaButtonType
import com.paris_2.aflami.designsystem.components.MediaCardType
import com.paris_2.aflami.designsystem.components.MediaPlayButton
import com.paris_2.aflami.designsystem.components.RatingCard
import com.paris_2.aflami.designsystem.theme.AflamiTheme


@Composable
fun DetailsImage(
    modifier: Modifier = Modifier,
    imageUris: List<String>,
    rating: String,
    onPlayClick: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { imageUris.size })
    Box(
        modifier = modifier
    ) {
        VerticalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            AflamiMediaCard(
                modifier = Modifier.fillMaxSize(),
                imageUri = imageUris[page],
                mediaCardType = MediaCardType.NORMAL,
                showRating = false,
                showGradientFilter = false,
                showPlayButton = false,
                clickable = false
            )
        }
        RatingCard(
            rating = rating,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 4.dp)
                .padding(bottom = 4.dp)
        )
        MediaPlayButton(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = (-32).dp),
            buttonType = MediaButtonType.BIG,
            onButtonClick = onPlayClick
        )
        ImagePageIndicator(
            pageSize = if (imageUris.size == 1) 3 else imageUris.size,
            currentPage = pagerState.currentPage,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 4.dp)
                .padding(bottom = 4.dp)
        )
    }
}

@PreviewLightDark
@Composable
fun DetailsImagePreview() {
    AflamiTheme {
        DetailsImage(
            modifier = Modifier.height(236.dp),
            imageUris = listOf(
                "https://xl.movieposterdb.com/12_03/1999/120689/xl_120689_c927b987.jpg",
                "https://xl.movieposterdb.com/12_03/1999/120689/xl_120689_c927b987.jpg",
                "https://xl.movieposterdb.com/12_03/1999/120689/xl_120689_c927b987.jpg"
            ),
            rating = "9.9",
            onPlayClick = {}
        )
    }
}
