package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.domain.mediaDetails.model.MovieVideo
import com.domain.mediaDetails.useCase.movie.AddRatingToMovieUseCase
import com.domain.mediaDetails.useCase.movie.GetMovieCastUseCase
import com.domain.mediaDetails.useCase.movie.GetMovieDetailsUseCase
import com.domain.mediaDetails.useCase.movie.GetMovieGalleryUseCase
import com.domain.mediaDetails.useCase.movie.GetMovieRecommendationsUseCase
import com.domain.mediaDetails.useCase.movie.GetMovieReviewsUseCase
import com.domain.mediaDetails.useCase.movie.GetMoviesProductionCompaniesUseCase
import com.domain.mediaDetails.useCases.movie.GetMovieVideoUseCase
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.mediaDetails.mediaDetailsUi.R
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.BaseViewModel
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfCastUi
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfProductionCompanyUi
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toUi
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsNavigator
import com.feature.mediaDetails.mediaDetailsUi.ui.paging.ReviewMoviePagingSource
import com.feature.mediaDetails.mediaDetailsUi.ui.paging.SimilarMoviePageSource
import com.paris_2.domain.authentication.usecase.IsLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieCastUseCase: GetMovieCastUseCase,
    private val getMovieGalleryUseCase: GetMovieGalleryUseCase,
    private val getMovieRecommendationsUseCase: GetMovieRecommendationsUseCase,
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase,
    private val getMovieProductionCompaniesUseCase: GetMoviesProductionCompaniesUseCase,
    private val getMovieVideoUseCase: GetMovieVideoUseCase,
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI,
    private val isLoggedInUseCase: IsLoggedInUseCase,
    private val addRatingToMovieUseCase: AddRatingToMovieUseCase,
    navigator: MediaDetailsNavigator
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
                productionCompanies = emptyList(),
            ),
            cast = emptyList(),
            reviews = flowOf(PagingData.empty()),
            gallery = emptyList(),
            recommendations = flowOf(PagingData.empty()),
            movieVideoUi = MovieVideoUi(
                key = "",
                name = "",
                site = "",
            ),
            selectedRating = 0f
        ),
        isLoading = true,
        errorMessage = null,
        showSnackBar = false,
    ), navigator
) {

    private val movieId by lazy {
        savedStateHandle.toRoute<MediaDetailsDestinations.MovieDetailsScreen>().movieId
    }

    init {
        loadedMovieDetails(mediaId = movieId)
        getInformationVideoMovie()
    }

    private fun getInformationVideoMovie() {
        tryToExecute(
            execute = { getMovieVideoUseCase(movieId) },
            onSuccess = ::onGetVideoMovieSuccess,
            onError = ::onGetVideoMovieError,
        )
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
            execute = {
                Pager(
                    config = PagingConfig(pageSize = 10),
                    pagingSourceFactory = {
                        ReviewMoviePagingSource(
                            mediaId = mediaId,
                            getMovieReviewsUseCase = getMovieReviewsUseCase
                        )
                    }
                ).flow.cachedIn(viewModelScope)
            },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        movieDetailsUiState = screenState.value.movieDetailsUiState.copy(
                            reviews = it
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
            execute = {
                Pager(
                    config = PagingConfig(pageSize = 10),
                    pagingSourceFactory = {
                        SimilarMoviePageSource(
                            movieId = mediaId,
                            getMovieRecommendationsUseCase = getMovieRecommendationsUseCase,
                        )
                    }
                ).flow.cachedIn(viewModelScope)
            },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        movieDetailsUiState = screenState.value.movieDetailsUiState.copy(
                            recommendations = it
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

    override fun onRateClick() {
        tryToExecute(
            execute = { isLoggedInUseCase() },
            onSuccess = { isLoggedIn ->
                if (isLoggedIn) {
                    updateState(
                        screenState.value.copy(
                            showRatingDialog = true
                        )
                    )
                }
                else {
                    navigate(
                        MediaDetailsDestinations.LoginDialogDestination(
                            R.string.rate
                        )
                    )
                }
            },
            onError = {
                updateState(screenState.value.copy(errorMessage = it))
            }
        )
    }

    override fun onAddToListClick() {
        tryToExecute(
            execute = { isLoggedInUseCase() },
            onSuccess = { isLoggedIn ->
                if (isLoggedIn) {
                    updateState(
                        screenState.value.copy(
                            showAddToListDialog = true
                        )
                    )
                } else {
                    navigate(
                        MediaDetailsDestinations.LoginDialogDestination(
                            R.string.add_to_list
                        )
                    )
                }
            },
            onError = {
                updateState(screenState.value.copy(errorMessage = it))
            }
        )
    }

    override fun onDismissAddToListDialog() {
        updateState(
            screenState.value.copy(
                showAddToListDialog = false
            )
        )
    }

    override fun onShowAllCastClick(movieId: Int) {
        navigate(MediaDetailsDestinations.MovieCastScreen(movieId = movieId))
    }


    override fun onRetryLoadMovieDetails() {
        updateState(
            screenState.value.copy(
                isLoading = true,
                errorMessage = null
            )
        )
        loadedMovieDetails(mediaId = movieId)
    }

    override fun onSimilarMovieClick(mediaId: Int) {
        mediaDetailsFeatureAPI.startMovieDetails(mediaId)
    }


    private fun onGetVideoMovieSuccess(movieVideo: MovieVideo) {
        updateState(
            screenState.value.copy(
                movieDetailsUiState = screenState.value.movieDetailsUiState.copy(
                    movieVideoUi = movieVideo.toUi()
                )
            )
        )
    }

    private fun onGetVideoMovieError(error: String) {
        updateState(
            screenState.value.copy(
                errorMessage = error
            )
        )
    }

    override fun onDismissRatingDialog() {
        updateState(
            screenState.value.copy(
                showRatingDialog = false
            )
        )
    }

    override fun onRatingSubmitted(movieId: Int, rating: Float) {
        tryToExecute(
            execute = {
                val step = 0.5f
                val roundedRating = ((rating / step).roundToInt() * step)
                addRatingToMovieUseCase(movieId, roundedRating)
            },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        showSnackBar = true,
                        snackBarSuccess = true,
                        snackBarMessage = R.string.rating_submit_successfully,
                        showRatingDialog = false
                    )
                )
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        showSnackBar = true,
                        snackBarSuccess = false,
                        snackBarMessage = R.string.failed_to_submit_rating,
                        errorMessage = it
                    )
                )
            }
        )
    }

    override fun onHideSnackBar() {
        updateState(
            screenState.value.copy(
                showSnackBar = false
            )
        )
    }
}