package com.feature.mediaDetails.mediaDetailsUi.screen.movie

import com.domain.mediaDetails.useCases.movie.GetMovieCastUseCase
import com.domain.mediaDetails.useCases.movie.GetMovieDetailsUseCase
import com.domain.mediaDetails.useCases.movie.GetMovieMediaUseCase
import com.domain.mediaDetails.useCases.movie.GetMovieReviewsUseCase
import com.domain.mediaDetails.useCases.movie.GetMoviesProductionCompaniesUseCase
import com.feature.mediaDetails.mediaDetailsUi.screen.MediaUi

data class MovieDetailsScreenState(
    val movieDetailsUiState: MovieDetailsUiState,
    val isLoading: Boolean,
    val errorMessage: String?
)

data class MovieDetailsUiState(
    val movie: MovieUi,
    val cast: List<CastUi>,
    val reviews: List<ReviewUi>,
    val gallery: List<String>
)

data class MovieUi(
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

class MovieViewModel(
    private val getMovieMediaUseCase: GetMovieMediaUseCase,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieCastUseCase : GetMovieCastUseCase,
    private val getMoviesProductionCompaniesUseCase: GetMoviesProductionCompaniesUseCase,
    private val getMovieReviewsUseCase : GetMovieReviewsUseCase
) {

}