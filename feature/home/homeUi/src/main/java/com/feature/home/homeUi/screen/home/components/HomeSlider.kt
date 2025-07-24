package com.feature.home.homeUi.screen.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.designSystem.safeimageviewer.SafeImageViewer
import com.feature.home.homeUi.R
import com.paris_2.aflami.designsystem.components.AflamiSectionTitle
import com.paris_2.aflami.designsystem.components.AflamiText
import com.paris_2.aflami.designsystem.components.GenresChip
import com.paris_2.aflami.designsystem.components.Slider
import com.paris_2.aflami.designsystem.components.SliderMedia
import com.paris_2.aflami.designsystem.components.SliderMediaTypeUi
import com.paris_2.aflami.designsystem.theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSlider(
    onMediaClick: (media: SliderMedia) -> Unit,
    mediaList: List<SliderMedia>,
    modifier: Modifier,
) {
    val mediaState = remember { mutableStateOf<SliderMedia>(SliderMedia(
        id = 0,
        imageUri = "",
        title = "",
        type = SliderMediaTypeUi.Movie,
        categories = emptyList(),
        rating = 0f,
        yearOfRelease = "2022",
    )) }


    Box {
        AnimatedVisibility(
            visible = mediaState.toString().isNotEmpty(),
            enter = slideInVertically(),
            exit = slideOutVertically(),
        ){

            SafeImageViewer(
                    model = mediaState.value.imageUri,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(420.dp)
                        .blur(18.dp),
                    contentScale = ContentScale.FillWidth,
                )

            Column(
                modifier = Modifier.padding(top = 96.dp, bottom = 65.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AflamiSectionTitle(
                    title = stringResource(R.string.popular),
                    painter = painterResource(R.drawable.ic_fire),
                    iconColor = Theme.colors.secondary,
                    hasViewAll = false,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                if (mediaList.isNotEmpty()) {
                    Slider(
                        items = mediaList,
                        onClick = { media ->
                            onMediaClick(media)
                        },
                        modifier = modifier,
                        currentMedia = mediaState,
                    )
                }
            }
        }


        AnimatedVisibility(
            visible = mediaState.toString().isNotEmpty(),
            modifier = Modifier.align(Alignment.BottomCenter),
            enter = slideInVertically(),
            exit = slideOutVertically(),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                AflamiText(
                    text = mediaState.value.title,
                    style = Theme.textStyle.title.small,
                    color = Theme.colors.text.title,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    minLines = 1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                LazyRow(
                    Modifier.padding(top = 8.dp).padding(horizontal = 16.dp)
                ) {
                    items(mediaState.value.categories) {
                        GenresChip(
                            title = it,
                            isSelected = false
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }
            }
        }
    }
}
