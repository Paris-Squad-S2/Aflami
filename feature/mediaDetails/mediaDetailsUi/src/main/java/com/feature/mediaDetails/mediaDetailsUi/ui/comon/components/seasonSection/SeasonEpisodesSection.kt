package com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.seasonSection

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.domain.mediaDetails.model.Episode
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details.EpisodeUi
import com.paris_2.aflami.designsystem.components.EpisodeCard
import kotlinx.datetime.toJavaLocalDate


@Composable
fun SeasonEpisodesSection(
    isVisible: Boolean,
    episodes: List<EpisodeUi>,
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
        exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp)
        ) {
            episodes.forEach { episode ->
                EpisodeCard(
                    episodeRating = episode.voteAverage.toFloat(),
                    episodeNumber = episode.episodeNumber.toString(),
                    episodeTitle = episode.episodeNumber.toString(),
                    episodeDuration = episode.runtime,
                    episodeDate = episode.airDate,
                    episodeDescription = episode.description,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}