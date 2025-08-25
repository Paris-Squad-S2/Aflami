package com.feature.mediaDetails.mediaDetailsUi.ui.comon.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.paris.aflami.designsystem.R
import com.paris.aflami.designsystem.theme.AflamiTheme
import com.paris.aflami.designsystem.theme.Theme

fun LazyGridScope.gallerySection(
    images: List<String>,
) {
    items(images) { image ->
        AsyncImage(
            model = image,
            contentDescription = "Gallery image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
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

}


@PreviewLightDark
@Composable
fun PreviewGallerySection() {
    AflamiTheme {
        LazyVerticalGrid(
            columns = androidx.compose.foundation.lazy.grid.GridCells.Adaptive(150.dp),
        ) {
            gallerySection(
                images = listOf(
                    "https://upload.wikimedia.org/wikipedia/en/5/5a/The_Green_Mile_film_poster.jpg",
                    "https://upload.wikimedia.org/wikipedia/en/f/fd/ShawshankRedemptionMoviePoster.jpg",
                    "https://upload.wikimedia.org/wikipedia/en/6/69/The_Godfather_%281972%29.png"
                )
            )
        }
    }
}
