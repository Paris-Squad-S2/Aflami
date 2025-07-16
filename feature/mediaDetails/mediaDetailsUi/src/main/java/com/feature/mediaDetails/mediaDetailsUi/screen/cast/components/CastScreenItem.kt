package com.feature.mediaDetails.mediaDetailsUi.screen.cast.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun CastScreenItem(
    imageUrl: String,
    name: String,
    height: Dp = 104.dp,
    cornerRadius: Dp = 16.dp,
    width: Dp? = null,
    modifier: Modifier = Modifier,
){
    Column(
        modifier = modifier
            .then(
                if (width != null) Modifier.width(width) else TODO()
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .padding(bottom = 4.dp)
                .clip(RoundedCornerShape(cornerRadius))
                .border(
                    width = 1.dp,
                    color = Theme.colors.stroke,
                    shape = RoundedCornerShape(cornerRadius)
                ),
            placeholder = painterResource(id = R.drawable.ic_film_roll),
            error = painterResource(id = R.drawable.img_disconnect)
        )
        Text(
            text = name,
            style = Theme.textStyle.label.small,
            color = Theme.colors.text.body,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            textAlign = TextAlign.Start,
            modifier = Modifier.align(Alignment.Start)
        )
    }
}
