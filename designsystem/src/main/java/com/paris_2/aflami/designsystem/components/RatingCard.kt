package com.paris_2.aflami.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme


@Composable
fun RatingCard(
    modifier: Modifier = Modifier,
    rating: Float,
) {
    Row(
        modifier = modifier
            .height(28.dp)
            .border(
                width = 1.dp,
                color = Theme.colors.stroke,
                shape = RoundedCornerShape(
                    topStart = 4.dp,
                    bottomEnd = 4.dp,
                    topEnd = 12.dp,
                    bottomStart = 12.dp
                )
            )
            .clip(
                RoundedCornerShape(
                    topStart = 4.dp,
                    bottomEnd = 4.dp,
                    topEnd = 12.dp,
                    bottomStart = 12.dp
                )
            )
            .background(Theme.colors.primaryVariant),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.padding(start = 9.33.dp, end = 3.33.dp),
            painter = painterResource(R.drawable.rating_star),
            contentDescription = "star"
        )
        Text(
            text = "%.1f".format(java.util.Locale.US, rating),
            color = Theme.colors.text.body,
            modifier = Modifier.padding(end = 8.dp),
            style = Theme.textStyle.label.small
        )
    }
}

@PreviewLightDark
@Composable
fun PreviewRatingCard() {
    AflamiTheme {
        RatingCard(rating = 8.5f)
    }

}

@PreviewLightDark
@Composable
fun PreviewRatingCard2() {
    AflamiTheme {
        RatingCard(rating = 9.946f)
    }
}