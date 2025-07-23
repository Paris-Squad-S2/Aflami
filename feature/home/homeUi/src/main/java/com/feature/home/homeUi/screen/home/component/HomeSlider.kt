package com.feature.home.homeUi.screen.home.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.designSystem.safeimageviewer.SafeImageViewer
import com.feature.home.homeUi.mapper.toSliderMediaList
import com.feature.home.homeUi.screen.home.MediaUiState
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.components.AflamiSectionTitle
import com.paris_2.aflami.designsystem.components.AflamiText
import com.paris_2.aflami.designsystem.components.GenresChip
import com.paris_2.aflami.designsystem.components.IconItem
import com.paris_2.aflami.designsystem.components.Slider
import com.paris_2.aflami.designsystem.components.TopAppBar
import com.paris_2.aflami.designsystem.components.iconItemWithDefaults
import com.paris_2.aflami.designsystem.theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSlider(
    onMediaClick: () -> Unit,
    onSearchIconClick: () -> Unit = {},
    mediaList: List<MediaUiState>,
    modifier: Modifier,
) {
    Box {
        if (mediaList.isNotEmpty())
            SafeImageViewer(
                model = mediaList[1].imageUri,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .blur(18.dp),
                contentScale = ContentScale.FillWidth,
            )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(
                title = "AFLAMI",
                subtitle = "More than just watching.",
                modifier = Modifier.padding(top = 16.dp),
                logo = iconItemWithDefaults(
                    ImageVector.vectorResource(R.drawable.ic_aflami_logo), {},
                    Theme.colors.primaryVariant,
                ),
                trailingIcons = listOf(
                    IconItem(
                        icon = ImageVector.vectorResource(R.drawable.ic_search),
                        onClick = onSearchIconClick,
                        backgroundColor = Theme.colors.surfaceHigh,
                        tint = Theme.colors.text.body
                    )
                )
            )

            AflamiSectionTitle(
                title = "Popular",
                painter = painterResource(com.feature.home.homeUi.R.drawable.ic_fire),
                iconColor = Theme.colors.secondary,
                hasViewAll = false,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Slider(
                items = mediaList.toSliderMediaList(),
                onClick = onMediaClick,
                modifier = modifier
            )

            if (mediaList.isNotEmpty()) {
                AflamiText(
                    text = mediaList[1].title,
                    style = Theme.textStyle.title.small,
                    color = Theme.colors.text.title,
                    modifier = Modifier.padding(top = 8.dp)
                )
                LazyRow (
                    Modifier.padding(top = 8.dp)
                ){
                    items(mediaList[1].categories){
                        GenresChip(
                            title = it,
                            isSelected = false
                        )
                    }
                }
            }

        }
    }

}
