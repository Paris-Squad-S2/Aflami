package com.paris_2.aflami.designsystem.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.fontscaling.MathUtils.lerp
import com.paris_2.aflami.designsystem.utils.BasePreview
import com.paris_2.aflami.designsystem.utils.PreviewMultiDevices
import kotlinx.coroutines.launch
import kotlin.math.abs


@SuppressLint("RestrictedApi")
@Composable
fun Slider(
    items: List<SliderMedia>,
    onClick: (media: SliderMedia) -> Unit,
    modifier: Modifier = Modifier,
    currentMedia: MutableState<SliderMedia>,
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
                    animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing)
                )
            }
        }
    }

    val configuration = LocalConfiguration.current
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenCenterPx = with(LocalDensity.current) { screenWidth.toPx() } / 2f

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        HorizontalPager(
            state = pagerState,
            contentPadding = if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                PaddingValues(horizontal = 80.dp)
            } else
                PaddingValues(horizontal = 300.dp),
            modifier = Modifier.fillMaxWidth(),
            beyondViewportPageCount = 2,
        ) { page ->
            val isFocused = page == pagerState.currentPage
            val item = items[page]
            var itemCenterX by remember { mutableFloatStateOf(0f) }

            val distanceFromCenter = abs(itemCenterX - screenCenterPx)
            val maxDistance = screenCenterPx
            val normalizedOffset = (distanceFromCenter / maxDistance).coerceIn(0f, 1f)

            val scale = 1f - (0.1f * normalizedOffset)

            currentMedia.value = items[pagerState.currentPage]

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .onGloballyPositioned { layoutCoordinates ->
                        val itemBounds = layoutCoordinates.boundsInWindow()
                        itemCenterX = itemBounds.left + itemBounds.width / 2
                    }
                    .graphicsLayer(
                        scaleX = scale,
                        translationY = lerp(
                            start = 0f,
                            stop = -30f,
                            amount = FastOutSlowInEasing.transform(1f - normalizedOffset)
                        )
                    )

            ) {
                MediaCard(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .clickable { onClick(item) },
                    imageUri = item.imageUri,
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
    val yearOfRelease: String,
)

enum class SliderMediaTypeUi {
    TvShow, Movie
}

@Composable
@PreviewMultiDevices
fun SliderPreview() {
    BasePreview {
        val sampleItems = listOf(
            SliderMedia(
                id = 1,
                imageUri = "https://image.tmdb.org/t/p/w500/your_sample_image1.jpg",
                title = "Sample Movie 1",
                type = SliderMediaTypeUi.Movie,
                categories = listOf("Action", "Adventure"),
                rating = 8.5f,
                yearOfRelease = "2023"
            ),
            SliderMedia(
                id = 2,
                imageUri = "https://image.tmdb.org/t/p/w500/your_sample_image2.jpg",
                title = "Sample TV Show",
                type = SliderMediaTypeUi.TvShow,
                categories = listOf("Drama"),
                rating = 9.0f,
                yearOfRelease = "2022"
            ),
            SliderMedia(
                id = 3,
                imageUri = "https://image.tmdb.org/t/p/w500/your_sample_image3.jpg",
                title = "Sample Movie 2",
                type = SliderMediaTypeUi.Movie,
                categories = listOf("Sci-Fi"),
                rating = 7.8f,
                yearOfRelease = "2024"
            )
        )

        val currentMedia = remember { mutableStateOf(sampleItems.first()) }

        Slider(
            items = sampleItems,
            currentMedia = currentMedia,
            modifier = Modifier.fillMaxWidth(),
            onClick = {}
        )
    }
}