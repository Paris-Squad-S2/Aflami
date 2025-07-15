package com.feature.mediaDetails.mediaDetailsUi.common.components.detailsImage

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.components.MediaButtonType
import com.paris_2.aflami.designsystem.components.MediaPlayButton
import com.paris_2.aflami.designsystem.components.RatingCard
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme


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
            .fillMaxWidth()
            .height(295.dp)
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(263.dp)
        ) {
            val color: Color = Theme.colors.surfaceHigh
            VerticalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                val painter = rememberAsyncImagePainter(model = imageUris[page])
                val painterState = painter.state

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                ) {
                    when (painterState) {
                        is AsyncImagePainter.State.Loading -> {
                            Image(
                                painter = painterResource(id = R.drawable.ic_film_roll),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(48.dp)
                                    .align(Alignment.Center)
                            )
                        }
                        is AsyncImagePainter.State.Error -> {
                            Image(
                                painter = painterResource(id = R.drawable.ic_film_roll),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(48.dp)
                                    .align(Alignment.Center)
                            )
                        }
                        is AsyncImagePainter.State.Success -> {
                            Image(
                                painter = painter,
                                contentDescription = "poster",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .align(Alignment.Center),
                                contentScale = ContentScale.Fit
                            )
                        }
                        else -> {}
                    }
                }
            }
            RatingCard(
                rating = rating,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 4.dp)
                    .padding(bottom = 4.dp)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = 32.dp)
                    .size(72.dp)
                    .drawBehind {
                        drawCircle(
                            color = color,
                            radius = size.minDimension / 2f,
                            center = center
                        )
                    }
                    .shadow(
                        elevation = 12.dp,
                        spotColor = Color(0xFFD85895).copy(alpha = 0.12f),
                        ambientColor = Color(0xFFD85895).copy(alpha = 0.12f),
                        shape = CircleShape
                    )
                    .border(
                        width = 2.dp,
                        color = Theme.colors.stroke.copy(alpha = 0.08f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                MediaPlayButton(
                    buttonType = MediaButtonType.BIG,
                    onButtonClick = onPlayClick,
                    showBoarder = false,
                    backGroundColor = Theme.colors.surfaceHigh
                )
            }
            if (imageUris.isNotEmpty()) {
                ImagePageIndicator(
                    pageSize = if (imageUris.size == 1) 3 else imageUris.size,
                    currentPage = if (imageUris.size == 1) 2 else pagerState.currentPage,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 4.dp)
                        .padding(bottom = 4.dp)
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun DetailsImagePreview() {
    AflamiTheme {
        DetailsImage(
            imageUris = listOf(
                "https://xl.movieposterdb.com/12_03/1999/120689/xl_120689_c927b987.jpg",
            ),
            rating = "9.9",
            onPlayClick = {}
        )
    }
}


