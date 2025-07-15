package com.feature.mediaDetails.mediaDetailsUi.screen.tvShow

import com.domain.mediaDetails.model.Image
import com.domain.mediaDetails.useCases.tvShows.GetSeasonsUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowCastUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowDetailsUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowMediaUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowReviewsUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowsProductionCompaniesUseCase
import com.feature.mediaDetails.mediaDetailsUi.screen.movie.CastUi
import com.feature.mediaDetails.mediaDetailsUi.screen.movie.ProductionCompanyUi
import com.feature.mediaDetails.mediaDetailsUi.screen.movie.ReviewUi

data class TvShowDetailsScreenState(
    val tvShowDetailsUiState : TvShowDetailsUiState,
    val isLoading: Boolean,
    val errorMessage: String?
)

data class TvShowDetailsUiState(
    val tvShowUi: TvShowUi,
    val cast: List<CastUi>,
    val reviews: List<ReviewUi>,
    val gallery: List<Image>,
)

data class TvShowUi(
    val posterUrl: String,
    val rating: String,
    val title: String,
    val genres: List<String>,
    val releaseDate: String,
    val runtime: String,
    val country: String,
    val description: String,
    val productionCompanies: List<ProductionCompanyUi>
)

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



class TvShowViewModel(
    private val getSeasonsUseCase : GetSeasonsUseCase,
    private val getTvShowCastUseCase: GetTvShowCastUseCase,
    private val getTvShowDetailsUseCase: GetTvShowDetailsUseCase,
    private val getTvShowMediaUseCase : GetTvShowMediaUseCase,
    private val getTvShowReviewsUseCase : GetTvShowReviewsUseCase,
    private val getTvShowsProductionCompaniesUseCase : GetTvShowsProductionCompaniesUseCase
)