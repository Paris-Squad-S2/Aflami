package com.feature.mediaDetails.mediaDetailsUi.ui.comon.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.components.Chips
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.theme.AflamiTheme

@Composable
fun ChipsRowSection(
    items: List<Pair<String, Int>>,
    selectedIndex: Int?,
    onItemSelected: (Int) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items.size) { index ->
            val isSelected = index == selectedIndex
            Chips(
                title = items[index].first,
                icon = painterResource(id = items[index].second),
                isSelected = isSelected,
                onClick = { onItemSelected(index) }
            )
        }
    }
}


@PreviewLightDark
@Composable
fun PreviewChipsRowSections() {
    AflamiTheme {
        val dummyItems = listOf(
            "More like this" to R.drawable.ic_camera_video,
            "Reviews" to R.drawable.ic_starr,
            "Gallery" to R.drawable.ic_album,
            "Production" to R.drawable.ic_city
        )
        AflamiTheme {
            ChipsRowSection(
                items = dummyItems,
                selectedIndex = null,
                onItemSelected = {}
            )
        }
    }
}
