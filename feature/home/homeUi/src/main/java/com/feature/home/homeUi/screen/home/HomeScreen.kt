package com.feature.home.homeUi.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.feature.home.homeUi.R
import com.feature.home.homeUi.screen.home.component.HomeSlider
import com.paris_2.aflami.designsystem.components.AflamiMediaCard
import com.paris_2.aflami.designsystem.components.AflamiSectionTitle
import com.paris_2.aflami.designsystem.components.MediaCardType
import com.paris_2.aflami.designsystem.theme.Theme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = koinViewModel(),
) {
    val homeScreenState = viewModel.screenState.collectAsStateWithLifecycle()

    HomeScreenContent(
        state = homeScreenState.value.homeUIState,
        action = viewModel
    )

}

@Composable
fun HomeScreenContent(
    state: HomeUIState,
    action: HomeScreenInteractionListener
) {

    val lazyState = rememberLazyListState()
    val isScrolling by remember { derivedStateOf { lazyState.isScrollInProgress } }

    LazyColumn(
        state = lazyState,
        modifier = Modifier.fillMaxSize(),
    ) {

        item {
            HomeSlider(
                onSearchIconClick = {
                    action.onSearchIconClick()
                },
                onMediaClick = {
//                    actions.onMediaCardClick()
                },
                mediaList = state.popularMediaList,
                modifier = Modifier.fillMaxSize(),
            )
        }


        item {
            AflamiSectionTitle(
                title = stringResource(R.string.continue_watching),
                hasViewAll = true,
                onClickViewAll = {
                    action.navigateToContinueWatchingScreen()
                },
                modifier = Modifier.padding(top = 10.dp)
            )

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(state.continueWatchingMediaList) { media ->
                    AflamiMediaCard(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable {
//                    actions.onMediaCardClick()
                            },
                        imageUri = media.imageUri,
                        rating = media.rating.toFloat(),
                        movieName = media.title,
                        mediaType = media.type.mediaName,
                        year = media.yearOfRelease.year.toString(),
                        mediaCardType = MediaCardType.NORMAL,
                        showGradientFilter = true,
                        enabled = !isScrolling,
                    )
                }
            }
        }

        item {
            AflamiSectionTitle(
                title = stringResource(R.string.top_rating),
                hasViewAll = true,
                painter = painterResource(R.drawable.ic_fire),
                iconColor = Theme.colors.secondary,
                onClickViewAll = {
                    action.navigateToTopRatingScreen()
                },
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(state.topRatedMediaList) { media ->
                    AflamiMediaCard(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable {
                                // nav to media Details
                            },
                        imageUri = media.imageUri,
                        rating = media.rating.toFloat(),
                        movieName = media.title,
                        mediaType = media.type.mediaName,
                        year = media.yearOfRelease.year.toString(),
                        mediaCardType = MediaCardType.NORMAL,
                        showGradientFilter = true,
                        enabled = !isScrolling,
                    )
                }
            }
        }

        // Todo ( Movie birthday )

//         MoodPicker(
//             modifier = TODO(),
//             backgroundColor = TODO(),
//             image = TODO(),
//             onEmojiClick = TODO(),
//             title = TODO(),
//             question = TODO()
//         )

        // Todo ( upComing )

        // Todo ( Bottom Bar )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}