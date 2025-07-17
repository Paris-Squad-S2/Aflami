package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.BaseViewModel
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.MediaUi


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

class MovieDetailsViewModelViewModel(
    savedStateHandle: SavedStateHandle
) :
    BaseViewModel<MovieDetailsScreenState>(
        MovieDetailsScreenState(
            movieDetailsUiState = MovieDetailsUiState(
                movie = MovieUi(
                    posterUrl = "",
                    rating = "",
                    title = "",
                    genres = emptyList(),
                    releaseDate = "",
                    runtime = "",
                    country = "",
                    description = "",
                    productionCompanies = emptyList()
                ),
                cast = emptyList(),
                reviews = emptyList(),
                gallery = emptyList()
            ),
            isLoading = false,
            errorMessage = null
        )
    ) {

    init {
        val mediaId =
            savedStateHandle.toRoute<MediaDetailsDestinations.MovieDetailsScreen>().movieId
        emitState(
            MovieDetailsScreenState(
                movieDetailsUiState = MovieDetailsUiState(
                    movie = MovieUi(
                        posterUrl = "",
                        rating = "",
                        title = "Movie ID: $mediaId",
                        genres = emptyList(),
                        releaseDate = "",
                        runtime = "",
                        country = "",
                        description = "",
                        productionCompanies = emptyList()
                    ),
                    cast = emptyList(),
                    reviews = emptyList(),
                    gallery = emptyList()
                ),
                isLoading = false,
                errorMessage = null
            )
        )
    }

    fun onNavigate() {
        navigate(
            MediaDetailsDestinations.TvShowDetailsScreen(
                tvShowId = 123
            )
        )
    }
}