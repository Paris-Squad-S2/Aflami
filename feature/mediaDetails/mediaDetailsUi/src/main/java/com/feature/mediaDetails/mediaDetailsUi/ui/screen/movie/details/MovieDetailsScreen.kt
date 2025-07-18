package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.ChipsRowSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.GallerySection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.MoreLikeThisSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.castSection.CastSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.companyProductionSection.ProductionCompanySection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.descriptionSection.DescriptionSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.detailsImage.DetailsImage
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.reviewSection.ReviewsSection
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.components.TopAppBar
import com.paris_2.aflami.designsystem.components.iconItemWithDefaults
import com.paris_2.aflami.designsystem.theme.Theme
import org.koin.compose.viewmodel.koinViewModel

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
    val selectedIndex = rememberSaveable { mutableStateOf<Int?>(null) }
    val movieChips = MovieChips.entries

    val rate = stringResource(com.feature.mediaDetails.mediaDetailsUi.R.string.rate)
    val addToList = stringResource(com.feature.mediaDetails.mediaDetailsUi.R.string.add_to_list)

    Box(
        Modifier
            .fillMaxSize()
            .background(Theme.colors.surface)
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
        ) {
            item {
                DetailsImage(
                    imageUris = listOf(
                        state.movieDetailsUiState.movie.posterUrl,
                    ),
                    rating = state.movieDetailsUiState.movie.rating,
                    onPlayClick = {},
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }
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
            item {
                ChipsRowSection(
                    items = movieChips.map { chip ->
                        stringResource(chip.titleResId) to chip.iconResId
                    },
                    selectedIndex = selectedIndex.value,
                    onItemSelected = { selectedIndex.value = it }
                )

            }

            selectedIndex.value?.let { index ->
                when (movieChips[index]) {

                    MovieChips.MORE_LIKE_THIS -> item {
                        MoreLikeThisSection(
                            mediaList = listOf(
                                state.movieDetailsUiState.movie,
                                state.movieDetailsUiState.movie.copy(title = stringResource(com.feature.mediaDetails.mediaDetailsUi.R.string.another_movie))
                            ),
                            onClick = {},
                            mediaType = stringResource(com.feature.mediaDetails.mediaDetailsUi.R.string.movie)
                        )
                    }

                    MovieChips.REVIEWS -> item {
                        ReviewsSection(
                            reviews = state.movieDetailsUiState.reviews.takeIf { it.isNotEmpty() }
                        )
                    }

                    MovieChips.GALLERY -> item {
                        GallerySection(state.movieDetailsUiState.gallery)
                    }

                    MovieChips.COMPANY_PRODUCTION -> item {
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

        TopAppBar(
            leadingIcons = listOf(
                iconItemWithDefaults(
                    icon = ImageVector.vectorResource(R.drawable.ic_back),
                    onClick = { movieDetailsScreenInteractionListener.onNavigateBack() }
                )
            ),
            trailingIcons = listOf(
                iconItemWithDefaults(
                    icon = ImageVector.vectorResource(R.drawable.ic_star),
                    onClick = {
                        movieDetailsScreenInteractionListener.onFavouriteClick(rate)
                    }

                ),
                iconItemWithDefaults(
                    icon = ImageVector.vectorResource(R.drawable.ic_heart_add),
                    onClick = {
                        movieDetailsScreenInteractionListener.onAddToListClick(addToList)
                    }
                )
            )
        )
    }
}