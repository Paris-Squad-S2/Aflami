package com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.seasonSection

import SeasonHeader
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details.EpisodeUi
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun SeasonSection(
    seasonNumber: Int,
    numberOfEpisodes: Int,
    episodes: List<EpisodeUi>,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(Theme.colors.surface)
            .fillMaxWidth()
    ) {
        SeasonHeader(
            seasonNumber = seasonNumber,
            numberOfEpisodes = numberOfEpisodes,
            isExpanded = isExpanded,
            onToggleExpand = onToggleExpand
        )
        SeasonEpisodesSection(
            isVisible = isExpanded,
            episodes = episodes
        )
    }
}

@PreviewLightDark
@Composable
fun PreviewSeasonSection() {
    var isExpanded by remember { mutableStateOf(true) }

    SeasonSection(
        seasonNumber = 1,
        numberOfEpisodes = 2,
        episodes = sampleEpisodes(),
        isExpanded = isExpanded,
        onToggleExpand = { isExpanded = !isExpanded }
    )
}

fun sampleEpisodes(): List<EpisodeUi> {
    return listOf(
        EpisodeUi(
            episodeNumber = 1,
            posterUrl = "",
            voteAverage = 8.2,
            airDate = "2023-05-12",
            runtime = "50",
            description = "An exciting start to the season.",
            stillUrl = ""
        ),
        EpisodeUi(
            episodeNumber = 2,
            posterUrl = "",
            voteAverage = 9.0,
            airDate = "2023-05-19",
            runtime = "52",
            description = "The story deepens with new characters.",
            stillUrl = ""
        )
    )
}