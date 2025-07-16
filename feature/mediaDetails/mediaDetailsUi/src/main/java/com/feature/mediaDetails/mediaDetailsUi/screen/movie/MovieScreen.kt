package com.feature.mediaDetails.mediaDetailsUi.screen.movie

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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.feature.mediaDetails.mediaDetailsUi.common.components.ChipsRowSection
import com.feature.mediaDetails.mediaDetailsUi.common.components.DescriptionSection
import com.feature.mediaDetails.mediaDetailsUi.common.components.cast.CastSection
import com.feature.mediaDetails.mediaDetailsUi.common.components.detailsImage.DetailsImage
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.components.TopAppBar
import com.paris_2.aflami.designsystem.components.iconItemWithDefaults
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme
import com.paris_2.aflami.designsystem.R as RDesignSystem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreenContent(
    state: MovieDetailsScreenState,
    //  movieScreenInteractionListener: MovieScreenInteractionListener
) {
    val selectedIndex = rememberSaveable { mutableStateOf<Int?>(null) }
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
                    onSeeAllClick = {}
                )
            }
            item {
                ChipsRowSection(
                    items =listOf(
                        "More like this" to R.drawable.ic_camera_video,
                        "Reviews" to R.drawable.ic_starr,
                        "Gallery" to R.drawable.ic_album,
                        "Production" to R.drawable.ic_city
                    ),
                    selectedIndex = selectedIndex.value,
                    onItemSelected = { selectedIndex.value = it }
                )
            }
        }

        TopAppBar(
            leadingIcons = listOf(
                iconItemWithDefaults(
                    icon = ImageVector.vectorResource(RDesignSystem.drawable.ic_back),
                    onClick = {}
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

@PreviewLightDark
@Composable
fun CompanyProductionCardPreview() {
    AflamiTheme {
        MovieDetailsScreenContent(
            state = fakeMovieDetailsScreenState()
        )
    }
}


fun fakeMovieDetailsScreenState(): MovieDetailsScreenState {
    return MovieDetailsScreenState(
        movieDetailsUiState = MovieDetailsUiState(
            movie = MovieUi(
                posterUrl = "https://xl.movieposterdb.com/12_03/1999/120689/xl_120689_c927b987.jpg",
                rating = "8.5",
                title = "The Green Mile",
                genres = listOf("Drama", "Fantasy"),
                releaseDate = "10-09-1999",
                runtime = "3h 9m",
                country = "USA",
                description = "A gentle giant with a mysterious gift brings hope to a death row prison block.",
                productionCompanies = listOf(
                    ProductionCompanyUi(
                        logoUrl = "https://upload.wikimedia.org/wikipedia/en/thumb/5/5a/Castle_Rock_Entertainment.svg/1200px-Castle_Rock_Entertainment.svg.png",
                        name = "Castle Rock Entertainment",
                        originCountry = "US"
                    )
                )
            ),
            cast = listOf(
                CastUi(
                    name = "Tom Hanks",
                    imageUrl = "https://upload.wikimedia.org/wikipedia/commons/e/e9/Tom_Hanks_TIFF_2019.jpg"
                ),
                CastUi(
                    name = "Michael Clarke Duncan",
                    imageUrl = "https://upload.wikimedia.org/wikipedia/commons/0/04/Michael_Clarke_Duncan_2009.jpg"
                )
            ),
            reviews = listOf(
                ReviewUi(
                    avatarUrl = "https://example.com/avatar1.jpg",
                    username = "johndoe",
                    name = "John Doe",
                    rating = 9.0,
                    createdAt = "10-09-2016"
                ),
                ReviewUi(
                    avatarUrl = "https://example.com/avatar2.jpg",
                    username = "janedoe",
                    name = "Jane Doe",
                    rating = 8.5,
                    createdAt = "15-10-2017"
                )
            ),
            gallery = listOf(
                "https://xl.movieposterdb.com/12_03/1999/120689/xl_120689_c927b987.jpg",
                "https://xl.movieposterdb.com/12_03/1999/120689/xl_120689_c927b987.jpg"
            )
        ),
        isLoading = false,
        errorMessage = null
    )
}
