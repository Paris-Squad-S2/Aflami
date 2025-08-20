package com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details

import androidx.annotation.StringRes
import androidx.paging.PagingData
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.MediaUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.SimilarMediaUI
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.CastUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.ProductionCompanyUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.ReviewUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class TvShowDetailsScreenState(
    val tvShowDetailsUiState: TvShowDetailsUiState = TvShowDetailsUiState(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isEpisodesLoading: Boolean = false,
    val isImageLoading: Boolean = false,
    val isDescriptionLoading: Boolean = false,
    val isCastLoading: Boolean = false,
    val isSeasonsLoading: Boolean = false,
    val isRecommendationsLoading: Boolean = false,
    val isReviewsLoading: Boolean = false,
    val isGalleryLoading: Boolean = false,
    val isProductionCompaniesLoading: Boolean = false,
    val seasonsLoadingStates: Map<Int, Boolean> = emptyMap(),
    val showRatingDialog: Boolean = false,
    @StringRes val snackBarMessage: Int? = null,
    val showSnackBar: Boolean = false,
    val snackBarSuccess: Boolean = false,
    val contentRestriction: ContentRestriction = ContentRestriction.Strict,
    val nsfwThreshold: Float = 0.8f,
    val genderThreshold: Float = 0.6f
)

enum class ContentRestriction() {
    Strict,
    Moderate,
    Off
}

data class TvShowDetailsUiState(
    val tvShowUi: TvShowUi = TvShowUi(),
    val recommendations: Flow<PagingData<SimilarMediaUI>> = emptyFlow(),
    val cast: List<CastUi> = emptyList(),
    val reviews:List<ReviewUi> = emptyList(),
    val gallery: List<String> = emptyList(),
    val tvShowVideoUi: TvShowVideoUi = TvShowVideoUi(),
    val selectedRating: Float = 0f,
    val episodeVideoUi: EpisodeVideoUi = EpisodeVideoUi(),
    val isYoutubePlayerVisible: Boolean = false,
    val youtubeVideoKey: String? = null,
)

data class TvShowVideoUi(
    val key: String = "",
    val name: String = "",
    val site: String = "",
)

data class TvShowUi(
    val id: Int = 0,
    override val posterUrl: String = "",
    override val rating: Float? = null,
    override val title: String = "",
    val genres: List<Int> = emptyList(),
    override val releaseDate: String = "",
    val runtime: String = "",
    val country: String = "",
    val description: String = "",
    val seasons: List<SeasonUi> = emptyList(),
    val productionCompanies: List<ProductionCompanyUi> = emptyList(),
    val isRated:Boolean = false,
    ) : MediaUi

data class SeasonUi(
    val id: Int = 0,
    val name: String = "",
    val episodeCount: Int = 0,
    val seasonNumber: Int = 0,
    val isExpanded: Boolean = false,
    val episodes: List<EpisodeUi> = emptyList(),
)


data class EpisodeUi(
    val episodeNumber: Int = 0,
    val posterUrl: String = "",
    val voteAverage: Double? = null,
    val airDate: String = "",
    val runtime: String = "",
    val description: String = "",
    val episodePhotoUrl: String = "",
)

data class EpisodeVideoUi(
    val key: String = "",
    val name: String = "",
    val site: String = "",
)
