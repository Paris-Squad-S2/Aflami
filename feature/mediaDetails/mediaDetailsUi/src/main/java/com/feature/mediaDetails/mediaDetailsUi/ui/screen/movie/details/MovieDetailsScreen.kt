package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details

import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.feature.mediaDetails.mediaDetailsUi.R
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.AddToListDialog
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.ChipsRowSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.GallerySection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.MovieTopComponent
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.MovieTopComponentDetails
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.RatingDialog
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.castSection.CastSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.companyProductionSection.ProductionCompanySection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.descriptionSection.DescriptionSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.reviewSection.ReviewsSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.hasDescriptionContent
import com.paris_2.aflami.designsystem.components.MediaCard
import com.paris_2.aflami.designsystem.components.MediaCardType
import com.paris_2.aflami.designsystem.components.PageLoadingPlaceHolder
import com.paris_2.aflami.designsystem.components.PlaceholderView
import com.paris_2.aflami.designsystem.components.TopAppBar
import com.paris_2.aflami.designsystem.components.iconItemWithDefaults
import com.paris_2.aflami.designsystem.theme.Theme
import kotlinx.coroutines.flow.emptyFlow
import com.paris_2.aflami.designsystem.R as RDesignSystem

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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun MovieDetailsScreenContent(
    state: MovieDetailsScreenState,
    movieDetailsScreenInteractionListener: MovieDetailsScreenInteractionListener,
) {
    val movieChips = MovieChips.entries
    val activity = LocalActivity.current
    var currentRating by remember { mutableFloatStateOf(state.movieDetailsUiState.selectedRating) }

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
            list = listOf("My Favorite Movies", "Kittens"),
            onDismiss = { movieDetailsScreenInteractionListener.onDismissAddToListDialog() },
        )
    }

    val defaultIndex = movieChips.indexOf(MovieChips.REVIEWS)
    val selectedIndex = rememberSaveable { mutableIntStateOf(defaultIndex) }
    val reviewsList = state.movieDetailsUiState.reviews.collectAsLazyPagingItems()

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
                val scrollState = rememberLazyListState()
                val isCollapsed by remember {
                    derivedStateOf {
                        scrollState.firstVisibleItemScrollOffset > 50 || scrollState.firstVisibleItemIndex > 0
                    }
                }
                val mediaList =
                    state.movieDetailsUiState.recommendations.collectAsLazyPagingItems()
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
                            )
                        }
                        else {
                            MovieTopComponent(
                                movieDetailsScreenInteractionListener = movieDetailsScreenInteractionListener,
                                animatedVisibilityScope = this@AnimatedContent,
                                sharedTransitionScope = this@SharedTransitionLayout,
                                title = state.movieDetailsUiState.movie.title,
                            )
                        }

                    }
                }
                LazyColumn(
                    state = scrollState,
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding()
                ) {
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
                                                rating = media.voteAverage?.toFloat(),
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
            }
        }
//        AnimatedVisibility(
//            visible = state.showSnackBar,
//            enter = fadeIn() + slideInVertically(),
//            exit = fadeOut() + slideOutVertically()
//        ) {
//            SnackBar(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .statusBarsPadding()
//                    .padding(start = 12.dp, end = 12.dp, top = 16.dp)
//                    .align(Alignment.TopCenter),
//                text = state.snackBarMessage ?: RDesignSystem.string.empty,
//                isSuccess = state.snackBarSuccess,
//                onClick = {
//                    movieDetailsScreenInteractionListener.onHideSnackBar()
//                }
//            )
//        }
    }
}
@Preview(showSystemUi = true)
@Composable
fun PreviewMovieDetailsScreen() {
    val fakeMovieDetailsUiState = MovieDetailsUiState(
        movie = MovieUi(
            id = 1,
            posterUrl = "",
            rating = 8.5f,
            title = "Stranger Things",
            genres = listOf("Drama", "Sci-Fi", "Horror"),
            releaseDate = "2016-07-15",
            runtime = "50 min",
            country = "USA",
            description = "When a young boy vanishes, a small town uncovers a mystery involving secret experiments, terrifying supernatural forces and one strange little girl.",
            productionCompanies = listOf(
                ProductionCompanyUi("Netflix", "", ""),
                ProductionCompanyUi("21 Laps", "", "")
            ),
        ),
        recommendations = emptyFlow(),
        cast = emptyList(),
        reviews = emptyFlow(),
        gallery = listOf(),
        selectedRating = 0f,
        movieVideoUi = MovieVideoUi(
            key = "key",
            name = "name",
            site = "site"
        )
    )

    MovieDetailsScreenContent(
        state = MovieDetailsScreenState(
            movieDetailsUiState = fakeMovieDetailsUiState,
            isLoading = false,
            errorMessage = null,
            isImageLoading = false,
            isDescriptionLoading = false,
            isCastLoading = true,
            isRecommendationsLoading = true,
            isReviewsLoading = false,
            isGalleryLoading = false,
            isProductionCompaniesLoading = false,
            showRatingDialog = false,
            showSnackBar = false
        ),
        movieDetailsScreenInteractionListener = object : MovieDetailsScreenInteractionListener {
            override fun onRateClick() {
            }

            override fun onAddToListClick() {
            }

            override fun onDismissAddToListDialog() {
            }

            override fun onShowAllCastClick(tvShowId: Int) {}
            override fun onRetryLoadMovieDetails() {
            }

            override fun onSimilarMovieClick(mediaId: Int) {
            }

            override fun onDismissRatingDialog() {}
            override fun onRatingSubmitted(movieId: Int, rating: Float) {
            }

            override fun onHideSnackBar() {}
        }
    )
}