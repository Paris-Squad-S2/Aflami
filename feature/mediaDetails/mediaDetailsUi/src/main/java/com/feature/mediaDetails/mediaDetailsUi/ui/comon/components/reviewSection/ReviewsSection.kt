package com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.reviewSection

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.feature.mediaDetails.mediaDetailsUi.R
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.ReviewUi
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun ReviewsSection(
    reviews: LazyPagingItems<ReviewUi>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.colors.surface),
        contentAlignment = Alignment.Center
    ) {
        if (reviews.itemCount>=1) {
            Column(
                modifier = modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                reviews.itemSnapshotList.items.forEach { review ->
                    Column {
                        ReviewCard(
                            name = review.name,
                            createdAt = review.createdAt,
                            avatarUrl = review.avatarUrl,
                            username = review.username,
                            rating = review.rating,
                            description = review.description
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
                text = stringResource(R.string.there_is_no_reviews),
                style = Theme.textStyle.label.large,
                color = Theme.colors.text.body.copy(alpha = 0.6f)
            )
        }

    }
}

