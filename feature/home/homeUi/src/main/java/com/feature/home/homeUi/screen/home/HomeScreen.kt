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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.feature.home.homeUi.R
import com.feature.home.homeUi.mapper.CategoryResourceMapper.getResourceId
import com.feature.home.homeUi.screen.home.component.HomeSlider
import com.feature.home.homeUi.screen.home.components.MoodPickerDialog
import com.paris_2.aflami.designsystem.components.AflamiMediaCard
import com.paris_2.aflami.designsystem.components.AflamiSectionTitle
import com.paris_2.aflami.designsystem.components.Chips
import com.paris_2.aflami.designsystem.components.MediaCardType
import com.paris_2.aflami.designsystem.components.MoodPicker
import com.paris_2.aflami.designsystem.theme.Theme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = koinViewModel(),
) {
    val homeScreenState = viewModel.screenState.collectAsStateWithLifecycle()

    HomeScreenContent(
        state = homeScreenState.value,
        action = viewModel
    )

}

@Composable
fun HomeScreenContent(
    state: HomeScreenUIState,
    action: HomeScreenInteractionListener
) {

    val lazyState = rememberLazyListState()
    val isScrolling by remember { derivedStateOf { lazyState.isScrollInProgress } }
    var isAllCategories by remember { mutableStateOf(state.homeUIState.isAllCategories) }

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
                mediaList = state.homeUIState.popularMediaList,
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
                items(state.homeUIState.continueWatchingMediaList) { media ->
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
                items(state.homeUIState.topRatedMediaList) { media ->
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


        item {
            MoodPicker(
                title = stringResource(R.string.mood_picker_get_a_movie),
                question = stringResource(R.string.what_s_your_vibe_today),
                onEmojiClick = { emojiMood ->
                   action.moodPickerSelected(emojiMood.tags)
                },
                image = painterResource(com.paris_2.aflami.designsystem.R.drawable.img_clown),
                backgroundColor = listOf(
                    Theme.colors.primary,
                    Theme.colors.status.redAccent,
                    Theme.colors.status.yellowAccent,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            )
        }
        item {
            AflamiSectionTitle(
                title = stringResource(R.string.upcoming),
                modifier = Modifier.padding(bottom = 12.dp)
            )
            LazyRow(
                contentPadding = PaddingValues(start = 12.dp, end = 12.dp, bottom = 12.dp),
            ) {
                item {
                    Chips(
                        title = stringResource(R.string.all),
                        icon = painterResource(R.drawable.ic_category_all),
                        isSelected = isAllCategories,
                        onClick = {
                            action.onAllCategoriesSelect()
                            if (!isAllCategories) isAllCategories = true
                        }
                    )
                }
                items(state.homeUIState.categories.size) { index ->
                    val category = state.homeUIState.categories.keys.elementAt(index)
                    Chips(
                        title = category.name,
                        icon = painterResource(getResourceId(category.id)),
                        isSelected = state.homeUIState.categories[category] ?: false,
                        onClick = {
                            isAllCategories = false
                            action.onCategorySelect(category = category)
                        }
                    )
                }
            }

        }

        items(state.homeUIState.upComingMediaList) { upcomingMedia ->
            AflamiMediaCard(
                imageUri = upcomingMedia.imageUri,
                rating = upcomingMedia.rating.toFloat(),
                movieName = upcomingMedia.title,
                mediaType = upcomingMedia.type.toString(),
                year = upcomingMedia.yearOfRelease.toString(),
                mediaCardType = MediaCardType.UP_COMING,
                showGradientFilter = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp)
                    .clickable {
                        action.onMediaCardClick(upcomingMedia)
                    },
            )
        }
    }
    if (state.homeUIState.showMoodPickerDialog && state.homeUIState.moodPickerMovie != null) {
            MoodPickerDialog(
                movie = state.homeUIState.moodPickerMovie,
                onDismiss = { action.onDismissMoodPicker() },
                onViewDetailsClick = {action.onMediaCardClick(state.homeUIState.moodPickerMovie) },
                onGetAnotherMovieClick = {action.getRandomMoodPickerMovie() }
            )


    }
}




@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}