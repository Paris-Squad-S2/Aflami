package com.feature.home.homeUi.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.feature.home.homeUi.R
import com.feature.home.homeUi.mapper.CategoryResourceMapper.getResourceId
import com.feature.home.homeUi.screen.home.components.HomeSection
import com.feature.home.homeUi.screen.home.components.HomeSlider
import com.feature.home.homeUi.screen.home.components.MoodPickerDialog
import com.paris_2.aflami.designsystem.components.Chips
import com.paris_2.aflami.designsystem.components.FontType
import com.paris_2.aflami.designsystem.components.IconItem
import com.paris_2.aflami.designsystem.components.MediaCard
import com.paris_2.aflami.designsystem.components.MediaCardType
import com.paris_2.aflami.designsystem.components.MoodPicker
import com.paris_2.aflami.designsystem.components.NetworkError
import com.paris_2.aflami.designsystem.components.PageLoadingPlaceHolder
import com.paris_2.aflami.designsystem.components.SectionTitle
import com.paris_2.aflami.designsystem.components.TopAppBar
import com.paris_2.aflami.designsystem.components.iconItemWithDefaults
import com.paris_2.aflami.designsystem.theme.Theme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = koinViewModel(),
) {
    val homeScreenState = viewModel.screenState.collectAsStateWithLifecycle()

    when {
        homeScreenState.value.errorMessage != null -> {
            NetworkError(
                modifier = Modifier.fillMaxSize(),
                onRetry = viewModel::onRetry
            )
        }

        else -> HomeScreenContent(
            state = homeScreenState.value,
            action = viewModel
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    state: HomeScreenUIState,
    action: HomeScreenInteractionListener
) {

    val lazyState = rememberLazyListState()
    val isScrolling by remember { derivedStateOf { lazyState.isScrollInProgress } }
    var isAllCategories by remember { mutableStateOf(state.homeUIState.isAllCategories) }
    val density = LocalDensity.current
    val maxScrollPx = with(density) { 56.dp.toPx() }

    val alpha by remember {
        derivedStateOf {
            val scroll =
                if (lazyState.firstVisibleItemIndex > 0) maxScrollPx else lazyState.firstVisibleItemScrollOffset.toFloat()
            (scroll / maxScrollPx).coerceIn(0f, 1f)
        }
    }
    val topBarBackground = Theme.colors.surface.copy(alpha = alpha)

    Box(
        Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            state = lazyState,
            modifier = Modifier.fillMaxSize(),
        ) {

            if (state.homeUIState.popularMediaList.isNotEmpty()) {
                item {
                    HomeSlider(
                        onMediaClick = {
                            action.onMediaSliderClick(it)
                        },
                        mediaList = state.homeUIState.popularMediaList,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            } else if (state.isPopularMediaLoading) {
                item {
                    PageLoadingPlaceHolder(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(410.dp)
                            .statusBarsPadding()
                    )
                }
            }


            if (state.homeUIState.continueWatchingMediaList.isNotEmpty()) {
                item {
                    HomeSection(
                        title = stringResource(id = com.feature.home.homeUi.R.string.continue_watching),
                        mediaList = state.homeUIState.continueWatchingMediaList,
                        onMediaClick = action::onMediaCardClick,
                        onSectionAllClick = action::navigateToContinueWatchingScreen,
                        isScrolling = isScrolling,
                        modifier = Modifier.padding(top=8.dp)
                    )
                }
            } else if (state.isContinueWatchingLoading) {
                item {
                    PageLoadingPlaceHolder(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(410.dp)
                            .statusBarsPadding()
                    )
                }
            }

            if (state.homeUIState.topRatedMediaList.isNotEmpty()) {
                item {
                    HomeSection(
                        title = stringResource(com.feature.home.homeUi.R.string.top_rating),
                        leadingIconPainter = ImageVector.vectorResource(R.drawable.ic_fire),
                        iconColor = Theme.colors.secondary,
                        mediaList = state.homeUIState.topRatedMediaList,
                        onMediaClick = action::onMediaCardClick,
                        onSectionAllClick = action::navigateToTopRatingScreen,
                        isScrolling = isScrolling,
                        modifier = Modifier.padding(top=8.dp)
                    )
                }
            } else if (state.isTopRatingLoading) {
                item {
                    PageLoadingPlaceHolder(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(410.dp)
                            .statusBarsPadding()
                    )
                }
            }

            item {
                MoodPicker(
                    title = stringResource(com.feature.home.homeUi.R.string.mood_picker_get_a_movie),
                    question = stringResource(com.feature.home.homeUi.R.string.what_s_your_vibe_today),
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
                        .padding(horizontal = 16.dp, vertical = 24.dp),
                )
            }
            item {
                SectionTitle(
                    title = stringResource(com.feature.home.homeUi.R.string.upcoming),
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                LazyRow(
                    contentPadding = PaddingValues(start = 12.dp, end = 12.dp, bottom = 12.dp),
                ) {
                    item {
                        Chips(
                            title = stringResource(com.feature.home.homeUi.R.string.all),
                            icon = ImageVector.vectorResource(R.drawable.ic_category_all),
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
                            icon = ImageVector.vectorResource(getResourceId(category.id)),
                            isSelected = state.homeUIState.categories[category] ?: false,
                            onClick = {
                                isAllCategories = false
                                action.onCategorySelect(category = category)
                            }
                        )
                    }
                }

            }

            if (state.homeUIState.upComingMediaList.isNotEmpty()) {
                items(state.homeUIState.upComingMediaList) { upcomingMedia ->
                    MediaCard(
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
            } else if (state.isCategoryLoading) {
                item {
                    PageLoadingPlaceHolder(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(410.dp)
                            .statusBarsPadding()
                    )
                }
            }
        }
    }

    TopAppBar(
        title = stringResource(com.feature.home.homeUi.R.string.aflami),
        subtitle = stringResource(R.string.more_than_just_watching),
        fontType = FontType.NICOMOJ,
        modifier = Modifier
            .background(topBarBackground)
            .padding(top = 32.dp),
        logo = iconItemWithDefaults(
            ImageVector.vectorResource(com.paris_2.aflami.designsystem.R.drawable.ic_aflami_logo),
            {},
            Theme.colors.primaryVariant,
        ),
        trailingIcons = listOf(
            IconItem(
                icon = ImageVector.vectorResource(com.paris_2.aflami.designsystem.R.drawable.ic_search),
                onClick = action::onSearchIconClick,
                backgroundColor = Theme.colors.surfaceHigh,
                tint = Theme.colors.text.body
            )
        )
    )

    if (state.homeUIState.showMoodPickerDialog && state.homeUIState.moodPickerMovie != null) {
        val moodPickerMovie = state.homeUIState.moodPickerMovie
        MoodPickerDialog(
            movie = moodPickerMovie,
            onDismiss = { action.onDismissMoodPicker() },
            onViewDetailsClick = { action.onMediaCardClick(moodPickerMovie) },
            onGetAnotherMovieClick = { action.getRandomMoodPickerMovie() }
        )
    }
}
