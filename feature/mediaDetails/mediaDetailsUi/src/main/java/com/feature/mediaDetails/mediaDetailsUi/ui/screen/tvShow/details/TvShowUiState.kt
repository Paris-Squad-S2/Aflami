package com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details

import com.feature.mediaDetails.mediaDetailsUi.ui.screen.MediaUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.CastUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.ProductionCompanyUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.ReviewUi

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
    val id :Int,
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