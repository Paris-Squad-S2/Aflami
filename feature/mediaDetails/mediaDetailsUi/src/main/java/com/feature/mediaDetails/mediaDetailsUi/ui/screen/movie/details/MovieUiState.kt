package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details

import androidx.annotation.StringRes
import androidx.paging.PagingData
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.MediaUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.SimilarMediaUI
import com.paris_2.aflami.designsystem.components.ButtonState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class MovieDetailsScreenState(
    val movieDetailsUiState: MovieDetailsUiState = MovieDetailsUiState(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isImageLoading: Boolean = false,
    val isDescriptionLoading: Boolean = false,
    val isCastLoading: Boolean = false,
    val isRecommendationsLoading: Boolean = false,
    val isReviewsLoading: Boolean = false,
    val isGalleryLoading: Boolean = false,
    val isProductionCompaniesLoading: Boolean = false,
    val showRatingDialog: Boolean = false,
    @StringRes val snackBarMessage: Int? = null,
    val showSnackBar: Boolean = false,
    val snackBarSuccess: Boolean = false,
    val showAddToListDialog: Boolean = false,
    val availableLists: List<ListItemUi> = emptyList(),
    val selectedListIndex: Int = -1,
    val showCreateListDialog: Boolean = false,
    val createListName: String = "",
    val createListButtonState: ButtonState = ButtonState.Normal,
    val contentRestriction: ContentRestriction = ContentRestriction.Strict,
    val nsfwThreshold: Float = 0.8f,
    val genderThreshold: Float = 0.6f
)

enum class ContentRestriction() {
    Strict,
    Moderate,
    Off
}

data class MovieDetailsUiState(
    val movie: MovieUi = MovieUi(),
    val recommendations: Flow<PagingData<SimilarMediaUI>> = emptyFlow(),
    val cast: List<CastUi> = emptyList(),
    val reviews: List<ReviewUi> = emptyList(),
    val gallery: List<String> = emptyList(),
    val movieVideoUi: MovieVideoUi = MovieVideoUi(),
    val selectedRating: Float = 0f,
    val isYoutubePlayerVisible: Boolean = false,
    val youtubeVideoKey: String? = null
)

data class MovieVideoUi(
    val key: String = "",
    val name: String = "",
    val site: String = "",
)

data class MovieUi(
    val id :Int = 0,
    override val posterUrl: String = "",
    override val rating: Float? = 0f,
    override val title: String = "",
    val genres: List<Int> = emptyList(),
    override val releaseDate: String = "",
    val runtime: String = "",
    val country: String = "",
    val description: String = "",
    val productionCompanies: List<ProductionCompanyUi> = emptyList(),
): MediaUi

data class ProductionCompanyUi(
    val logoUrl: String = "",
    val name: String = "",
    val originCountry: String = ""
)

data class CastUi(
    val name: String = "",
    val imageUrl: String = ""
)

data class ReviewUi(
    val avatarUrl: String = "",
    val username: String = "",
    val name: String = "",
    val rating: Double? = null,
    val createdAt : String = "",
    val description: String = ""
)

data class ListItemUi(
    val id: String = "",
    val name: String = "",
    val itemCount: Int = 0
)