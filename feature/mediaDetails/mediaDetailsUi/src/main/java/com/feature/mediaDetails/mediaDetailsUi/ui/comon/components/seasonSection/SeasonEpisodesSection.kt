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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details.EpisodeUi
import com.paris_2.aflami.designsystem.components.EpisodeCard


@Composable
fun SeasonEpisodesSection(
    maxHeight: Dp,
    isVisible: Boolean,
    episodes: List<EpisodeUi>,
) {
    AnimatedVisibility(
        modifier = Modifier.height(maxHeight),
        visible = isVisible,
        enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
        exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp)
        ) {
            items(episodes.size) { episodeIndex ->
                val episode = episodes[episodeIndex]
                EpisodeCard(
                    episodeRating = episode.voteAverage.toFloat(),
                    episodeNumber = episode.episodeNumber.toString(),
                    episodeTitle = episode.episodeNumber.toString(),
                    episodeDuration = episode.runtime,
                    imageUri = episode.stillUrl,
                    episodeDate = episode.airDate,
                    episodeDescription = episode.description,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}