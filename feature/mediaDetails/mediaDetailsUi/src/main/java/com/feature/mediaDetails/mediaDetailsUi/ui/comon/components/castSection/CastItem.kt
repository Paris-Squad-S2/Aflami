package com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.castSection

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.paris_2.aflami.designsystem.components.PageLoadingPlaceHolder
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun CastItem(
    imageUrl: String,
    name: String,
    height: Dp = 104.dp,
    cornerRadius: Dp = 16.dp,
    width: Dp? = null,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .then(
                if (width != null) Modifier.width(width) else Modifier.fillMaxWidth()
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var imageState by remember { mutableStateOf(ImageState.Loading) }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .padding(bottom = 4.dp)
                .clip(RoundedCornerShape(cornerRadius))
                .border(
                    1.dp,
                    Theme.colors.stroke,
                    RoundedCornerShape(cornerRadius)
                ),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.animation.AnimatedVisibility(visible = imageState == ImageState.Loading) {
                PageLoadingPlaceHolder()
            }
            androidx.compose.animation.AnimatedVisibility(visible = imageState == ImageState.Error) {
                Image(
                    painter = painterResource(id = com.paris_2.aflami.designsystem.R.drawable.img_disconnect),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(8.dp)
                )
            }
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize(),
                onLoading = {
                    imageState = ImageState.Loading
                },
                onError = {
                    imageState = ImageState.Error
                },
                onSuccess = {
                    imageState = ImageState.Success
                }
            )
        }
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

private enum class ImageState {
    Loading, Error, Success
}