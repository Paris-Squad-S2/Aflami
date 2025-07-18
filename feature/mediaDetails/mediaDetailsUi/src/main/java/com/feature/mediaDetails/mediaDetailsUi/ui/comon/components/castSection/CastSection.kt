package com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.castSection

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.feature.mediaDetails.mediaDetailsUi.R
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.CastUi
import com.paris_2.aflami.designsystem.theme.Theme
import com.paris_2.aflami.designsystem.utils.BasePreview

@Composable
fun CastSection(
    castList: List<CastUi>,
    onSeeAllClick: () -> Unit,
) {
    Column(
        modifier = Modifier.
        fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.cast),
                style = Theme.textStyle.headline.small,
                color = Theme.colors.text.title
            )
            Text(
                text = stringResource(R.string.all),
                style = Theme.textStyle.label.medium,
                color = Theme.colors.primary,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable {
                        onSeeAllClick()
                    }
            )
        }
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(castList.size) { index ->
                CastItem(
                    imageUrl = castList[index].imageUrl,
                    name = castList[index].name,
                    modifier = Modifier,
                    height = 78.dp,
                    width = 78.dp
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .height(1.dp)
                .background(Theme.colors.stroke)
        )
    }
}

@PreviewLightDark
@Composable
fun PreviewCastSection() {
    BasePreview {

        val sampleCast = listOf(
            CastUi("Tom Hanks", "https://upload.wikimedia.org/wikipedia/commons/a/a9/Tom_Hanks_TIFF_2019.jpg"),
            CastUi("Michael Clarke", "https://upload.wikimedia.org/wikipedia/commons/e/e1/Michael_Clarke_Duncan.jpg"),
            CastUi("David Morse", "https://upload.wikimedia.org/wikipedia/commons/d/d6/David_Morse_2007.jpg"),
            CastUi("Bonnie Hunt", "https://upload.wikimedia.org/wikipedia/commons/f/fa/Bonnie_Hunt_2011.jpg")
        )

        CastSection(castList = sampleCast, onSeeAllClick = {})

    }
}
