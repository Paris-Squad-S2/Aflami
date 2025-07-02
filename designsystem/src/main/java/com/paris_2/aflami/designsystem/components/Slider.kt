package com.paris_2.aflami.designsystem.components

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.utils.BasePreview
import com.paris_2.aflami.designsystem.utils.PreviewMultiDevices
import kotlinx.coroutines.launch
import kotlin.math.abs
import com.paris_2.aflami.designsystem.R


@Composable
fun Slider(
    items: List<MediaItem>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(
        pageCount = { items.size },
        initialPage = 1
    )
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    coroutineScope.launch {
                        if (dragAmount > 0) {
                            pagerState.animateScrollToPage(
                                (pagerState.currentPage - 1).coerceAtLeast(0)
                            )
                        } else {
                            pagerState.animateScrollToPage(
                                (pagerState.currentPage + 1).coerceAtMost(items.size - 1)
                            )
                        }
                    }
                }
            },
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
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(top = if (isFocused) 0.dp else 24.dp)
                    .graphicsLayer(
                        scaleX = scaleX,
                        scaleY = 1f
                    )
            ) {
                AflamiMediaCard(
                    imageId = item.imageResId,
                    rating = item.rating,
                    mediaCardType = MediaCardType.SLIDER,
                    showRating = isFocused,
                    showPlayButton = isFocused,
                    onClick = { onClick },
                    cardHeight = if (isFocused) 300 else 276
                )
            }
        }
    }
}

data class MediaItem(
    val imageResId: Int,
    val rating: String
)

@Composable
@PreviewMultiDevices
fun SliderPreview() {
    BasePreview {
        val sampleItems = listOf(
            MediaItem(R.drawable.anime_movie, "9.0"),
            MediaItem(R.drawable.anime_horizontal, "8.7"),
            MediaItem(R.drawable.attack_on_titan, "9.1"),
            MediaItem(R.drawable.anime_movie, "9.0"),
            MediaItem(R.drawable.anime_horizontal, "8.7"),
            MediaItem(R.drawable.attack_on_titan, "9.1")
        )
        Slider(
            items = sampleItems,
            modifier = Modifier.fillMaxWidth(),
            onClick = {}
        )
    }
}

