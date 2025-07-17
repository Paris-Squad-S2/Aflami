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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.feature.mediaDetails.mediaDetailsUi.R
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.descriptionSection.ExpandableText
import com.paris_2.aflami.designsystem.components.RatingCard
import com.paris_2.aflami.designsystem.theme.Theme

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

            ExpandableText(description)

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
        description = "In 1935, corrections officer Paul Edgecomb oversees \"The Green Mile,\" the death row section of Cold Mountain Penitentiary, alongside officers Brutus Howell, Dean Stanton, Harry Terwilliger. When John Coffey, a giant African-American man convicted of brutally murdering two little white girls, arrives on death row, Paul begins to notice something unusual about him. Coffey seems to possess a supernatural power to heal people's ailments. As Paul and his fellow officers investigate further, they discover that Coffey may be innocent of the crimes he was convicted of. The story explores themes of justice, redemption, and the supernatural within the confines of a Depression-era prison.",
    )
}
