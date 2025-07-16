package com.feature.mediaDetails.mediaDetailsUi.common.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun GallerySection(
    images: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        images.chunked(2).forEach { rowImages ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowImages.forEach { imageUrl ->
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Gallery image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .weight(1f)
                            .height(145.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(
                                width = 1.dp,
                                color = Theme.colors.stroke,
                                shape = RoundedCornerShape(12.dp)
                            ),
                        placeholder = painterResource(id = R.drawable.ic_film_roll),
                        error = painterResource(id = R.drawable.img_disconnect)
                    )
                }
                if (rowImages.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}


@PreviewLightDark
@Composable
fun PreviewGallerySection() {
    AflamiTheme {
        GallerySection(
            images = listOf(
                "https://upload.wikimedia.org/wikipedia/en/5/5a/The_Green_Mile_film_poster.jpg",
                "https://upload.wikimedia.org/wikipedia/en/f/fd/ShawshankRedemptionMoviePoster.jpg",
                "https://upload.wikimedia.org/wikipedia/en/6/69/The_Godfather_%281972%29.png"
            )
        )
    }
}
