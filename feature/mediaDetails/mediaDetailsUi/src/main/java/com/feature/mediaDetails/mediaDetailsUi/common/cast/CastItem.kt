package com.feature.mediaDetails.mediaDetailsUi.common.cast

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun CastItem(
    imageUrl: String,
    name: String,
    modifier: Modifier,
) {
    Column(
        modifier.width(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(78.dp)
                .clip(RoundedCornerShape(16.dp))
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = name,
            style = Theme.textStyle.label.small,
            color = Theme.colors.text.body,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )

    }

}

@PreviewLightDark
@Composable
fun CastItemPreview() {
    CastItem(
        "https://xl.movieposterdb.com/12_03/1999/120689/xl_120689_c927b987.jpg",
        "Ahmed",
        modifier = Modifier
    )
}