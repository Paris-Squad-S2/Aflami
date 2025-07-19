package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
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
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfProductionCompanyUi
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toUi
import com.feature.mediaDetails.mediaDetailsUi.ui.paging.ReviewMoviePagingSource
import com.feature.mediaDetails.mediaDetailsUi.ui.paging.SimilarMoviePageSource
import com.paris_2.aflami.appnavigation.AppNavigator
import kotlinx.coroutines.flow.flowOf

class MovieDetailsViewModelViewModel(
    savedStateHandle: SavedStateHandle,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieCastUseCase: GetMovieCastUseCase,
    private val getMovieGalleryUseCase: GetMovieGalleryUseCase,
    private val getMovieRecommendationsUseCase: GetMovieRecommendationsUseCase,
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase,
    private val getMovieProductionCompaniesUseCase: GetMoviesProductionCompaniesUseCase,
    private val addMovieToFavoriteUseCase: AddMovieToFavoriteUseCase,
    private val appNavigator: AppNavigator,
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
            reviews = flowOf(PagingData.empty()),
            gallery = emptyList(),
            recommendations = flowOf(PagingData.empty())
        ),
        isLoading = true,
        errorMessage = null
    )
) {

    private val movieId by lazy {
        savedStateHandle.toRoute<MediaDetailsDestinations.MovieDetailsScreen>().movieId
    }

    init {
        loadedMovieDetails(mediaId = movieId)
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

    override fun onFavouriteClick(title: Int) {
        navigate(MediaDetailsDestinations.LoginDialogDestination(title))
    }

    override fun onAddToListClick(title: Int) {
        navigate(MediaDetailsDestinations.LoginDialogDestination(title))
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
}