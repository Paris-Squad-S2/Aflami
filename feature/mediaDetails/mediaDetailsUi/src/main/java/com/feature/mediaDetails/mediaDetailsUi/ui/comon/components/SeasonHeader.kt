import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.domain.mediaDetails.model.Episode
import com.feature.mediaDetails.mediaDetailsUi.R
import com.paris_2.aflami.designsystem.components.EpisodeCard
import com.paris_2.aflami.designsystem.theme.Theme
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate

@Composable
fun SeasonHeader(
    seasonNumber: Int,
    numberOfEpisodes: Int,
    episodes: List<Episode>,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(Theme.colors.surface)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = "Season $seasonNumber",
                style = Theme.textStyle.title.small,
                color = Theme.colors.text.title
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "$numberOfEpisodes episodes",
                style = Theme.textStyle.label.small,
                color = Theme.colors.text.hint
            )

            IconButton(onClick = onToggleExpand) {
                Image(
                    painter = painterResource(
                        id = if (isExpanded) R.drawable.ic_not_expanded else R.drawable.ic_expanded
                    ),
                    contentDescription = "Expand/Collapse",
                    alignment = Alignment.Center
                )
            }
        }

        AnimatedVisibility(
            visible = isExpanded,
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
                        episodeTitle = "Episode ${episode.episodeNumber}",
                        episodeDuration = "${episode.runtime} min",
                        episodeDate = episode.airDate.toJavaLocalDate().toString(),
                        episodeDescription = episode.description,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun PreviewableSeasonHeader(initiallyExpanded: Boolean) {
    var expanded by remember { mutableStateOf(initiallyExpanded) }

    SeasonHeader(
        seasonNumber = 1,
        numberOfEpisodes = 2,
        isExpanded = expanded,
        onToggleExpand = { expanded = !expanded },
        episodes = listOf(
            Episode(
                id = 1,
                episodeNumber = 1,
                posterUrl = "",
                voteAverage = 8.4,
                airDate = LocalDate(2022, 1, 1),
                runtime = 45,
                description = "Episode 1 description",
                stillUrl = ""
            ),
            Episode(
                id = 2,
                episodeNumber = 2,
                posterUrl = "",
                voteAverage = 9.0,
                airDate = LocalDate(2022, 1, 8),
                runtime = 50,
                description = "Episode 2 description",
                stillUrl = ""
            )
        )
    )
}

@Preview(name = "Collapsed", showBackground = true)
@Composable
fun PreviewSeasonHeaderCollapsed() {
    PreviewableSeasonHeader(initiallyExpanded = false)
}

@Preview(name = "Expanded", showBackground = true)
@Composable
fun PreviewSeasonHeaderExpanded() {
    PreviewableSeasonHeader(initiallyExpanded = true)
}
