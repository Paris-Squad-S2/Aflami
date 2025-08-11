package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details

import androidx.annotation.StringRes
import androidx.paging.PagingData
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.MediaUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.SimilarMediaUI
import com.paris_2.aflami.designsystem.components.ButtonState
import kotlinx.coroutines.flow.Flow

data class MovieDetailsScreenState(
    val movieDetailsUiState: MovieDetailsUiState,
    val isLoading: Boolean,
    val errorMessage: String?,
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
    val createListButtonState: ButtonState = ButtonState.Normal
)

data class MovieDetailsUiState(
    val movie: MovieUi,
    val recommendations: Flow<PagingData<SimilarMediaUI>>,
    val cast: List<CastUi>,
    val reviews: List<ReviewUi>,
    val gallery: List<String>,
    val movieVideoUi: MovieVideoUi,
    val selectedRating: Float,
    val isYoutubePlayerVisible: Boolean = false,
    val youtubeVideoKey: String? = null
)

data class MovieVideoUi(
    val key: String,
    val name: String,
    val site: String,
)

data class MovieUi(
    val id :Int,
    override val posterUrl: String,
    override val rating: Float?,
    override val title: String,
    val genres: List<Int>,
    override val releaseDate: String,
    val runtime: String,
    val country: String,
    val description: String,
    val productionCompanies: List<ProductionCompanyUi>,
): MediaUi

data class ProductionCompanyUi(
    val logoUrl: String,
    val name: String,
    val originCountry: String
)

data class CastUi(
    val name: String,
    val imageUrl: String
)

data class ReviewUi(
    val avatarUrl: String,
    val username: String,
    val name: String,
    val rating: Double?,
    val createdAt : String,
    val description: String
)

data class ListItemUi(
    val id: String,
    val name: String,
    val itemCount: Int
)