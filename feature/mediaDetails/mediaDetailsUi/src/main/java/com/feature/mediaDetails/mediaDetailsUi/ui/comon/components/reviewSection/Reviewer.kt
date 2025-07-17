package com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.reviewSection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.paris_2.aflami.designsystem.components.RatingCard
import com.paris_2.aflami.designsystem.theme.Theme
import com.feature.mediaDetails.mediaDetailsUi.R

@Composable
fun ReviewCard(
    name: String,
    createdAt: String,
    avatarUrl: String,
    username: String,
    rating: Double,
    description: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Theme.colors.surface,
                RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = avatarUrl,
                    contentDescription = "Profile picture",
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.profile_placholder),
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = name,
                        style = Theme.textStyle.title.medium,
                        color = Theme.colors.text.title
                    )
                    Text(
                        text = username,
                        style = Theme.textStyle.label.small,
                        color = Theme.colors.text.hint
                    )
                }

                RatingCard(
                    rating = rating.toFloat(),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            var expanded by remember { mutableStateOf(false) }
            val maxLines = if (expanded) Int.MAX_VALUE else 4

            val annotatedText = buildAnnotatedString {
                withStyle(SpanStyle(color = Theme.colors.text.body)) {
                    append(description)
                }
                if (!expanded) {
                    append(" ")
                    withStyle(SpanStyle(color = Theme.colors.primary)) {
                        append("Read more")
                    }
                }
            }

            ClickableText(
                text = annotatedText,
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis,
                style = Theme.textStyle.body.small,
                onClick = { expanded = true }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = createdAt,
                style = Theme.textStyle.label.small,
                color = Theme.colors.text.hint
            )
        }
    }
}

@PreviewLightDark
@Composable
fun ReviewCardPreview() {
    ReviewCard(
        name = "Manuel São Bento",
        createdAt = "10-09-2016",
        avatarUrl = "https://yourcdn.com/avatar.jpg",
        username = "@msbreviews",
        rating = 9.9,
        description = "Hmmm! I wasn’t sure if I was watching a sentimental edition of “Hawaii Five-O” here or a collection of outtakes from a “Sonic” movie as this "
    )
}
