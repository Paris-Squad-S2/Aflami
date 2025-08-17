package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.mediaDetails.mediaDetailsUi.R
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.BaseViewModel
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfCastUi
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfMovieSimilarUI
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfProductionCompanyUi
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfReviewUi
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toMedia
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toUi
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsNavigator
import com.feature.mediaDetails.mediaDetailsUi.ui.paging.PagingSource
import com.paris.domain.lists.useCase.AddMovieToListUseCase
import com.paris.domain.lists.useCase.CreateListUseCase
import com.paris.domain.lists.useCase.GetListUseCase
import com.paris_2.aflami.designsystem.components.ButtonState
import com.paris_2.domain.media.entity.MovieVideo
import com.paris_2.domain.media.useCase.AddWatchHistoryUseCase
import com.paris_2.domain.media.useCase.movie.AddRatingToMovieUseCase
import com.paris_2.domain.media.useCase.movie.GetMovieCastUseCase
import com.paris_2.domain.media.useCase.movie.GetMovieDetailsUseCase
import com.paris_2.domain.media.useCase.movie.GetMovieGalleryUseCase
import com.paris_2.domain.media.useCase.movie.GetMovieRecommendationsUseCase
import com.paris_2.domain.media.useCase.movie.GetMovieReviewsUseCase
import com.paris_2.domain.media.useCase.movie.GetMovieVideoUseCase
import com.paris_2.domain.media.useCase.movie.GetMoviesProductionCompaniesUseCase
import com.paris_2.domain.user.usecase.IsLoggedInUseCase
import com.paris_2.domain.user.usecase.SettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt
import com.paris_2.aflami.designsystem.R as RDesignSystem

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
    private val addWatchHistoryUseCase: AddWatchHistoryUseCase,
    private val addRatingToMovieUseCase: AddRatingToMovieUseCase,
    private val addMovieToListUseCase: AddMovieToListUseCase,
    private val getListsUseCase: GetListUseCase,
    private val createListUseCase: CreateListUseCase,
    private val settingsUseCase: SettingsUseCase,
    navigator: MediaDetailsNavigator,
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
            reviews = emptyList(),
            gallery = emptyList(),
            recommendations = flowOf(PagingData.empty()),
            movieVideoUi = MovieVideoUi(
                key = "",
                name = "",
                site = "",
            ),
            selectedRating = 0f,
            isYoutubePlayerVisible = false,
            youtubeVideoKey = null
        ),
        isLoading = true,
        errorMessage = null,
        showSnackBar = false,
        availableLists = emptyList(),
        selectedListIndex = -1,
        showCreateListDialog = false,
        createListName = "",
        createListButtonState = ButtonState.Normal
    ), navigator
) {


    private val movieId by lazy {
        savedStateHandle.toRoute<MediaDetailsDestinations.MovieDetailsScreen>().movieId
    }

    init {
        getRestriction()
        loadedMovieDetails(mediaId = movieId)
        getInformationVideoMovie()
        loadAvailableLists()
    }

    private fun getRestriction() {
        tryToExecute(
            execute = { settingsUseCase.getRestriction() },
            onSuccess = ::onGetRestrictionSuccess,
            onError = ::onGetRestrictionError
        )
    }

    private fun onGetRestrictionSuccess(restriction: String) {
        updateState(
            screenState.value.copy(
                contentRestriction = ContentRestriction.valueOf(restriction),
                showSnackBar = false,
                snackBarMessage = null
            )
        )
        when (screenState.value.contentRestriction) {
            ContentRestriction.Strict -> updateState(
                screenState.value.copy(
                    nsfwThreshold = 0.8f,
                    genderThreshold = 0.6f
                )
            )

            ContentRestriction.Moderate -> updateState(
                screenState.value.copy(
                    nsfwThreshold = 0.4f,
                    genderThreshold = 0.6f
                )
            )

            ContentRestriction.Off -> updateState(
                screenState.value.copy(
                    nsfwThreshold = 0f,
                    genderThreshold = 0f
                )
            )
        }

    }

    private fun onGetRestrictionError(error: String) {
        updateState(
            screenState.value.copy(
                showSnackBar = true,
                snackBarMessage = R.string.failed_to_load_restriction_settings,
            )
        )
        hideSnackBar()
    }

    private fun loadAvailableLists() {
        tryToExecute(
            execute = { getListsUseCase(1) },
            onSuccess = { lists ->
                updateState(
                    screenState.value.copy(
                        availableLists = lists.map {
                            ListItemUi(
                                id = it.id.toString(),
                                name = it.name,
                                itemCount = it.itemCount
                            )
                        }
                    )
                )
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        errorMessage = errorMessage
                    )
                )
            }
        )
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
            onSuccess = { movie ->
                addWatchHistoryUseCase(movie.toMedia())
                updateState(
                    screenState.value.copy(
                        isLoading = false,
                        movieDetailsUiState = screenState.value.movieDetailsUiState.copy(
                            movie = movie.toUi(),
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
                getMovieReviewsUseCase(mediaId, 1).toListOfReviewUi()
            },
            onSuccess = { reviews ->
                updateState(
                    screenState.value.copy(
                        movieDetailsUiState = screenState.value.movieDetailsUiState.copy(
                            reviews = reviews
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
                        PagingSource(
                            mediaUseCase = { page ->
                                getMovieRecommendationsUseCase(
                                    mediaId,
                                    page
                                ).toListOfMovieSimilarUI()
                            }
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
                    updateState(screenState.value.copy(showRatingDialog = true))
                } else {
                    navigate(MediaDetailsDestinations.LoginDialogDestination(R.string.rate))
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
                    updateState(screenState.value.copy(showAddToListDialog = true))
                } else {
                    navigate(MediaDetailsDestinations.LoginDialogDestination(R.string.add_to_list))
                }
            },
            onError = {
                updateState(screenState.value.copy(errorMessage = it))
            }
        )
    }

    override fun onListSelectionChanged(index: Int) {
        updateState(
            screenState.value.copy(
                selectedListIndex = index
            )
        )
    }

    override fun onAddToSelectedList() {
        val selectedList =
            screenState.value.availableLists.getOrNull(screenState.value.selectedListIndex)
        if (selectedList != null) {
            tryToExecute(
                execute = {
                    addMovieToListUseCase(selectedList.id, movieId)
                },
                onSuccess = {
                    updateState(
                        screenState.value.copy(
                            showAddToListDialog = false,
                            selectedListIndex = -1,
                            showSnackBar = true,
                            snackBarSuccess = true,
                            snackBarMessage = R.string.movie_added_to_list_successfully
                        )
                    )
                    hideSnackBar()
                    loadAvailableLists()
                },
                onError = { errorMessage ->
                    updateState(
                        screenState.value.copy(
                            errorMessage = errorMessage,
                            showSnackBar = true,
                            snackBarSuccess = false,
                        )
                    )
                    hideSnackBar()
                }
            )
        }
    }

    override fun onDismissAddToListDialog() {
        updateState(
            screenState.value.copy(
                showAddToListDialog = false
            )
        )
    }

    override fun onCreateListShow() {
        updateState(screenState.value.copy(showCreateListDialog = true))
    }

    override fun onCreateListDismiss() {
        updateState(
            screenState.value.copy(
                showCreateListDialog = false,
                createListName = "",
                createListButtonState = ButtonState.Normal
            )
        )
    }

    override fun onCreateListNameChange(name: String) {
        updateState(
            screenState.value.copy(
                createListName = name,
                createListButtonState = if (name.isBlank()) ButtonState.Disabled else ButtonState.Normal
            )
        )
    }

    override fun onCreateListConfirm() {
        val listName = screenState.value.createListName.trim()
        if (listName.isNotEmpty()) {
            updateState(screenState.value.copy(createListButtonState = ButtonState.Loading))
            tryToExecute(
                execute = { createListUseCase.invoke(listName) },
                onSuccess = { result ->
                    updateState(
                        screenState.value.copy(
                            showCreateListDialog = false,
                            createListName = "",
                            createListButtonState = ButtonState.Normal,
                            showSnackBar = true,
                            snackBarSuccess = result.success,
                            snackBarMessage = if (result.success) RDesignSystem.string.added_new_list_successfully else RDesignSystem.string.some_error_happened
                        )
                    )
                    hideSnackBar()
                    loadAvailableLists()
                },
                onError = { errorMessage ->
                    updateState(
                        screenState.value.copy(
                            errorMessage = errorMessage,
                            createListButtonState = ButtonState.Normal
                        )
                    )
                }
            )
        }
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

    override fun playYoutubeVideo(videoKey: String) {
        updateState(
            screenState.value.copy(
                movieDetailsUiState = screenState.value.movieDetailsUiState.copy(
                    isYoutubePlayerVisible = true,
                    youtubeVideoKey = videoKey
                )
            )
        )
    }

    override fun closeYoutubePlayer() {
        updateState(
            screenState.value.copy(
                movieDetailsUiState = screenState.value.movieDetailsUiState.copy(
                    isYoutubePlayerVisible = false,
                    youtubeVideoKey = null
                )
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
                hideSnackBar()
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
                hideSnackBar()
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

    private fun hideSnackBar() {
        viewModelScope.launch {
            if (screenState.value.showSnackBar) {
                delay(3000)
                updateState(screenState.value.copy(showSnackBar = false))
            }
        }
    }

}