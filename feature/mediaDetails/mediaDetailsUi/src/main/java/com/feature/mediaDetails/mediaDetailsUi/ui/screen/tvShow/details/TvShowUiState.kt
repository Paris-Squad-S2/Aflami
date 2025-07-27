package com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details

import androidx.paging.PagingData
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.MediaUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.SimilarMediaUI
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.CastUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.ProductionCompanyUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.ReviewUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class TvShowDetailsScreenState(
    val tvShowDetailsUiState: TvShowDetailsUiState =TvShowDetailsUiState(),
    val isLoading: Boolean =true,
    val errorMessage: String? = null ,
    val isEpisodesLoading: Boolean =true,
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
    val tvShowUi: TvShowUi = TvShowUi(),
    val recommendations: Flow<PagingData<SimilarMediaUI>> = flowOf(PagingData.empty()),
    val cast: List<CastUi> = emptyList(),
    val reviews: Flow<PagingData<ReviewUi>> = flowOf(PagingData.empty()),
    val gallery: List<String> = emptyList(),
    val tvShowVideoUi: TvShowVideoUi = TvShowVideoUi(),
)

data class TvShowVideoUi(
    val key: String = "",
    val name: String = "",
    val site: String = "",
)

data class TvShowUi(
    val id: Int = 0,
    override val posterUrl: String = "",
    override val rating: Float = 0f,
    override val title: String = "",
    val genres: List<String> = emptyList(),
    override val releaseDate: String = "",
    val runtime: String = "",
    val country: String = "",
    val description: String = "",
    val seasons: List<SeasonUi> = emptyList(),
    val productionCompanies: List<ProductionCompanyUi> = emptyList(),
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
