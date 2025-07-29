package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details

import androidx.annotation.StringRes
import androidx.paging.PagingData
import com.feature.mediaDetails.mediaDetailsUi.R
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.MediaUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.SimilarMediaUI
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
    @StringRes val snackBarMessage: Int = R.string.failed_to_submit_rating,
    val showSnackBar: Boolean = false,
    val snackBarSuccess: Boolean = false
)

data class MovieDetailsUiState(
    val movie: MovieUi,
    val recommendations: Flow<PagingData<SimilarMediaUI>>,
    val cast: List<CastUi>,
    val reviews: Flow<PagingData<ReviewUi>>,
    val gallery: List<String>,
    val movieVideoUi: MovieVideoUi,
    val selectedRating: Float
)

data class MovieVideoUi(
    val key: String,
    val name: String,
    val site: String,
)

data class MovieUi(
    val id :Int,
    override val posterUrl: String,
    override val rating: Float,
    override val title: String,
    val genres: List<String>,
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
    val rating: Double,
    val createdAt : String,
    val description: String
)

