package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details

import com.feature.mediaDetails.mediaDetailsUi.ui.screen.MediaUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.SimilarMediaUI

data class MovieDetailsScreenState(
    val movieDetailsUiState: MovieDetailsUiState,
    val isLoading: Boolean,
    val errorMessage: String?
)

data class MovieDetailsUiState(
    val movie: MovieUi,
    val recommendations: List<SimilarMediaUI>,
    val cast: List<CastUi>,
    val reviews: List<ReviewUi>,
    val gallery: List<String>
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
    val productionCompanies: List<ProductionCompanyUi>
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
)

