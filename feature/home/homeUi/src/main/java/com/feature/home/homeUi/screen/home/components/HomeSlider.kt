package com.feature.home.homeUi.screen.home.components

import android.os.Build
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.designSystem.safeimageviewer.SafeImageViewer
import com.feature.home.homeUi.R
import com.paris_2.aflami.designsystem.components.GenresChip
import com.paris_2.aflami.designsystem.components.Icon
import com.paris_2.aflami.designsystem.components.SectionTitle
import com.paris_2.aflami.designsystem.components.Slider
import com.paris_2.aflami.designsystem.components.SliderMedia
import com.paris_2.aflami.designsystem.components.SliderMediaTypeUi
import com.paris_2.aflami.designsystem.components.Text
import com.paris_2.aflami.designsystem.theme.Theme
import io.sifr.shaded.blurProcessor.BlurEdgeTreatment
import io.sifr.shaded.modifiers.blur


@Composable
fun HomeSlider(
    onMediaClick: (media: SliderMedia) -> Unit,
    mediaList: List<SliderMedia>,
    modifier: Modifier,
) {
    val mediaState = remember {
        mutableStateOf(
            SliderMedia(
                id = 0,
                imageUri = "",
                title = "",
                type = SliderMediaTypeUi.Movie,
                categories = emptyList(),
                rating = 0f,
                yearOfRelease = "2022",
            )
        )
    }


    Box {
        AnimatedVisibility(
            visible = mediaState.toString().isNotEmpty(),
            enter = slideInVertically(),
            exit = slideOutVertically(),
        ) {

            SafeImageViewer(
                model = mediaState.value.imageUri,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .height(400.dp)
                    .fillMaxWidth()
                    .then(if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                        Modifier.blur(
                            radius = 12.dp,
                            edgeTreatment = BlurredEdgeTreatment.Unbounded,
                        )
                    }else {
                Modifier
                    .blur(radius = 12f, edgeTreatment = BlurEdgeTreatment.UNBOUNDED)
            })
            )

            Column(
                modifier = Modifier.padding(top = 96.dp, bottom = 56.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                SectionTitle(
                    title = stringResource(R.string.popular),
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_fire),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .size(width = 16.dp, height = 18.dp),
                            tint = Theme.colors.secondary,
                        )
                    },
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
                Text(
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
                    Modifier
                        .padding(top = 8.dp)
                        .padding(horizontal = 16.dp)
                ) {
                    items(mediaState.value.categories.take(3)) {
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
