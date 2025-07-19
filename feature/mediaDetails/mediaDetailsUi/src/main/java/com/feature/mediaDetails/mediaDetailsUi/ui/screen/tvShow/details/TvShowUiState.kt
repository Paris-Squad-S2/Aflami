package com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details

import androidx.paging.PagingData
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.MediaUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.SimilarMediaUI
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.CastUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.ProductionCompanyUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.ReviewUi
import kotlinx.coroutines.flow.Flow

data class TvShowDetailsScreenState(
    val tvShowDetailsUiState: TvShowDetailsUiState,
    val isLoading: Boolean,
    val errorMessage: String?,
    val isEpisodesLoading: Boolean,
    val isImageLoading: Boolean = false,
    val isDescriptionLoading: Boolean = false,
    val isCastLoading: Boolean = false,
    val isSeasonsLoading: Boolean = false,
    val isRecommendationsLoading: Boolean = false,
    val isReviewsLoading: Boolean = false,
    val isGalleryLoading: Boolean = false,
    val isProductionCompaniesLoading: Boolean = false,
    val seasonsLoadingStates: Map<Int, Boolean> = emptyMap(),
)

data class TvShowDetailsUiState(
    val tvShowUi: TvShowUi,
    val recommendations: Flow<PagingData<SimilarMediaUI>>,
    val cast: List<CastUi>,
    val reviews: Flow<PagingData<ReviewUi>>,
    val gallery: List<String>,
)

data class TvShowUi(
    val id: Int,
    override val posterUrl: String,
    override val rating: Float,
    override val title: String,
    val genres: List<String>,
    override val releaseDate: String,
    val runtime: String,
    val country: String,
    val description: String,
    val seasons: List<SeasonUi>,
    val productionCompanies: List<ProductionCompanyUi>,
) : MediaUi

data class SeasonUi(
    val id: Int,
    val name: String,
    val episodeCount: Int,
    val seasonNumber: Int,
    val isExpanded: Boolean = false,
    val episodes: List<EpisodeUi>,
)

data class EpisodeUi(
    val episodeNumber: Int,
    val posterUrl: String,
    val voteAverage: Double,
    val airDate: String,
    val runtime: String,
    val description: String,
    val stillUrl: String,
)
