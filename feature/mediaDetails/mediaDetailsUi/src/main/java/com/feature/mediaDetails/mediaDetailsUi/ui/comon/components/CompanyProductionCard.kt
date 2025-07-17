package com.feature.mediaDetails.mediaDetailsUi.ui.comon.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun CompanyProductionCard(
    modifier: Modifier = Modifier,
    imageUrl: String,
    companyName: String,
    countryName: String
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(145.dp)
            .border(
                width = 1.dp,
                color = Theme.colors.stroke,
                shape = RoundedCornerShape( 12.dp)
            )
            .clip(RoundedCornerShape( 12.dp))
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "$companyName logo",
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            contentScale = ContentScale.Fit,
            placeholder = painterResource(id = R.drawable.ic_film_roll),
            error = painterResource(id = R.drawable.img_disconnect)
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 8.dp
                )
        ) {
            Text(
                text = companyName,
                style = Theme.textStyle.label.large,
                color = Theme.colors.onPrimaryColors.onPrimary,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
            Text(
                text = countryName,
                style = Theme.textStyle.label.small,
                color = Theme.colors.onPrimaryColors.onPrimaryBody,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }
    }
}

@PreviewLightDark
@Composable
fun CompanyProductionCardPreview() {
    AflamiTheme {
        CompanyProductionCard(
            imageUrl = "https://xl.movieposterdb.com/12_03/1999/120689/xl_120689_c927b987.jpg",
            companyName = "Universal",
            countryName = "US"
        )
    }
}
