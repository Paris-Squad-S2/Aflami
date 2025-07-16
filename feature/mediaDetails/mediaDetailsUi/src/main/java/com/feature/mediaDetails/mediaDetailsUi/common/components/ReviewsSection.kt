package com.feature.mediaDetails.mediaDetailsUi.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.feature.mediaDetails.mediaDetailsUi.screen.movie.ReviewUi
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun ReviewsSection(
    reviews: List<ReviewUi>?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.colors.surface),
        contentAlignment = Alignment.Center
    ) {
        if (!reviews.isNullOrEmpty()) {
            Column(
                modifier = modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                reviews.forEach { review ->
                    Column {
                        ReviewCard(
                            name = review.name,
                            createdAt = review.createdAt,
                            avatarUrl = review.avatarUrl,
                            username = review.username,
                            rating = review.rating,
                            description = "Hmmm! I wasn’t sure if I was watching a sentimental edition of “Hawaii Five-O” here or a collection of outtakes from a “Sonic” movie as this "
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Theme.colors.stroke)
                                .then(Modifier.height(1.dp))
                        )
                    }
                }
            }
        } else {
            Text(
                text = "There is no reviews!",
                style = Theme.textStyle.label.large,
                color = Theme.colors.text.body.copy(alpha = 0.6f)
            )
        }

    }
}

