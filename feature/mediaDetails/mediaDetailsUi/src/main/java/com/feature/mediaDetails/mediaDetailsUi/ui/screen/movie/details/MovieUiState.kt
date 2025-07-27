package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details

import androidx.paging.PagingData
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.MediaUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.SimilarMediaUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class MovieDetailsScreenState(
    val movieDetailsUiState: MovieDetailsUiState = MovieDetailsUiState(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val isImageLoading: Boolean = false,
    val isDescriptionLoading: Boolean = false,
    val isCastLoading: Boolean = false,
    val isRecommendationsLoading: Boolean = false,
    val isReviewsLoading: Boolean = false,
    val isGalleryLoading: Boolean = false,
    val isProductionCompaniesLoading: Boolean = false,
)

data class MovieDetailsUiState(
    val movie: MovieUi = MovieUi(),
    val recommendations: Flow<PagingData<SimilarMediaUI>> = flowOf(PagingData.empty()),
    val cast: List<CastUi> = emptyList(),
    val reviews: Flow<PagingData<ReviewUi>> = flowOf(PagingData.empty()),
    val gallery: List<String> = emptyList(),
    val movieVideoUi: MovieVideoUi = MovieVideoUi(),
)

data class MovieVideoUi(
    val key: String = "",
    val name: String = "",
    val site: String = "",
)

data class MovieUi(
    val id: Int = 0,
    override val posterUrl: String = "",
    override val rating: Float = 0f,
    override val title: String = "",
    val genres: List<String> = emptyList(),
    override val releaseDate: String = "",
    val runtime: String = "",
    val country: String = "",
    val description: String = "",
    val productionCompanies: List<ProductionCompanyUi> = emptyList(),
) : MediaUi

data class ProductionCompanyUi(
    val logoUrl: String,
    val name: String,
    val originCountry: String,
)

data class CastUi(
    val name: String,
    val imageUrl: String,
)

data class ReviewUi(
    val avatarUrl: String,
    val username: String,
    val name: String,
    val rating: Double,
    val createdAt: String,
    val description: String,
)

