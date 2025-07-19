package com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.detailsImage

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.designSystem.safeimageviewer.SafeImageViewer
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.components.MediaButtonType
import com.paris_2.aflami.designsystem.components.MediaPlayButton
import com.paris_2.aflami.designsystem.components.RatingCard
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme
import dropShadow
import kotlinx.coroutines.delay


@Composable
fun DetailsImage(
    modifier: Modifier = Modifier,
    imageUris: List<String>,
    rating: Float,
    onPlayClick: () -> Unit,
) {
    val displayImages = imageUris.take(10)
    val pagerState = rememberPagerState(pageCount = { displayImages.size })
    LaunchedEffect(displayImages) {
        if (displayImages.size > 1) {
            while (true) {
                delay(4000)
                val nextPage = (pagerState.currentPage + 1) % displayImages.size
                pagerState.animateScrollToPage(
                    nextPage,
                    animationSpec = tween(durationMillis = 200)
                )
            }
        }
    }
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
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                ) {
                    SafeImageViewer(
                        model = displayImages[page],
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = "poster",
                        placeholder = {
                            Image(
                                painter = painterResource(id = R.drawable.ic_film_roll),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(48.dp)
                                    .align(Alignment.Center)
                            )
                        },
                        errorPlaceholder = {
                            Image(
                                painter = painterResource(id = R.drawable.img_disconnect),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(48.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    )
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
                    .dropShadow(
                        shape = CircleShape,
                        color = Color(0x1FD85895),
                        spread = 12.dp,
                        offsetX = (-5).dp,
                        alpha = 0.08F
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
            if (displayImages.size > 1) {
                ImagePageIndicator(
                    pageSize = displayImages.size,
                    currentPage = pagerState.currentPage,
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
                "https://xl.movieposterdb.com/12_03/1999/120689/xl_120689_c927b987.jpg",
                "https://xl.movieposterdb.com/12_03/1999/120689/xl_120689_c927b987.jpg",
                "https://xl.movieposterdb.com/12_03/1999/120689/xl_120689_c927b987.jpg",
                "https://xl.movieposterdb.com/12_03/1999/120689/xl_120689_c927b987.jpg",
                "https://xl.movieposterdb.com/12_03/1999/120689/xl_120689_c927b987.jpg",
                "https://xl.movieposterdb.com/12_03/1999/120689/xl_120689_c927b987.jpg",
                "https://xl.movieposterdb.com/12_03/1999/120689/xl_120689_c927b987.jpg",
                "https://xl.movieposterdb.com/12_03/1999/120689/xl_120689_c927b987.jpg",
                "https://xl.movieposterdb.com/12_03/1999/120689/xl_120689_c927b987.jpg",
                "https://xl.movieposterdb.com/12_03/1999/120689/xl_120689_c927b987.jpg",
                "https://xl.movieposterdb.com/12_03/1999/120689/xl_120689_c927b987.jpg",
            ),
            rating = 9.9f,
            onPlayClick = {}
        )
    }
}


