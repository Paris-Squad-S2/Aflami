package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.castSection.CastSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.companyProductionSection.ProductionCompanySection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.descriptionSection.DescriptionSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.detailsImage.DetailsImage
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.reviewSection.ReviewsSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.hasDescriptionContent
import com.paris_2.aflami.designsystem.components.AflamiMediaCard
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
    viewModel: MovieDetailsViewModelViewModel = koinViewModel(),
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
    val maxScrollPx = with(density) { 56.dp.toPx() }

    val alpha by remember {
        derivedStateOf {
            val scroll =
                if (listState.firstVisibleItemIndex > 0) maxScrollPx else listState.firstVisibleItemScrollOffset.toFloat()
            (scroll / maxScrollPx).coerceIn(0f, 1f)
        }
    }

    val backgroundColor = Theme.colors.surface.copy(alpha = alpha)


    val defaultIndex = movieChips.indexOf(MovieChips.REVIEWS)
    val selectedIndex = rememberSaveable { mutableIntStateOf(defaultIndex) }


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
                                onClick = movieDetailsScreenInteractionListener::onNavigateBack
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
                                onClick = movieDetailsScreenInteractionListener::onNavigateBack
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
                                imageUris = state.movieDetailsUiState.gallery,
                                rating = state.movieDetailsUiState.movie.rating,
                                onPlayClick = {},
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
                                } else {

                                    items(mediaList.itemCount) { mediaIndex ->
                                        mediaList[mediaIndex]?.let { media ->
                                            AflamiMediaCard(
                                                modifier = Modifier.fillMaxWidth(),
                                                imageUri = media.posterPath,
                                                rating = media.voteAverage.toFloat(),
                                                movieName = media.title,
                                                mediaType = stringResource(R.string.movie),
                                                year = media.releaseDate.takeLast(4),
                                                mediaCardType = MediaCardType.UP_COMING,
                                                showGradientFilter = true,
                                                clickable = true,
                                                onClick = { },
                                                cardWidth = null
                                            )
                                        }
                                    }
                                }

                            MovieChips.REVIEWS -> item {
                                if (state.isReviewsLoading) {
                                    PageLoadingPlaceHolder(
                                        modifier = Modifier.padding(16.dp)
                                    )
                                } else {
                                    ReviewsSection(
                                        reviews = state.movieDetailsUiState.reviews.collectAsLazyPagingItems()
                                    )
                                }
                            }

                            MovieChips.GALLERY -> item {
                                if (state.isGalleryLoading) {
                                    PageLoadingPlaceHolder(
                                        modifier = Modifier.padding(16.dp)
                                    )
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
                                            .padding(top = 12.dp)
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
                            onClick = movieDetailsScreenInteractionListener::onNavigateBack
                        )
                    ),
                    trailingIcons = listOf(
                        iconItemWithDefaults(
                            icon = ImageVector.vectorResource(RDesignSystem.drawable.ic_star),
                            onClick = {
                                movieDetailsScreenInteractionListener.onFavouriteClick(R.string.rate)
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