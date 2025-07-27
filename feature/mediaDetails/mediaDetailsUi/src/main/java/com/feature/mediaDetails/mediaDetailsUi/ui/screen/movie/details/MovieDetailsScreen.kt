package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.feature.mediaDetails.mediaDetailsUi.R
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.ChipsRowSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.GallerySection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.RatingDialog
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.castSection.CastSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.companyProductionSection.ProductionCompanySection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.descriptionSection.DescriptionSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.detailsImage.DetailsImage
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.reviewSection.ReviewsSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.hasDescriptionContent
import com.paris_2.aflami.designsystem.components.MediaCard
import com.paris_2.aflami.designsystem.components.MediaCardType
import com.paris_2.aflami.designsystem.components.PageLoadingPlaceHolder
import com.paris_2.aflami.designsystem.components.PlaceholderView
import com.paris_2.aflami.designsystem.components.TopAppBar
import com.paris_2.aflami.designsystem.components.iconItemWithDefaults
import com.paris_2.aflami.designsystem.theme.Theme
import org.koin.compose.viewmodel.koinViewModel
import com.paris_2.aflami.designsystem.R as RDesignSystem

@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailsViewModel = koinViewModel(),
) {
    val state = viewModel.screenState.collectAsStateWithLifecycle()
    MovieDetailsScreenContent(
        state = state.value,
        movieDetailsScreenInteractionListener = viewModel
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreenContent(
    state: MovieDetailsScreenState,
    movieDetailsScreenInteractionListener: MovieDetailsScreenInteractionListener,
) {
    val movieChips = MovieChips.entries
    val listState = rememberLazyListState()
    val density = LocalDensity.current
    val activity = LocalActivity.current
    val maxScrollPx = with(density) { 56.dp.toPx() }
    var currentRating by remember { mutableFloatStateOf(state.movieDetailsUiState.selectedRating) }
    val alpha by remember {
        derivedStateOf {
            val scroll =
                if (listState.firstVisibleItemIndex > 0) maxScrollPx else listState.firstVisibleItemScrollOffset.toFloat()
            (scroll / maxScrollPx).coerceIn(0f, 1f)
        }
    }
    if (state.showRatingDialog) {
        RatingDialog(
            currentRating = currentRating,
            onRatingChange = { newRating ->
                currentRating = newRating },
            onDismiss = { movieDetailsScreenInteractionListener.onDismissRatingDialog() },
            onSubmit = { movieDetailsScreenInteractionListener.onDismissRatingDialog()  }
        )
    }

    val backgroundColor = Theme.colors.surface.copy(alpha = alpha)
    val defaultIndex = movieChips.indexOf(MovieChips.REVIEWS)
    val selectedIndex = rememberSaveable { mutableIntStateOf(defaultIndex) }
    val reviewsList = state.movieDetailsUiState.reviews.collectAsLazyPagingItems()

    Box(
        Modifier
            .fillMaxSize()
            .background(Theme.colors.surface)
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        when {
            state.isLoading -> {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    TopAppBar(
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
                    TopAppBar(
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
                val mediaList =
                    state.movieDetailsUiState.recommendations.collectAsLazyPagingItems()
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding()
                ) {
                    item {
                        if (state.isImageLoading) {
                            PageLoadingPlaceHolder(
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                        } else {
                            DetailsImage(
                                imageUris = listOf(state.movieDetailsUiState.movie.posterUrl) + state.movieDetailsUiState.gallery,
                                rating = state.movieDetailsUiState.movie.rating,
                                onPlayClick = movieDetailsScreenInteractionListener::onClickPlayTrailer,
                                hasVideo = !(state.movieDetailsUiState.movieVideoUi.site.isEmpty() ||
                                        state.movieDetailsUiState.movieVideoUi.key.isEmpty()),
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                        }
                    }

                    if (state.isDescriptionLoading) {
                        item {
                            PageLoadingPlaceHolder(
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    } else if (hasDescriptionContent(state.movieDetailsUiState.movie)) {
                        item {
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
                        item {
                            PageLoadingPlaceHolder(
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    } else if (state.movieDetailsUiState.cast.isNotEmpty()) {
                        item {
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

                    item {
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
                                    item {
                                        PageLoadingPlaceHolder(
                                            modifier = Modifier.padding(16.dp)
                                        )
                                    }
                                } else if (mediaList.itemSnapshotList.isEmpty()) {
                                    item {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(Theme.colors.surface)
                                                .padding(vertical = 30.dp)
                                                .navigationBarsPadding(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = stringResource(R.string.there_is_no_recommendations),
                                                style = Theme.textStyle.label.large,
                                                color = Theme.colors.text.body.copy(alpha = 0.6f)
                                            )
                                        }
                                    }
                                } else {
                                    items(mediaList.itemCount) { mediaIndex ->
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
                                                rating = media.voteAverage.toFloat(),
                                                movieName = media.title,
                                                mediaType = stringResource(R.string.movie),
                                                year = media.releaseDate.take(4),
                                                mediaCardType = MediaCardType.UP_COMING,
                                                showGradientFilter = true,
                                                clickable = true,
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
                                    item {
                                        PageLoadingPlaceHolder(
                                            modifier = Modifier.padding(16.dp)
                                        )
                                    }
                                } else if (reviewsList.itemSnapshotList.isEmpty()) {
                                    item {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(Theme.colors.surface)
                                                .padding(vertical = 30.dp)
                                                .navigationBarsPadding(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = stringResource(R.string.there_is_no_reviews),
                                                style = Theme.textStyle.label.large,
                                                color = Theme.colors.text.body.copy(alpha = 0.6f)
                                            )
                                        }
                                    }
                                } else {
                                    items(reviewsList.itemCount) { index ->
                                        ReviewsSection(reviewsList[index])
                                    }
                                }

                            MovieChips.GALLERY -> item {
                                if (state.isGalleryLoading) {
                                    PageLoadingPlaceHolder(
                                        modifier = Modifier.padding(16.dp)
                                    )
                                } else if (state.movieDetailsUiState.gallery.isEmpty()) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Theme.colors.surface)
                                            .padding(vertical = 30.dp)
                                            .navigationBarsPadding(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = stringResource(R.string.there_is_no_gallery),
                                            style = Theme.textStyle.label.large,
                                            color = Theme.colors.text.body.copy(alpha = 0.6f)
                                        )
                                    }
                                } else {
                                    GallerySection(state.movieDetailsUiState.gallery)
                                }
                            }

                            MovieChips.COMPANY_PRODUCTION -> item {
                                if (state.isProductionCompaniesLoading) {
                                    PageLoadingPlaceHolder(
                                        modifier = Modifier.padding(16.dp)
                                    )
                                } else {
                                    ProductionCompanySection(
                                        companies = state.movieDetailsUiState.movie.productionCompanies,
                                        modifier = Modifier
                                            .padding(horizontal = 16.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                TopAppBar(
                    leadingIcons = listOf(
                        iconItemWithDefaults(
                            icon = ImageVector.vectorResource(RDesignSystem.drawable.ic_back),
                            onClick = { activity?.finish() }
                        )
                    ),
                    trailingIcons = listOf(
                        iconItemWithDefaults(
                            icon = ImageVector.vectorResource(RDesignSystem.drawable.ic_star),
                            onClick = {
                                movieDetailsScreenInteractionListener.onFavouriteClick(R.string.rate) // when click on this should open rating dialog
                            }
                        ),
                        iconItemWithDefaults(
                            icon = ImageVector.vectorResource(RDesignSystem.drawable.ic_heart_add),
                            onClick = {
                                movieDetailsScreenInteractionListener.onAddToListClick(R.string.add_to_list)
                            }
                        )
                    ),
                    modifier = Modifier.background(backgroundColor)
                )
            }
        }
    }
}