package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.ChipsRowSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.GallerySection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.MoreLikeThisSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.companyProductionSection.ProductionCompanySection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.reviewSection.ReviewsSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.castSection.CastSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.descriptionSection.DescriptionSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.detailsImage.DetailsImage
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.components.TopAppBar
import com.paris_2.aflami.designsystem.components.iconItemWithDefaults
import com.paris_2.aflami.designsystem.theme.Theme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailsViewModelViewModel = koinViewModel()
) {
    val state = viewModel.screenState.collectAsStateWithLifecycle()
    MovieDetailsScreenContent(state = state.value, movieDetailsScreenInteractionListener = viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreenContent(
    state: MovieDetailsScreenState,
    movieDetailsScreenInteractionListener: MovieDetailsScreenInteractionListener
) {
    val selectedIndex = rememberSaveable { mutableStateOf<Int?>(null) }
    val chips = listOf(
        "More like this" to R.drawable.ic_camera_video,
        "Reviews" to R.drawable.ic_starr,
        "Gallery" to R.drawable.ic_album,
        "Company Production" to R.drawable.ic_city
    )
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
                    onPlayClick = {}
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
                    items = chips,
                    selectedIndex = selectedIndex.value,
                    onItemSelected = { selectedIndex.value = it }
                )
            }
            selectedIndex.value?.let { index ->
                when (chips[index].first) {
                    "Reviews" -> item {
                        ReviewsSection(
                            reviews = state.movieDetailsUiState.reviews.takeIf { it.isNotEmpty() }
                        )
                    }
                    "Gallery" -> item {
                        GallerySection(state.movieDetailsUiState.gallery)
                    }
                    "Company Production" -> item {
                        ProductionCompanySection(
                            companies = state.movieDetailsUiState.movie.productionCompanies
                        )
                    }
                    "More like this" -> item {
                        MoreLikeThisSection(
                            mediaList = listOf(
                                state.movieDetailsUiState.movie,
                                state.movieDetailsUiState.movie.copy(title = "Another Movie")
                            ),
                            onClick = {},
                            mediaType = "Movie"
                        )
                    }
                }
            }
        }

        TopAppBar(
            leadingIcons = listOf(
                iconItemWithDefaults(
                    icon = ImageVector.vectorResource(R.drawable.ic_back),
                    onClick = {movieDetailsScreenInteractionListener.onNavigateBack()}
                )
            ),
            trailingIcons = listOf(
                iconItemWithDefaults(
                    icon = ImageVector.vectorResource(R.drawable.ic_star),
                    onClick = {}
                ),
                iconItemWithDefaults(
                    icon = ImageVector.vectorResource(R.drawable.ic_heart_add),
                    onClick = {}
                )
            )
        )
    }
}
