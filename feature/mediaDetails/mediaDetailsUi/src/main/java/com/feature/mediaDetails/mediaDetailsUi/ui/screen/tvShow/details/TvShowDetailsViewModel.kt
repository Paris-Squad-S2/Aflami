package com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details

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
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toEpisodeVideoUi
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfEpisodeUi
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfMTvShowSimilarUI
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfProductionCompanyUi
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfReviewUi
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toMedia
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toTvVideoUi
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toUi
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsNavigator
import com.feature.mediaDetails.mediaDetailsUi.ui.paging.PagingSource
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.SimilarMediaUI
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.ReviewUi
import com.paris.domain.media.entity.Cast
import com.paris.domain.media.entity.Image
import com.paris.domain.media.entity.MediaType
import com.paris.domain.media.entity.MediaVideo
import com.paris.domain.media.entity.ProductionCompany
import com.paris.domain.media.entity.Season
import com.paris.domain.media.entity.TvShow
import com.paris.domain.media.useCase.media.AddWatchHistoryUseCase
import com.paris.domain.media.useCase.media.FilterRatedMediaUseCase
import com.paris.domain.media.useCase.tvShows.AddRatingToTvShowUseCase
import com.paris.domain.media.useCase.tvShows.GetEpisodeVideoUseCase
import com.paris.domain.media.useCase.tvShows.GetSeasonDetailsUseCase
import com.paris.domain.media.useCase.tvShows.GetTvShowCastUseCase
import com.paris.domain.media.useCase.tvShows.GetTvShowDetailsUseCase
import com.paris.domain.media.useCase.tvShows.GetTvShowGalleryUseCase
import com.paris.domain.media.useCase.tvShows.GetTvShowRecommendationsUseCase
import com.paris.domain.media.useCase.tvShows.GetTvShowReviewsUseCase
import com.paris.domain.media.useCase.tvShows.GetTvShowVideoUseCase
import com.paris.domain.media.useCase.tvShows.GetTvShowsProductionCompaniesUseCase
import com.paris.domain.user.usecase.GetAccountIdUseCase
import com.paris.domain.user.usecase.IsLoggedInUseCase
import com.paris.domain.user.usecase.SettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class TvShowDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getTvShowDetailsUseCase: GetTvShowDetailsUseCase,
    private val getTvShowCastUseCase: GetTvShowCastUseCase,
    private val getTvShowGalleryUseCase: GetTvShowGalleryUseCase,
    private val getTvShowRecommendationsUseCase: GetTvShowRecommendationsUseCase,
    private val getTvShowReviewsUseCase: GetTvShowReviewsUseCase,
    private val getTvShowProductionCompaniesUseCase: GetTvShowsProductionCompaniesUseCase,
    private val getSeasonDetailsUseCase: GetSeasonDetailsUseCase,
    private val getTvShowVideoUseCase: GetTvShowVideoUseCase,
    private val addWatchHistoryUseCase: AddWatchHistoryUseCase,
    private val getEpisodeVideoUseCase: GetEpisodeVideoUseCase,
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI,
    private val isLoggedInUseCase: IsLoggedInUseCase,
    private val addRatingToTvShowUseCase: AddRatingToTvShowUseCase,
    private val settingsUseCase: SettingsUseCase,
    private val getRatingUseCase: FilterRatedMediaUseCase,
    private val getAccountIdUseCase: GetAccountIdUseCase,
    navigator: MediaDetailsNavigator,
) : TvShowScreenInteractionListener, BaseViewModel<TvShowDetailsScreenState>(
    TvShowDetailsScreenState(
        TvShowDetailsUiState()
    ), navigator
) {

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

    private val mediaId by lazy {
        savedStateHandle.toRoute<MediaDetailsDestinations.TvShowDetailsScreen>().tvShowId
    }

    init {
        getRestriction()
        loadTvShowDetails(mediaId)
        getInformationVideoTvShow()
    }

    private fun updateTvShowDetailsUiState(updater: (TvShowDetailsUiState) -> TvShowDetailsUiState) {
        updateState(
            screenState.value.copy(
                tvShowDetailsUiState = updater(screenState.value.tvShowDetailsUiState)
            )
        )
    }

    private fun showError(errorMessage: String) {
        updateState(screenState.value.copy(errorMessage = errorMessage))
    }

    private fun hideSnackBar() {
        viewModelScope.launch {
            if (screenState.value.showSnackBar) {
                delay(SNACKBAR_HIDE_DELAY_MS)
                updateState(screenState.value.copy(showSnackBar = false))
            }
        }
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
        showError(error)
    }

    private fun getInformationVideoTvShow() {
        tryToExecute(
            execute = { getTvShowVideoUseCase(mediaId) },
            onSuccess = ::onGetVideoTvShowSuccess,
            onError = ::onGetVideoError,
        )
    }

    private fun loadTvShowDetails(mediaId: Int) {
        tryToExecute(
            execute = { getTvShowDetailsUseCase(mediaId) },
            onSuccess = { tvShow -> onLoadTvShowDetailsSuccess(tvShow, mediaId) },
            onError = ::onLoadTvShowDetailsError
        )
    }
    
    private suspend fun onLoadTvShowDetailsSuccess(tvShow: TvShow, mediaId: Int) {
        addWatchHistoryUseCase(tvShow.toMedia())
        updateTvShowIfItRated(mediaId)
        updateState(
            screenState.value.copy(
                tvShowDetailsUiState = screenState.value.tvShowDetailsUiState.copy(
                    tvShowUi = tvShow.toUi()
                ),
                isLoading = false,
                errorMessage = null
            )
        )
        loadTvShowCast(mediaId)
        loadTvShowGallery(mediaId)
        loadTvShowRecommendations(mediaId)
        loadTvShowReviews(mediaId)
        loadTvShowsProductionCompanies(mediaId)
    }

    private fun updateTvShowIfItRated(mediaId: Int) {
        tryToExecute(
            execute = {
                val accountId = getAccountIdUseCase() ?: -1
                getRatingUseCase(accountId = accountId, MediaType.TvShow).any {
                    it.id == mediaId
                }
            },
            onSuccess = { isRated ->
                updateState(
                    screenState.value.copy(
                        tvShowDetailsUiState = screenState.value.tvShowDetailsUiState.copy(
                            tvShowUi = screenState.value.tvShowDetailsUiState.tvShowUi.copy(
                                isRated = isRated
                            )
                        )
                    )
                )
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        tvShowDetailsUiState = screenState.value.tvShowDetailsUiState.copy(
                            tvShowUi = screenState.value.tvShowDetailsUiState.tvShowUi.copy(
                                isRated = false
                            )
                        ),
                        errorMessage = it
                    )
                )
            },
        )
    }

    private fun onLoadTvShowDetailsError(error: String) {
        updateState(
            screenState.value.copy(
                isLoading = false,
                errorMessage = error
            )
        )
    }

    private fun loadTvShowCast(mediaId: Int) {
        tryToExecute(
            execute = { getTvShowCastUseCase(mediaId) },
            onSuccess = ::onLoadTvShowCastSuccess,
            onError = ::showError
        )
    }
    
    private fun onLoadTvShowCastSuccess(cast: List<Cast>) {
        updateTvShowDetailsUiState { tvShowDetails ->
            tvShowDetails.copy(cast = cast.map { it.toUi() })
        }
    }

    private fun loadTvShowGallery(mediaId: Int) {
        tryToExecute(
            execute = { getTvShowGalleryUseCase(mediaId) },
            onSuccess = ::onLoadTvShowGallerySuccess,
            onError = ::showError
        )
    }
    
    private fun onLoadTvShowGallerySuccess(images: List<Image>) {
        updateTvShowDetailsUiState { tvShowDetails ->
            tvShowDetails.copy(gallery = images.toUi())
        }
    }

    private fun loadTvShowRecommendations(mediaId: Int) {
        tryToExecute(
            execute = { executeLoadTvShowRecommendations(mediaId) },
            onSuccess = ::onLoadTvShowRecommendationsSuccess,
            onError = ::showError
        )
    }
    
    private fun executeLoadTvShowRecommendations(mediaId: Int): Flow<PagingData<SimilarMediaUI>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                PagingSource(
                    mediaUseCase = { page ->
                        getTvShowRecommendationsUseCase(
                            mediaId,
                            page
                        ).toListOfMTvShowSimilarUI()
                    }
                )
            }
        ).flow.cachedIn(viewModelScope)
    }
    
    private fun onLoadTvShowRecommendationsSuccess(recommendations: Flow<PagingData<SimilarMediaUI>>) {
        updateTvShowDetailsUiState { tvShowDetails ->
            tvShowDetails.copy(recommendations = recommendations)
        }
    }

    private fun loadTvShowReviews(mediaId: Int) {
        tryToExecute(
            execute = { getTvShowReviewsUseCase(mediaId, 1).toListOfReviewUi() },
            onSuccess = ::onLoadTvShowReviewsSuccess,
            onError = ::showError
        )
    }
    
    private fun onLoadTvShowReviewsSuccess(reviews: List<ReviewUi>) {
        updateTvShowDetailsUiState { tvShowDetails ->
            tvShowDetails.copy(reviews = reviews)
        }
    }

    private fun loadTvShowsProductionCompanies(mediaId: Int) {
        tryToExecute(
            execute = { getTvShowProductionCompaniesUseCase(mediaId) },
            onSuccess = ::onLoadTvShowsProductionCompaniesSuccess,
            onError = ::showError
        )
    }
    
    private fun onLoadTvShowsProductionCompaniesSuccess(productionCompanies: List<ProductionCompany>) {
        updateTvShowDetailsUiState { tvShowDetails ->
            tvShowDetails.copy(
                tvShowUi = tvShowDetails.tvShowUi.copy(
                    productionCompanies = productionCompanies.toListOfProductionCompanyUi()
                )
            )
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
            updateState(
                screenState.value.copy(
                    showRatingDialog = true
                )
            )
        } else {
            navigate(
                MediaDetailsDestinations.LoginDialogDestination(
                    R.string.rate
                )
            )
        }
    }

    override fun onShowAllCastClick(tvShowId: Int) {
        navigate(MediaDetailsDestinations.TvShowCastScreen(tvShowId = tvShowId))
    }

    override fun onClickOnSeason(seasonNumber: Int) {
        val currentSeason = screenState.value.tvShowDetailsUiState.tvShowUi.seasons
            .find { it.seasonNumber == seasonNumber }

        if (currentSeason?.isExpanded == true && currentSeason.episodes.isNotEmpty()) {
            return
        }

        tryToExecute(
            execute = { executeClickOnSeason(seasonNumber) },
            onSuccess = ::onClickOnSeasonSuccess,
            onError = ::onClickOnSeasonError
        )
    }
    
    private suspend fun executeClickOnSeason(seasonNumber: Int): Season {
        updateState(
            screenState.value.copy(
                seasonsLoadingStates = screenState.value.seasonsLoadingStates + (seasonNumber to true)
            )
        )
        return getSeasonDetailsUseCase(
            screenState.value.tvShowDetailsUiState.tvShowUi.id,
            seasonNumber
        )
    }
    
    private fun onClickOnSeasonSuccess(seasons: Season) {
        updateState(
            screenState.value.copy(
                tvShowDetailsUiState = screenState.value.tvShowDetailsUiState.copy(
                    tvShowUi = screenState.value.tvShowDetailsUiState.tvShowUi.copy(
                        seasons = screenState.value.tvShowDetailsUiState.tvShowUi.seasons.map {
                            if (it.seasonNumber == seasons.seasonNumber) {
                                it.copy(
                                    isExpanded = true,
                                    episodes = seasons.episodes.toListOfEpisodeUi()
                                )
                            } else {
                                it
                            }
                        }
                    ),
                ),
                seasonsLoadingStates = screenState.value.seasonsLoadingStates - seasons.seasonNumber
            )
        )
    }
    
    private fun onClickOnSeasonError(error: String) {
        val seasonNumber = screenState.value.seasonsLoadingStates.keys.firstOrNull() ?: 0
        updateState(
            screenState.value.copy(
                errorMessage = error,
                seasonsLoadingStates = screenState.value.seasonsLoadingStates - seasonNumber
            )
        )
    }

    override fun onRetryLoadTvShowDetails() {
        updateState(
            screenState.value.copy(
                isLoading = true,
                errorMessage = null
            )
        )
        loadTvShowDetails(mediaId = mediaId)
    }

    override fun onDismissRatingDialog() {
        updateState(
            screenState.value.copy(
                showRatingDialog = false
            )
        )
    }

    override fun onRatingSubmitted(tvShowId: Int, rating: Float) {
        tryToExecute(
            execute = { executeSubmitRating(tvShowId, rating) },
            onSuccess = ::onRatingSubmittedSuccess,
            onError = ::onRatingSubmittedError
        )
    }
    
    private suspend fun executeSubmitRating(tvShowId: Int, rating: Float) {
        val roundedRating = ((rating / RATING_STEP).roundToInt() * RATING_STEP)
        addRatingToTvShowUseCase(tvShowId, roundedRating)
    }
    
    private fun onRatingSubmittedSuccess(unit: Unit) {
        updateState(
            screenState.value.copy(
                showSnackBar = true,
                snackBarSuccess = true,
                snackBarMessage = R.string.rating_submit_successfully,
                showRatingDialog = false,
                tvShowDetailsUiState = screenState.value.tvShowDetailsUiState.copy(
                    tvShowUi = screenState.value.tvShowDetailsUiState.tvShowUi.copy(
                        isRated = true
                    )
                )
            )
        )
        hideSnackBar()
    }
    
    private fun onRatingSubmittedError(error: String) {
        updateState(
            screenState.value.copy(
                showSnackBar = true,
                snackBarSuccess = false,
                snackBarMessage = R.string.failed_to_submit_rating,
                errorMessage = error,
                showRatingDialog = false
            )
        )
        hideSnackBar()
    }

    override fun onClickPlayEpisodeTrailer(tvShowId: Int, seasonNumber: Int, episodeNumber: Int) {
        tryToExecute(
            execute = { getEpisodeVideoUseCase(tvShowId, seasonNumber, episodeNumber) },
            onSuccess = ::onGetVideoEpisodeSuccess,
            onError = ::onGetVideoEpisodeError,
        )
    }

    override fun onHideSnackBar() {
        updateState(screenState.value.copy(showSnackBar = false))
    }

    override fun onSimilarTvShowClick(mediaId: Int) {
        mediaDetailsFeatureAPI.startTvShowDetails(
            tvShowId = mediaId
        )
    }

    override fun playYoutubeVideo(videoKey: String) {
        updateTvShowDetailsUiState { tvShowDetails ->
            tvShowDetails.copy(
                isYoutubePlayerVisible = true,
                youtubeVideoKey = videoKey
            )
        }
    }

    override fun closeYoutubePlayer() {
        updateTvShowDetailsUiState { tvShowDetails ->
            tvShowDetails.copy(
                isYoutubePlayerVisible = false,
                youtubeVideoKey = null
            )
        }
    }

    private fun onGetVideoTvShowSuccess(tvShowVideo: MediaVideo) {
        updateTvShowDetailsUiState { tvShowDetails ->
            tvShowDetails.copy(tvShowVideoUi = tvShowVideo.toTvVideoUi())
        }
    }

    private fun onGetVideoEpisodeSuccess(episodeVideo: MediaVideo) {
        if (episodeVideo.site.isEmpty() || episodeVideo.key.isEmpty()) {
            updateState(
                screenState.value.copy(
                    showSnackBar = true,
                )
            )
            hideSnackBar()
        } else {
            playYoutubeVideo(episodeVideo.key)
        }
        updateState(
            screenState.value.copy(
                showSnackBar = false,
                tvShowDetailsUiState = screenState.value.tvShowDetailsUiState.copy(episodeVideoUi = episodeVideo.toEpisodeVideoUi())
            )
        )
    }

    private fun onGetVideoError(error: String) {
        showError(error)
    }

    private fun onGetVideoEpisodeError(error: String) {
        updateState(
            screenState.value.copy(
                snackBarMessage = R.string.not_found_video,
                showSnackBar = true
            )
        )
        hideSnackBar()
    }
}