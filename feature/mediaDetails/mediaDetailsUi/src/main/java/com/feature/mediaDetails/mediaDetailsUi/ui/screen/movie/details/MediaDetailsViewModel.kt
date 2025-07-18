package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details


import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.domain.mediaDetails.useCases.movie.AddMovieToFavoriteUseCase
import com.domain.mediaDetails.useCases.movie.GetMovieCastUseCase
import com.domain.mediaDetails.useCases.movie.GetMovieDetailsUseCase
import com.domain.mediaDetails.useCases.movie.GetMovieGalleryUseCase
import com.domain.mediaDetails.useCases.movie.GetMovieRecommendationsUseCase
import com.domain.mediaDetails.useCases.movie.GetMovieReviewsUseCase
import com.domain.mediaDetails.useCases.movie.GetMoviesProductionCompaniesUseCase
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.BaseViewModel
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfCastUi
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfMovieSimilarUI
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfProductionCompanyUi
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfReviewUi
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toUi
import com.paris_2.aflami.appnavigation.AppNavigator

class MovieDetailsViewModelViewModel(
    savedStateHandle: SavedStateHandle,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieCastUseCase: GetMovieCastUseCase,
    private val getMovieGalleryUseCase: GetMovieGalleryUseCase,
    private val getMovieRecommendationsUseCase: GetMovieRecommendationsUseCase,
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase,
    private val getMovieProductionCompaniesUseCase: GetMoviesProductionCompaniesUseCase,
    private val addMovieToFavoriteUseCase: AddMovieToFavoriteUseCase,
    private val appNavigator: AppNavigator
) : MovieDetailsScreenInteractionListener, BaseViewModel<MovieDetailsScreenState>(
    MovieDetailsScreenState(
        movieDetailsUiState = MovieDetailsUiState(
            movie = MovieUi(
                id = 0,
                posterUrl = "",
                rating = 0f,
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
            gallery = emptyList(),
            recommendations = emptyList()
        ),
        isLoading = true,
        errorMessage = null
    )
) {

    init {
        val movieId =
            savedStateHandle.toRoute<MediaDetailsDestinations.MovieDetailsScreen>().movieId
        loadedMovieDetails(movieId)
    }

    private fun loadedMovieDetails(mediaId: Int) {
        tryToExecute(
            execute = { getMovieDetailsUseCase(mediaId) },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        isLoading = false,
                        movieDetailsUiState = screenState.value.movieDetailsUiState.copy(
                            movie = it.toUi(),
                        )
                    )
                )
                loadCastDetails(mediaId)
                loadMovieGallery(mediaId)
                loadMovieRecommendations(mediaId)
                loadMovieReviews(mediaId)
                loadMovieProductionCompanies(mediaId)
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        isLoading = false,
                        errorMessage = it
                    )
                )
            }
        )
    }

    private fun loadMovieProductionCompanies(mediaId: Int) {
        tryToExecute(
            execute = { getMovieProductionCompaniesUseCase(mediaId) },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        movieDetailsUiState = screenState.value.movieDetailsUiState.copy(
                            movie = screenState.value.movieDetailsUiState.movie.copy(
                                productionCompanies = it.toListOfProductionCompanyUi()
                            )
                        )
                    )
                )
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it
                    )
                )
            }
        )
    }

    private fun loadMovieReviews(mediaId: Int) {
        tryToExecute(
            execute = { getMovieReviewsUseCase(mediaId, 1) }, //TODO handle pagination
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        movieDetailsUiState = screenState.value.movieDetailsUiState.copy(
                            reviews = it.toListOfReviewUi()
                        )
                    )
                )
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it
                    )
                )
            }
        )
    }

    private fun loadMovieRecommendations(mediaId: Int) {
        tryToExecute(
            execute = { getMovieRecommendationsUseCase(mediaId, 1) }, //TODO handle pagination
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        movieDetailsUiState = screenState.value.movieDetailsUiState.copy(
                            recommendations = it.toListOfMovieSimilarUI()
                        )
                    )
                )
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it
                    )
                )
            }
        )
    }

    private fun loadCastDetails(mediaId: Int) {
        tryToExecute(
            execute = { getMovieCastUseCase(mediaId) },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        movieDetailsUiState = screenState.value.movieDetailsUiState.copy(
                            cast = it.toListOfCastUi()
                        )
                    )
                )
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it
                    )
                )
            }
        )
    }

    private fun loadMovieGallery(mediaId: Int) {
        tryToExecute(
            execute = { getMovieGalleryUseCase(mediaId) },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        movieDetailsUiState = screenState.value.movieDetailsUiState.copy(
                            gallery = it.toUi()
                        )
                    )
                )
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it
                    )
                )
            }
        )
    }

    override fun onNavigateBack() {
        tryToExecute(
            execute = { appNavigator.navigateUp() },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it
                    )
                )
            }
        )
    }

    override fun onFavouriteClick(title: String) {
        navigate(MediaDetailsDestinations.LoginDialogDestination(title))
    }

    override fun onAddToListClick(title: String) {
        navigate(MediaDetailsDestinations.LoginDialogDestination(title))
    }

    override fun onShowAllCastClick(movieId: Int) {
        navigate(MediaDetailsDestinations.MovieCastScreen(movieId = movieId))
    }
}