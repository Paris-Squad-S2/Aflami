package com.paris_2.aflami.designsystem.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.utils.BasePreview
import com.paris_2.aflami.designsystem.utils.PreviewMultiDevices
import kotlinx.coroutines.launch
import kotlin.math.abs


@Composable
fun Slider(
    items: List<SliderMedia>,
    onClick: (media: SliderMedia) -> Unit,
    modifier: Modifier = Modifier,
    currentMedia: MutableState<SliderMedia>
) {
    val pagerState = rememberPagerState(
        pageCount = { items.size },
        initialPage = 1
    )

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            while (true) {
                kotlinx.coroutines.delay(4000)

                val nextPage = (pagerState.currentPage + 1).coerceAtMost(items.size - 1)

                pagerState.animateScrollToPage(
                    page = nextPage,
                    animationSpec = tween(durationMillis = 400)
                )
            }
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 80.dp),
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
            val isFocused = page == pagerState.currentPage
            val scaleX = 1f - (0.1f * abs(pageOffset))
            val item = items[page]
            currentMedia.value = items[pagerState.currentPage]

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(top = if (isFocused) 0.dp else 12.dp)
                    .graphicsLayer(
                        scaleX = scaleX,
                        scaleY = 1f
                    ).clickable {
                        onClick(item)
                    }
            ) {
                AflamiMediaCard(
                    modifier = Modifier.width(244.dp).height(300.dp),
                    imageUri = item.imageUri.toString(),
                    rating = item.rating,
                    mediaCardType = MediaCardType.SLIDER,
                    showRating = isFocused,
                    showPlayButton = isFocused,
                    onPlayButtonClick = { onClick(item) },
                    cardHeight = if (isFocused) 300.dp else 276.dp
                )
            }
        }
    }
}

data class SliderMedia(
    val id: Int,
    val imageUri: String,
    val title: String,
    val type: SliderMediaTypeUi,
    val categories: List<String>,
    val rating: Float,
    val yearOfRelease: String
)

enum class SliderMediaTypeUi {
    TvShow, Movie
}

@Composable
@PreviewMultiDevices
fun SliderPreview() {
    BasePreview {
//        val sampleItems = listOf(
//            MediaItem(R.drawable.anime_movie, 9.0f),
//            MediaItem(R.drawable.anime_horizontal, 8.7f),
//            MediaItem(R.drawable.attack_on_titan, 9.1f),
//            MediaItem(R.drawable.anime_movie, 9.0f),
//            MediaItem(R.drawable.anime_horizontal, 8.7f),
//            MediaItem(R.drawable.attack_on_titan, 9.1f)
//        )
//        Slider(
//            items = sampleItems,
//            modifier = Modifier.fillMaxWidth(),
//            onClick = {}
//        )
    }
}

