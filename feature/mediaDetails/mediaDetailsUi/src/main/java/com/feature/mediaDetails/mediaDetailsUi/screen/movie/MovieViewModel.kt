package com.feature.mediaDetails.mediaDetailsUi.screen.movie

import com.domain.mediaDetails.model.Image
import com.domain.mediaDetails.useCases.movie.AddMovieToFavoriteUseCase
import com.domain.mediaDetails.useCases.movie.AddMovieToListUseCase
import com.domain.mediaDetails.useCases.movie.GetMovieCastUseCase
import com.domain.mediaDetails.useCases.movie.GetMovieDetailsUseCase
import com.domain.mediaDetails.useCases.movie.GetMovieMediaUseCase
import com.domain.mediaDetails.useCases.movie.GetMovieReviewsUseCase
import com.domain.mediaDetails.useCases.movie.GetMoviesProductionCompaniesUseCase

data class MovieDetailsScreenState(
    val movieDetailsUiState: MovieDetailsUiState,
    val isLoading: Boolean,
    val errorMessage: String?
)

data class MovieDetailsUiState(
    val movie: MovieUi,
    val cast: List<CastUi>,
    val reviews: List<ReviewUi>,
    val gallery: List<Image>,
)

data class MovieUi(
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
    private val addMovieToListUseCase: AddMovieToListUseCase,
    private val getMoviesProductionCompaniesUseCase: GetMoviesProductionCompaniesUseCase,
    private val addMovieToFavoriteUseCase : AddMovieToFavoriteUseCase,
    private val getMovieReviewsUseCase : GetMovieReviewsUseCase
) {

}