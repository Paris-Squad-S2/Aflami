package com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.BaseViewModel

class TvShowDetailsViewModelViewModel(
    savedStateHandle: SavedStateHandle,
) :
    BaseViewModel<TvShowDetailsScreenState>(
        TvShowDetailsScreenState(
            TvShowDetailsUiState(
                tvShowUi = TvShowUi(
                    posterUrl = "",
                    rating = "",
                    title = "",
                    genres = emptyList(),
                    releaseDate = "",
                    runtime = "",
                    country = "",
                    description = "",
                    productionCompanies = emptyList()
                ),
                cast = emptyList(),
                reviews = emptyList(),
                gallery = emptyList(),
                seasons = emptyList()
            ),
            isLoading = false,
            errorMessage = null
        )
    ) {

    init {
        val mediaId =
            savedStateHandle.toRoute<MediaDetailsDestinations.TvShowDetailsScreen>().tvShowId
        emitState(
            TvShowDetailsScreenState(
                TvShowDetailsUiState(
                    tvShowUi = TvShowUi(
                        posterUrl = "",
                        rating = "",
                        title = "",
                        genres = emptyList(),
                        releaseDate = "",
                        runtime = "",
                        country = "",
                        description = "TvShow ID: $mediaId",
                        productionCompanies = emptyList()
                    ),
                    cast = emptyList(),
                    reviews = emptyList(),
                    gallery = emptyList(),
                    seasons = emptyList()
                ),
                isLoading = false,
                errorMessage = null
            )
        )
    }

    fun onNavigate() {
        navigate(
            MediaDetailsDestinations.MovieDetailsScreen(
                movieId = 123
            )
        )
    }
}