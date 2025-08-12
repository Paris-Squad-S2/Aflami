package com.feature.home.homeUi.screen.home

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.feature.home.homeUi.R
import com.feature.home.homeUi.mapper.CategoryResourceMapper.getResourceId
import com.feature.home.homeUi.mapper.toDisplayName
import com.feature.home.homeUi.screen.continueWatching.ContinueWatchingActivity
import com.feature.home.homeUi.screen.home.components.Chips
import com.feature.home.homeUi.screen.home.components.HomeSection
import com.feature.home.homeUi.screen.home.components.HomeSlider
import com.feature.home.homeUi.screen.home.components.MediaCard
import com.feature.home.homeUi.screen.home.components.MediaCardType
import com.feature.home.homeUi.screen.home.components.MoodPicker
import com.feature.home.homeUi.screen.home.components.MoodPickerDialog
import com.feature.home.homeUi.screen.home.components.SectionTitle
import com.feature.home.homeUi.screen.topRatingMovies.TopRatingActivity
import com.feature.home.homeUi.utils.shimmerable
import com.paris_2.aflami.designsystem.components.AppTopBar
import com.paris_2.aflami.designsystem.components.IconItem
import com.paris_2.aflami.designsystem.components.NetworkError
import com.paris_2.aflami.designsystem.components.iconItemWithDefaults
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
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

@Composable
fun HomeScreenContent(
    state: HomeScreenUIState,
    action: HomeScreenInteractionListener,
) {

    val context = LocalContext.current
    val lazyState = rememberLazyListState()
    val continueWatchingState = rememberLazyListState()
    val topRatedState = rememberLazyListState()
    val upComingState = rememberLazyListState()
    val topRatedScrolling by remember { derivedStateOf { topRatedState.isScrollInProgress } }
    val continueWatchingScrolling by remember { derivedStateOf { continueWatchingState.isScrollInProgress } }
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
    )
    {
        LazyColumn(
            state = lazyState,
            modifier = Modifier.fillMaxSize(),
        ) {

            item {
                HomeSlider(
                    onMediaClick = {
                        action.onMediaSliderClick(it)
                    },
                    mediaList = state.homeUIState.popularMediaList,
                    modifier = Modifier.fillMaxSize(),
                    isShimmerEnabled = state.isPopularMediaLoading
                )
            }


            item {
                HomeSection(
                    title = stringResource(id = R.string.continue_watching),
                    mediaList = state.homeUIState.continueWatchingMediaList,
                    onMediaClick = action::onMediaCardClick,
                    onSectionAllClick = {
                        val intent = Intent(context, ContinueWatchingActivity::class.java)
                        context.startActivity(intent)
                    },
                    isScrolling = continueWatchingScrolling,
                    modifier = Modifier.padding(top = 6.dp),
                    isShimmerEnabled = state.isContinueWatchingLoading
                )
            }


            item {
                HomeSection(
                    title = stringResource(R.string.top_rating),
                    leadingIconPainter = ImageVector.vectorResource(R.drawable.ic_fire),
                    iconColor = Theme.colors.secondary,
                    mediaList = state.homeUIState.topRatedMediaList,
                    onMediaClick = action::onMediaCardClick,
                    onSectionAllClick = {
                        val intent = Intent(context, TopRatingActivity::class.java)
                        context.startActivity(intent)
                    },
                    isScrolling = topRatedScrolling,
                    modifier = Modifier.padding(top = 24.dp),
                    isShimmerEnabled = state.isTopRatingLoading
                )
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
                        .padding(horizontal = 16.dp, vertical = 24.dp),
                )
            }
            item {
                SectionTitle(
                    title = stringResource(R.string.upcoming),
                    modifier = Modifier.padding(bottom = 12.dp),
                    shimmerModifier = Modifier.shimmerable(enabled = state.isCategoryLoading)
                )
                LazyRow(
                    state = upComingState,
                    contentPadding = PaddingValues(start = 12.dp, end = 12.dp, bottom = 12.dp),
                ) {
                    item {
                        AnimatedVisibility(
                            visible = !state.isCategoryLoading,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            Chips(
                                title = stringResource(R.string.all),
                                icon = ImageVector.vectorResource(R.drawable.ic_category_all),
                                isSelected = isAllCategories,
                                onClick = {
                                    action.onAllCategoriesSelect()
                                    if (!isAllCategories) isAllCategories = true
                                },
                                modifier = Modifier
                                    .padding(2.dp)
                            )
                        }
                    }
                    items(15) {
                        AnimatedVisibility(
                            visible = state.isCategoryLoading,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .size(56.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                        .shimmerable(enabled = state.isCategoryLoading)
                                )
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .height(30.dp)
                                        .width(42.dp)
                                        .shimmerable(enabled = state.isCategoryLoading)
                                )
                            }
                        }

                    }
                    items(state.homeUIState.categories.size) { index ->
                        val category = state.homeUIState.categories.keys.elementAt(index)
                        Chips(
                            title = stringResource(category.toDisplayName()),
                            icon = ImageVector.vectorResource(getResourceId(category)),
                            isSelected = state.homeUIState.categories[category] ?: false,
                            onClick = {
                                isAllCategories = false
                                action.onCategorySelect(category = category)
                            },
                            modifier = Modifier
                                .padding(2.dp)

                        )

                    }
                }

            }

            items(5) {
                AnimatedVisibility(
                    visible = state.isCategoryLoading,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 8.dp)
                            .shimmerable(enabled = state.isCategoryLoading)
                    )
                }
            }
            items(state.homeUIState.upComingMediaList) { upcomingMedia ->
                MediaCard(
                    imageUri = upcomingMedia.imageUri,
                    rating = upcomingMedia.rating?.toFloat(),
                    movieName = upcomingMedia.title,
                    mediaType = stringResource(upcomingMedia.type.mediaID),
                    year = upcomingMedia.yearOfRelease.year.toString(),
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
    }

    AppTopBar(
        title = stringResource(R.string.aflami),
        subtitle = stringResource(R.string.more_than_just_watching),
        titleTextStyle = Theme.textStyle.logoText,
        modifier = Modifier
            .background(topBarBackground)
            .padding(top = 32.dp),
        logo = iconItemWithDefaults(
            icon = ImageVector.vectorResource(com.paris_2.aflami.designsystem.R.drawable.ic_aflami_logo),
            backgroundColor = Theme.colors.primaryVariant,
            tint = Color.Unspecified,
        ),
        trailingIcons = listOf(
            IconItem(
                icon = ImageVector.vectorResource(com.paris_2.aflami.designsystem.R.drawable.ic_search),
                onClick = action::onSearchIconClick,
                backgroundColor = Theme.colors.primaryVariant,
                tint = Theme.colors.text.body
            )
        )
    )

    AnimatedVisibility(
        visible = state.homeUIState.showMoodPickerDialog&& state.homeUIState.moodPickerMovie != null,
        enter = slideInVertically { it },
        exit = slideOutVertically { it }
    ) {
        val moodPickerMovie = state.homeUIState.moodPickerMovie
        MoodPickerDialog(
            movie = moodPickerMovie!!,
            onDismiss = { action.onDismissMoodPicker() },
            onViewDetailsClick = { action.onMediaCardClick(moodPickerMovie) },
            onGetAnotherMovieClick = { action.getRandomMoodPickerMovie() }
        )
    }

}
