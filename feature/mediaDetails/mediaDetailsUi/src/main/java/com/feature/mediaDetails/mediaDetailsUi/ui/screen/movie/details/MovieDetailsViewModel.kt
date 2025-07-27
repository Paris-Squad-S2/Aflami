package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.domain.mediaDetails.model.Cast
import com.domain.mediaDetails.model.Gallery
import com.domain.mediaDetails.model.Movie
import com.domain.mediaDetails.model.MovieVideo
import com.domain.mediaDetails.model.ProductionCompany
import com.domain.mediaDetails.useCase.movie.AddRatingToMovieUseCase
import com.domain.mediaDetails.useCase.movie.GetMovieCastUseCase
import com.domain.mediaDetails.useCase.movie.GetMovieDetailsUseCase
import com.domain.mediaDetails.useCase.movie.GetMovieGalleryUseCase
import com.domain.mediaDetails.useCase.movie.GetMovieRecommendationsUseCase
import com.domain.mediaDetails.useCase.movie.GetMovieReviewsUseCase
import com.domain.mediaDetails.useCase.movie.GetMoviesProductionCompaniesUseCase
import com.domain.mediaDetails.useCases.movie.GetMovieVideoUseCase
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.BaseViewModel
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfCastUi
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfProductionCompanyUi
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toUi
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.paging.ReviewMoviePagingSource
import com.feature.mediaDetails.mediaDetailsUi.ui.paging.SimilarMoviePageSource
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.SimilarMediaUI
import com.paris_2.domain.authentication.usecase.IsLoggedInUseCase
import kotlinx.coroutines.flow.Flow

class MovieDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieCastUseCase: GetMovieCastUseCase,
    private val getMovieGalleryUseCase: GetMovieGalleryUseCase,
    private val getMovieRecommendationsUseCase: GetMovieRecommendationsUseCase,
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase,
    private val getMovieProductionCompaniesUseCase: GetMoviesProductionCompaniesUseCase,
    private val getMovieVideoUseCase: GetMovieVideoUseCase,
    private val isLoggedInUseCase: IsLoggedInUseCase,
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI,
    private val addRatingToMovieUseCase: AddRatingToMovieUseCase,
) : MovieDetailsScreenInteractionListener,
    BaseViewModel<MovieDetailsScreenState>(MovieDetailsScreenState()) {

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
            onSuccess = { handleMovieDetailsSuccess(mediaId, it) },
            onError = ::handleMovieDetailsError
        )
    }


    private fun loadMovieProductionCompanies(mediaId: Int) {
        tryToExecute(
            execute = { getMovieProductionCompaniesUseCase(mediaId) },
            onSuccess = ::handleProductionCompaniesSuccess,
            onError = ::handleProductionCompaniesError
        )
    }


    private fun loadMovieReviews(mediaId: Int) {
        tryToExecute(
            execute = { createReviewPager(mediaId) },
            onSuccess = ::handleMovieReviewsSuccess,
            onError = ::handleMovieReviewsError
        )
    }


    private fun loadMovieRecommendations(mediaId: Int) {
        tryToExecute(
            execute = { createMovieRecommendationsPager(mediaId) },
            onSuccess = ::handleMovieRecommendationsSuccess,
            onError = ::handleMovieRecommendationsError
        )
    }


    private fun loadCastDetails(mediaId: Int) {
        tryToExecute(
            execute = { getMovieCastUseCase(mediaId) },
            onSuccess = ::handleCastSuccess,
            onError = ::handleCastError
        )
    }


    private fun loadMovieGallery(mediaId: Int) {
        tryToExecute(
            execute = { getMovieGalleryUseCase(mediaId) },
            onSuccess = ::handleGallerySuccess,
            onError = ::handleGalleryError
        )
    }


    override fun onFavouriteClick(title: Int) {
        tryToExecute(
            execute = { isLoggedInUseCase() },
            onSuccess = { isLoggedIn ->
                if (isLoggedIn) {
                    tryToExecute(
                        execute = { addRatingToMovieUseCase() },
                        onSuccess = {
                            updateState(
                                screenState.value.copy(
                                    showRatingDialog = true
                                )
                            )
                        },
                        onError = {
                            updateState(screenState.value.copy(errorMessage = it))
                        }
                    )
                } else {
                    navigate(MediaDetailsDestinations.LoginDialogDestination(title))
                }
            },
            onError = {
                updateState(screenState.value.copy(errorMessage = it))
            }
        )

    }

    override fun onAddToListClick(title: Int) {
        navigate(MediaDetailsDestinations.LoginDialogDestination(title))
    }

    override fun onShowAllCastClick(movieId: Int) {
        navigate(MediaDetailsDestinations.MovieCastScreen(movieId = movieId))
    }

    override fun onRetryLoadMovieDetails() {
        resetStateBeforeRetry()
        loadedMovieDetails(mediaId = movieId)
    }

    override fun onSimilarMovieClick(mediaId: Int) {
        mediaDetailsFeatureAPI.startMovieDetails(mediaId)
    }

    override fun onDismissRatingDialog() {
        updateState(
            screenState.value.copy(
                showRatingDialog = false
            )
        )
    }

    override fun onRatingSubmitted(rating: Float) {}

    private fun resetStateBeforeRetry() {
        updateState(
            screenState.value.copy(
                isLoading = true,
                errorMessage = null
            )
        )
    }


    private fun handleMovieDetailsSuccess(mediaId: Int, result: Movie) {
        updateState(
            screenState.value.copy(
                isLoading = false,
                movieDetailsUiState = screenState.value.movieDetailsUiState.copy(
                    movie = result.toUi()
                )
            )
        )

        loadAllAdditionalMovieData(mediaId)
    }

    private fun loadAllAdditionalMovieData(mediaId: Int) {
        loadCastDetails(mediaId)
        loadMovieGallery(mediaId)
        loadMovieRecommendations(mediaId)
        loadMovieReviews(mediaId)
        loadMovieProductionCompanies(mediaId)
    }

    private fun handleMovieDetailsError(error: String?) {
        updateState(
            screenState.value.copy(
                isLoading = false,
                errorMessage = error
            )
        )
    }

    private fun handleProductionCompaniesSuccess(result: List<ProductionCompany>) {
        updateState(
            screenState.value.copy(
                movieDetailsUiState = screenState.value.movieDetailsUiState.copy(
                    movie = screenState.value.movieDetailsUiState.movie.copy(
                        productionCompanies = result.toListOfProductionCompanyUi()
                    )
                )
            )
        )
    }

    private fun handleProductionCompaniesError(error: String?) {
        updateState(
            screenState.value.copy(
                errorMessage = error
            )
        )
    }

    private fun createReviewPager(mediaId: Int): Flow<PagingData<ReviewUi>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                ReviewMoviePagingSource(
                    mediaId = mediaId,
                    getMovieReviewsUseCase = getMovieReviewsUseCase
                )
            }
        ).flow.cachedIn(viewModelScope)
    }

    private fun handleMovieReviewsSuccess(reviewsFlow: Flow<PagingData<ReviewUi>>) {
        updateState(
            screenState.value.copy(
                movieDetailsUiState = screenState.value.movieDetailsUiState.copy(
                    reviews = reviewsFlow
                )
            )
        )
    }

    private fun handleMovieReviewsError(errorMessage: String?) {
        updateState(
            screenState.value.copy(
                errorMessage = errorMessage
            )
        )
    }

    private fun createMovieRecommendationsPager(mediaId: Int): Flow<PagingData<SimilarMediaUI>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                SimilarMoviePageSource(
                    movieId = mediaId,
                    getMovieRecommendationsUseCase = getMovieRecommendationsUseCase
                )
            }
        ).flow.cachedIn(viewModelScope)
    }

    private fun handleMovieRecommendationsSuccess(pagingData: Flow<PagingData<SimilarMediaUI>>) {
        updateState(
            screenState.value.copy(
                movieDetailsUiState = screenState.value.movieDetailsUiState.copy(
                    recommendations = pagingData
                )
            )
        )
    }

    private fun handleMovieRecommendationsError(message: String) {
        updateState(
            screenState.value.copy(
                errorMessage = message
            )
        )
    }

    private fun handleCastSuccess(result: List<Cast>) {
        updateState(
            screenState.value.copy(
                movieDetailsUiState = screenState.value.movieDetailsUiState.copy(
                    cast = result.toListOfCastUi()
                )
            )
        )
    }

    private fun handleCastError(errorMessage: String) {
        updateState(
            screenState.value.copy(
                errorMessage = errorMessage
            )
        )
    }

    private fun handleGallerySuccess(result: Gallery) {
        updateState(
            screenState.value.copy(
                movieDetailsUiState = screenState.value.movieDetailsUiState.copy(
                    gallery = result.toUi()
                )
            )
        )
    }

    private fun handleGalleryError(errorMessage: String) {
        updateState(
            screenState.value.copy(
                errorMessage = errorMessage
            )
        )
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
}