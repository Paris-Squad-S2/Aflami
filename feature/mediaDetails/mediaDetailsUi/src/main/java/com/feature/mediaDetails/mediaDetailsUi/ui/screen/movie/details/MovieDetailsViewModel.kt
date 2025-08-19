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
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toMovieVideoUi
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toUi
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsNavigator
import com.feature.mediaDetails.mediaDetailsUi.ui.paging.PagingSource
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.SimilarMediaUI
import com.paris.domain.lists.entity.Lists
import com.paris.domain.lists.useCase.AddMovieToListUseCase
import com.paris.domain.lists.useCase.CreateListUseCase
import com.paris.domain.lists.useCase.GetListUseCase
import com.paris.domain.lists.entity.Response
import com.paris_2.aflami.designsystem.components.ButtonState
import com.paris_2.domain.media.entity.Cast
import com.paris_2.domain.media.entity.Image
import com.paris_2.domain.media.entity.MediaVideo
import com.paris_2.domain.media.entity.Movie
import com.paris_2.domain.media.entity.ProductionCompany
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
import kotlinx.coroutines.flow.Flow
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
) : MovieDetailsScreenInteractionListener,
    BaseViewModel<MovieDetailsScreenState>(MovieDetailsScreenState(), navigator) {


    private val movieId by lazy {
        savedStateHandle.toRoute<MediaDetailsDestinations.MovieDetailsScreen>().movieId
    }

    companion object {
        private const val SNACKBAR_HIDE_DELAY_MS = 3000L
        private const val STRICT_NSFW_THRESHOLD = 0.8f
        private const val STRICT_GENDER_THRESHOLD = 0.6f
        private const val MODERATE_NSFW_THRESHOLD = 0.4f
        private const val MODERATE_GENDER_THRESHOLD = 0.6f
        private const val OFF_NSFW_THRESHOLD = 0.0f
        private const val OFF_GENDER_THRESHOLD = 0.0f
        private const val RATING_STEP = 0.5f
    }

    init {
        getRestriction()
        loadedMovieDetails(mediaId = movieId)
        getInformationVideoMovie()
        loadAvailableLists()
    }

    private fun updateMovieDetailsUiState(updater: (MovieDetailsUiState) -> MovieDetailsUiState) {
        updateState(
            screenState.value.copy(
                movieDetailsUiState = updater(screenState.value.movieDetailsUiState)
            )
        )
    }

    private fun showError(errorMessage: String) {
        updateState(screenState.value.copy(errorMessage = errorMessage))
    }

    private fun showSuccessSnackBar(message: Int) {
        updateState(
            screenState.value.copy(
                showSnackBar = true,
                snackBarSuccess = true,
                snackBarMessage = message
            )
        )
        hideSnackBar()
    }

    private fun showErrorSnackBar(message: Int) {
        updateState(
            screenState.value.copy(
                showSnackBar = true,
                snackBarSuccess = false,
                snackBarMessage = message
            )
        )
        hideSnackBar()
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
        when (ContentRestriction.valueOf(restriction)) {
            ContentRestriction.Strict -> updateState(
                screenState.value.copy(
                    nsfwThreshold = STRICT_NSFW_THRESHOLD,
                    genderThreshold = STRICT_GENDER_THRESHOLD
                )
            )

            ContentRestriction.Moderate -> updateState(
                screenState.value.copy(
                    nsfwThreshold = MODERATE_NSFW_THRESHOLD,
                    genderThreshold = MODERATE_GENDER_THRESHOLD
                )
            )

            ContentRestriction.Off -> updateState(
                screenState.value.copy(
                    nsfwThreshold = OFF_NSFW_THRESHOLD,
                    genderThreshold = OFF_GENDER_THRESHOLD
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
            onSuccess = ::onLoadAvailableListsSuccess,
            onError = ::showError
        )
    }

    private fun onLoadAvailableListsSuccess(lists: List<Lists>) {
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
            onSuccess = { movie -> onLoadMovieDetailsSuccess(movie, mediaId) },
            onError = ::onLoadMovieDetailsError
        )
    }

    private suspend fun onLoadMovieDetailsSuccess(movie: Movie, mediaId: Int) {

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
    }

    private fun onLoadMovieDetailsError(error: String) {
        updateState(
            screenState.value.copy(
                isLoading = false,
                errorMessage = error
            )
        )
    }

    private fun loadMovieProductionCompanies(mediaId: Int) {
        tryToExecute(
            execute = { getMovieProductionCompaniesUseCase(mediaId) },
            onSuccess = ::onLoadMovieProductionCompaniesSuccess,
            onError = ::showError
        )
    }

    private fun onLoadMovieProductionCompaniesSuccess(companies: List<ProductionCompany>) {
        updateMovieDetailsUiState { movieDetails ->
            movieDetails.copy(
                movie = movieDetails.movie.copy(
                    productionCompanies = companies.toListOfProductionCompanyUi()
                )
            )
        }
    }

    private fun loadMovieReviews(mediaId: Int) {
        tryToExecute(
            execute = ::executeLoadMovieReviews,
            onSuccess = ::onLoadMovieReviewsSuccess,
            onError = ::showError
        )
    }

    private suspend fun executeLoadMovieReviews(): List<ReviewUi> {
        return getMovieReviewsUseCase(movieId, 1).toListOfReviewUi()
    }

    private fun onLoadMovieReviewsSuccess(reviews: List<ReviewUi>) {
        updateMovieDetailsUiState { movieDetails ->
            movieDetails.copy(reviews = reviews)
        }
    }

    private fun loadMovieRecommendations(mediaId: Int) {
        tryToExecute(
            execute = { executeLoadMovieRecommendations(mediaId) },
            onSuccess = ::onLoadMovieRecommendationsSuccess,
            onError = ::showError
        )
    }

    private fun executeLoadMovieRecommendations(mediaId: Int): Flow<PagingData<SimilarMediaUI>> {
        return Pager(
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
    }

    private fun onLoadMovieRecommendationsSuccess(recommendations: Flow<PagingData<SimilarMediaUI>>) {
        updateMovieDetailsUiState { movieDetails ->
            movieDetails.copy(recommendations = recommendations)
        }
    }

    private fun loadCastDetails(mediaId: Int) {
        tryToExecute(
            execute = { getMovieCastUseCase(mediaId) },
            onSuccess = ::onLoadCastDetailsSuccess,
            onError = ::showError
        )
    }

    private fun onLoadCastDetailsSuccess(cast: List<Cast>) {
        updateMovieDetailsUiState { movieDetails ->
            movieDetails.copy(cast = cast.toListOfCastUi())
        }
    }

    private fun loadMovieGallery(mediaId: Int) {
        tryToExecute(
            execute = { getMovieGalleryUseCase(mediaId) },
            onSuccess = ::onLoadMovieGallerySuccess,
            onError = ::showError
        )
    }

    private fun onLoadMovieGallerySuccess(images: List<Image>) {
        updateMovieDetailsUiState { movieDetails ->
            movieDetails.copy(gallery = images.toUi())
        }
    }

    override fun onRateClick() {
        tryToExecute(
            execute = { isLoggedInUseCase() },
            onSuccess = ::onRateClickSuccess,
            onError = ::showError
        )
    }

    private fun onRateClickSuccess(isLoggedIn: Boolean) {
        if (isLoggedIn) {
            updateState(screenState.value.copy(showRatingDialog = true))
        } else {
            navigate(MediaDetailsDestinations.LoginDialogDestination(R.string.rate))
        }
    }

    override fun onAddToListClick() {
        tryToExecute(
            execute = { isLoggedInUseCase() },
            onSuccess = ::onAddToListClickSuccess,
            onError = ::showError
        )
    }

    private fun onAddToListClickSuccess(isLoggedIn: Boolean) {
        if (isLoggedIn) {
            updateState(screenState.value.copy(showAddToListDialog = true))
        } else {
            navigate(MediaDetailsDestinations.LoginDialogDestination(R.string.add_to_list))
        }
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
                execute = { addMovieToListUseCase(selectedList.id, movieId) },
                onSuccess = ::onAddToSelectedListSuccess,
                onError = ::onAddToSelectedListError
            )
        }
    }

    private fun onAddToSelectedListSuccess(unit: Unit) {
        updateState(
            screenState.value.copy(
                showAddToListDialog = false,
                selectedListIndex = -1
            )
        )
        showSuccessSnackBar(R.string.movie_added_to_list_successfully)
        loadAvailableLists()
    }

    private fun onAddToSelectedListError(errorMessage: String) {
        showError(errorMessage)
        showErrorSnackBar(RDesignSystem.string.some_error_happened)
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
                onSuccess = ::onCreateListConfirmSuccess,
                onError = ::onCreateListConfirmError
            )
        }
    }

    private fun onCreateListConfirmSuccess(result: Response) {
        updateState(
            screenState.value.copy(
                showCreateListDialog = false,
                createListName = "",
                createListButtonState = ButtonState.Normal
            )
        )
        if (result.success) {
            showSuccessSnackBar(RDesignSystem.string.added_new_list_successfully)
        } else {
            showErrorSnackBar(RDesignSystem.string.some_error_happened)
        }
        loadAvailableLists()
    }

    private fun onCreateListConfirmError(error: String) {
        updateState(
            screenState.value.copy(
                createListButtonState = ButtonState.Normal,
                errorMessage = error
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

    override fun playYoutubeVideo(videoKey: String) {
        updateMovieDetailsUiState { movieDetails ->
            movieDetails.copy(
                isYoutubePlayerVisible = true,
                youtubeVideoKey = videoKey
            )
        }
    }

    override fun closeYoutubePlayer() {
        updateMovieDetailsUiState { movieDetails ->
            movieDetails.copy(
                isYoutubePlayerVisible = false,
                youtubeVideoKey = null
            )
        }
    }

    private fun onGetVideoMovieSuccess(movieVideo: MediaVideo) {
        updateMovieDetailsUiState { movieDetails ->
            movieDetails.copy(
                movieVideoUi = movieVideo.toMovieVideoUi()
            )
        }
    }

    private fun onGetVideoMovieError(error: String) {
        showError(error)
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
            execute = { executeSubmitRating(rating) },
            onSuccess = ::onRatingSubmittedSuccess,
            onError = ::onRatingSubmittedError
        )
    }

    private suspend fun executeSubmitRating(rating: Float) {
        val roundedRating = ((rating / RATING_STEP).roundToInt() * RATING_STEP)
        addRatingToMovieUseCase(movieId, roundedRating)
    }

    private fun onRatingSubmittedSuccess(unit: Unit) {
        updateState(
            screenState.value.copy(
                showRatingDialog = false
            )
        )
        showSuccessSnackBar(R.string.rating_submit_successfully)
    }

    private fun onRatingSubmittedError(errorMessage: String) {
        showError(errorMessage)
        showErrorSnackBar(R.string.failed_to_submit_rating)
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
                delay(SNACKBAR_HIDE_DELAY_MS)
                updateState(screenState.value.copy(showSnackBar = false))
            }
        }
    }
}