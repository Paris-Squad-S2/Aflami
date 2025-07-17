package com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.BaseViewModel
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.MediaUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.CastUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.ProductionCompanyUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.ReviewUi

data class TvShowDetailsScreenState(
    val tvShowDetailsUiState : TvShowDetailsUiState,
    val isLoading: Boolean,
    val errorMessage: String?
)
data class TvShowDetailsUiState(
    val tvShowUi: TvShowUi,
    val cast: List<CastUi>,
    val reviews: List<ReviewUi>,
    val gallery: List<String>,
    val seasons: List<SeasonUi>
)

data class TvShowUi(
    override val posterUrl: String,
    override val rating: String,
    override val title: String,
    val genres: List<String>,
    override val releaseDate: String,
    val runtime: String,
    val country: String,
    val description: String,
    val productionCompanies: List<ProductionCompanyUi>
): MediaUi
data class SeasonUi(
    val id: String,
    val name: String,
    val episodes: List<EpisodeUi>
)

data class EpisodeUi(
    val episodeNumber: Int,
    val posterUrl: String,
    val voteAverage: Double,
    val airDate: String,
    val runtime: String,
    val description: String,
    val stillUrl: String
)

class TvShowDetailsViewModelViewModel(
    savedStateHandle: SavedStateHandle
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