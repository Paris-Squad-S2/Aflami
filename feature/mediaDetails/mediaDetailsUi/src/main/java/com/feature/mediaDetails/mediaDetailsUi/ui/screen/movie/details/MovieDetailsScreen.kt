@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details

import android.annotation.SuppressLint
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.feature.mediaDetails.mediaDetailsUi.R
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.AddToListDialog
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.ChipsRowSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.gallerySection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.MediaCard
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.MediaCardType
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.MovieTopComponent
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.MovieTopComponentDetails
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.RatingDialog
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.VideoPlayer
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.castSection.CastSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.companyProductionSection.productionCompanySection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.descriptionSection.DescriptionSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.reviewSection.ReviewsSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.hasDescriptionContent
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.components.CreateListDialog
import com.paris.aflami.designsystem.components.AppSnackBar
import com.paris.aflami.designsystem.components.AppText
import com.paris.aflami.designsystem.components.AppTopBar
import com.paris.aflami.designsystem.components.PageLoadingPlaceHolder
import com.paris.aflami.designsystem.components.PlaceholderView
import com.paris.aflami.designsystem.components.iconItemWithDefaults
import com.paris.aflami.designsystem.theme.Theme
import kotlinx.coroutines.delay
import com.paris.aflami.designsystem.R as RDesignSystem

@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailsViewModel = hiltViewModel(),
) {
    val state = viewModel.screenState.collectAsStateWithLifecycle()
    MovieDetailsScreenContent(
        state = state.value,
        movieDetailsScreenInteractionListener = viewModel
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun MovieDetailsScreenContent(
    state: MovieDetailsScreenState,
    movieDetailsScreenInteractionListener: MovieDetailsScreenInteractionListener,
) {
    val movieChips = MovieChips.entries
    val activity = LocalActivity.current
    var currentRating by remember { mutableFloatStateOf(state.movieDetailsUiState.selectedRating) }
    val defaultIndex = movieChips.indexOf(MovieChips.REVIEWS)
    val selectedIndex = rememberSaveable { mutableIntStateOf(defaultIndex) }
    val mediaList = state.movieDetailsUiState.recommendations.collectAsLazyPagingItems()
    val scrollState = rememberLazyGridState()

    LaunchedEffect(state.snackBarSuccess, state.showSnackBar) {
        if (state.showSnackBar && state.snackBarSuccess) {
            delay(3000)
            movieDetailsScreenInteractionListener.onHideSnackBar()
        }
    }
    val isCollapsed by remember {
        derivedStateOf {
            scrollState.firstVisibleItemScrollOffset > 50 || scrollState.firstVisibleItemIndex > 0
        }
    }
    LaunchedEffect(isCollapsed) {
        if (isCollapsed && scrollState.layoutInfo.totalItemsCount > 0) {
            scrollState.animateScrollToItem(index = scrollState.layoutInfo.totalItemsCount - 1)
        }
    }


    if (state.showRatingDialog) {
        RatingDialog(
            currentRating = currentRating,
            onRatingChange = { newRating ->
                currentRating = newRating
            },
            onDismiss = { movieDetailsScreenInteractionListener.onDismissRatingDialog() },
            onSubmit = {
                movieDetailsScreenInteractionListener.onRatingSubmitted(
                    movieId = state.movieDetailsUiState.movie.id,
                    rating = currentRating
                )
            }
        )
    }

    if (state.showAddToListDialog) {
        AddToListDialog(
            lists = state.availableLists,
            selectedIndex = state.selectedListIndex,
            onDismiss = { movieDetailsScreenInteractionListener.onDismissAddToListDialog() },
            onListSelectionChanged = { index ->
                movieDetailsScreenInteractionListener.onListSelectionChanged(index)
            },
            onAddToSelectedList = {
                movieDetailsScreenInteractionListener.onAddToSelectedList()
            },
            onCreateNewList = {
                movieDetailsScreenInteractionListener.onCreateListShow()
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(Theme.colors.surface)
                .navigationBarsPadding()
        ) {
            when {
                state.isLoading -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        AppTopBar(
                            leadingIcons = listOf(
                                iconItemWithDefaults(
                                    icon = ImageVector.vectorResource(RDesignSystem.drawable.ic_back),
                                    onClick = { activity?.finish() }
                                )
                            )
                        )
                        PageLoadingPlaceHolder(
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                state.movieDetailsUiState.movie.title.isEmpty() -> {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        AppTopBar(
                            leadingIcons = listOf(
                                iconItemWithDefaults(
                                    icon = ImageVector.vectorResource(RDesignSystem.drawable.ic_back),
                                    onClick = { activity?.finish() }
                                )
                            )
                        )
                        PlaceholderView(
                            modifier = Modifier.fillMaxSize(),
                            image = painterResource(RDesignSystem.drawable.ic_network_error),
                            title = stringResource(R.string.no_movie_details),
                            subTitle = stringResource(R.string.movie_details_not_available),
                            spacer = 16.dp
                        )
                    }
                }

                else -> {
                    if (state.movieDetailsUiState.isYoutubePlayerVisible && !state.movieDetailsUiState.youtubeVideoKey.isNullOrEmpty()) {
                        VideoPlayer(
                            videoKey = state.movieDetailsUiState.youtubeVideoKey,
                            onCloseClick = { movieDetailsScreenInteractionListener.closeYoutubePlayer() }
                        )
                    } else {
                        SharedTransitionLayout {
                            AnimatedContent(
                                targetState = isCollapsed,
                                label = "basic_transition"
                            ) { target ->
                                if (!target) {
                                    MovieTopComponentDetails(
                                        state = state,
                                        movieDetailsScreenInteractionListener = movieDetailsScreenInteractionListener,
                                        animatedVisibilityScope = this@AnimatedContent,
                                        sharedTransitionScope = this@SharedTransitionLayout,
                                        listState = scrollState
                                    )
                                } else {
                                    MovieTopComponent(
                                        movieDetailsScreenInteractionListener = movieDetailsScreenInteractionListener,
                                        animatedVisibilityScope = this@AnimatedContent,
                                        sharedTransitionScope = this@SharedTransitionLayout,
                                        title = state.movieDetailsUiState.movie.title,
                                        state = state
                                    )
                                }

                            }
                        }
                    }

                    LazyVerticalGrid (
                        state = scrollState,
                        columns = GridCells.Adaptive(150.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .navigationBarsPadding(),
                    ) {
                        if (state.isDescriptionLoading) {
                            item (span = {GridItemSpan(maxLineSpan)}){
                                PageLoadingPlaceHolder(
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        } else if (hasDescriptionContent(state.movieDetailsUiState.movie)) {
                            item (span = {GridItemSpan(maxLineSpan)}){
                                DescriptionSection(
                                    title = state.movieDetailsUiState.movie.title,
                                    genres = state.movieDetailsUiState.movie.genres,
                                    releaseDate = state.movieDetailsUiState.movie.releaseDate,
                                    runtime = state.movieDetailsUiState.movie.runtime,
                                    country = state.movieDetailsUiState.movie.country,
                                    description = state.movieDetailsUiState.movie.description
                                )
                            }
                        }

                        if (state.isCastLoading) {
                            item (span = {GridItemSpan(maxLineSpan)}){
                                PageLoadingPlaceHolder(
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        } else if (state.movieDetailsUiState.cast.isNotEmpty()) {
                            item (span = {GridItemSpan(maxLineSpan)}){
                                CastSection(
                                    castList = state.movieDetailsUiState.cast,
                                    onSeeAllClick = {
                                        movieDetailsScreenInteractionListener.onShowAllCastClick(
                                            state.movieDetailsUiState.movie.id
                                        )
                                    }
                                )
                            }
                        }

                        item (span = {GridItemSpan(maxLineSpan)}){
                            ChipsRowSection(
                                items = movieChips.map { chip ->
                                    stringResource(chip.titleResId) to chip.iconResId
                                },
                                selectedIndex = selectedIndex.intValue,
                                onItemSelected = { selectedIndex.intValue = it }
                            )
                        }

                        selectedIndex.intValue.let { index ->
                            when (movieChips[index]) {
                                MovieChips.MORE_LIKE_THIS ->
                                    if (state.isRecommendationsLoading) {
                                        item (span = {GridItemSpan(maxLineSpan)}){
                                            PageLoadingPlaceHolder(
                                                modifier = Modifier.padding(16.dp)
                                            )
                                        }
                                    } else if (mediaList.itemSnapshotList.isEmpty()) {
                                        item (span = {GridItemSpan(maxLineSpan)}){
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .background(Theme.colors.surface)
                                                    .padding(vertical = 30.dp)
                                                    .navigationBarsPadding(),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                AppText(
                                                    text = stringResource(R.string.there_is_no_recommendations),
                                                    style = Theme.textStyle.label.large,
                                                    color = Theme.colors.text.body.copy(alpha = 0.6f)
                                                )
                                            }
                                        }
                                    } else {
                                        items(mediaList.itemCount,span = {GridItemSpan(maxLineSpan)}) { mediaIndex ->
                                            mediaList[mediaIndex]?.let { media ->
                                                MediaCard(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(
                                                            start = 16.dp,
                                                            end = 16.dp,
                                                            bottom = 8.dp
                                                        ),
                                                    imageUri = media.posterPath,
                                                    rating = media.voteAverage?.toFloat(),
                                                    movieName = media.title,
                                                    mediaType = stringResource(R.string.movie),
                                                    year = media.releaseDate.take(4),
                                                    mediaCardType = MediaCardType.UP_COMING,
                                                    showGradientFilter = true,
                                                    clickable = true,
                                                    nsfwThreshold = state.nsfwThreshold,
                                                    genderThreshold = state.genderThreshold,
                                                    onClick = {
                                                        movieDetailsScreenInteractionListener.onSimilarMovieClick(
                                                            mediaId = media.id
                                                        )
                                                    },
                                                    cardWidth = null
                                                )
                                            }
                                        }
                                    }

                                MovieChips.REVIEWS ->
                                    if (state.isReviewsLoading) {
                                        item (span = {GridItemSpan(maxLineSpan)}){
                                            PageLoadingPlaceHolder(
                                                modifier = Modifier.padding(16.dp)
                                            )
                                        }
                                    } else if (state.movieDetailsUiState.reviews.isEmpty()) {
                                        item (span = {GridItemSpan(maxLineSpan)}){
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .background(Theme.colors.surface)
                                                    .padding(vertical = 30.dp)
                                                    .navigationBarsPadding(),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                AppText(
                                                    text = stringResource(R.string.there_is_no_reviews),
                                                    style = Theme.textStyle.label.large,
                                                    color = Theme.colors.text.body.copy(alpha = 0.6f)
                                                )
                                            }
                                        }
                                    } else {
                                        items(state.movieDetailsUiState.reviews,span = {GridItemSpan(maxLineSpan)}) { review ->
                                            ReviewsSection(review)
                                        }
                                    }

                                MovieChips.GALLERY -> {
                                    if (state.isGalleryLoading) {
                                        item (span = {GridItemSpan(maxLineSpan)}){
                                            PageLoadingPlaceHolder(
                                                modifier = Modifier.padding(16.dp)
                                            )
                                        }
                                    } else if (state.movieDetailsUiState.gallery.isEmpty()) {
                                        item(span = {GridItemSpan(maxLineSpan)}) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .background(Theme.colors.surface)
                                                    .padding(vertical = 30.dp)
                                                    .navigationBarsPadding(),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                AppText(
                                                    text = stringResource(R.string.there_is_no_gallery),
                                                    style = Theme.textStyle.label.large,
                                                    color = Theme.colors.text.body.copy(alpha = 0.6f)
                                                )
                                            }
                                        }
                                    } else {
                                        gallerySection(state.movieDetailsUiState.gallery)
                                    }
                                }

                                MovieChips.COMPANY_PRODUCTION -> {
                                    if (state.isProductionCompaniesLoading) {
                                       item (span = {GridItemSpan(maxLineSpan)}){
                                           PageLoadingPlaceHolder(
                                               modifier = Modifier.padding(16.dp)
                                           )
                                       }
                                    } else {
                                        productionCompanySection(
                                            companies = state.movieDetailsUiState.movie.productionCompanies,
                                            modifier = Modifier
                                                .padding(horizontal = 16.dp)
                                        )
                                    }
                                }
                            }
                        }
                        item (span = {GridItemSpan(maxLineSpan)}){
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(LocalConfiguration.current.screenHeightDp.dp / 12)
                            )
                        }
                    }
                }
            }
        }

        CreateListDialog(
            onDismiss = movieDetailsScreenInteractionListener::onCreateListDismiss,
            onAddClicked = movieDetailsScreenInteractionListener::onCreateListConfirm,
            onListNameValueChange = movieDetailsScreenInteractionListener::onCreateListNameChange,
            buttonState = state.createListButtonState,
            listName = state.createListName,
            showDialog = state.showCreateListDialog
        )

        AnimatedVisibility(
            visible = state.showSnackBar,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            AppSnackBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(start = 12.dp, end = 12.dp, top = 16.dp)
                    .align(Alignment.TopCenter),
                text = state.snackBarMessage ?: RDesignSystem.string.empty,
                isSuccess = state.snackBarSuccess,
                onClick = {
                    movieDetailsScreenInteractionListener.onHideSnackBar()
                }
            )
        }
    }
}