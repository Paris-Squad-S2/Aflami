package com.feature.home.homeUi.screen.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.feature.home.homeUi.mapper.toSliderMediaList
import com.feature.home.homeUi.screen.home.MediaUiState
import com.paris_2.aflami.designsystem.components.Slider

@Composable
fun HomeSlider(
    onMediaClick: () -> Unit,
    mediaList: List<MediaUiState>,
    modifier: Modifier
) {
    Column {
        Row {

        }
        Slider(
            items = mediaList.toSliderMediaList(),
            onClick = onMediaClick,
            modifier = modifier
        )
    }

}
