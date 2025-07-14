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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.domain.mediaDetails.model.Cast
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun CastItem(
    cast: Cast,
    modifier: Modifier,
) {
    Column(
        modifier.width(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = cast.imageUrl,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(16.dp))
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = cast.name,
            style = Theme.textStyle.label.small,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )

    }

}