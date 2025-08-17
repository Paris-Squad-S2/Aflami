package com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.mediaDetails.mediaDetailsUi.R
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.BaseViewModel
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfEpisodeUi
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfMTvShowSimilarUI
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfProductionCompanyUi
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfReviewUi
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toMedia
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toUi
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsNavigator
import com.feature.mediaDetails.mediaDetailsUi.ui.paging.PagingSource
import com.paris_2.domain.media.entity.EpisodeVideo
import com.paris_2.domain.media.entity.TvShowVideo
import com.paris_2.domain.media.useCase.AddWatchHistoryUseCase
import com.paris_2.domain.media.useCase.tvShows.AddRatingToTvShowUseCase
import com.paris_2.domain.media.useCase.tvShows.GetEpisodeVideoUseCase
import com.paris_2.domain.media.useCase.tvShows.GetSeasonDetailsUseCase
import com.paris_2.domain.media.useCase.tvShows.GetTvShowCastUseCase
import com.paris_2.domain.media.useCase.tvShows.GetTvShowDetailsUseCase
import com.paris_2.domain.media.useCase.tvShows.GetTvShowGalleryUseCase
import com.paris_2.domain.media.useCase.tvShows.GetTvShowRecommendationsUseCase
import com.paris_2.domain.media.useCase.tvShows.GetTvShowReviewsUseCase
import com.paris_2.domain.media.useCase.tvShows.GetTvShowVideoUseCase
import com.paris_2.domain.media.useCase.tvShows.GetTvShowsProductionCompaniesUseCase
import com.paris_2.domain.user.usecase.IsLoggedInUseCase
import com.paris_2.domain.user.usecase.SettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
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
    navigator: MediaDetailsNavigator,
) : TvShowScreenInteractionListener, BaseViewModel<TvShowDetailsScreenState>(

    TvShowDetailsScreenState(
        TvShowDetailsUiState()
    ), navigator
) {


    private val mediaId by lazy {
        savedStateHandle.toRoute<MediaDetailsDestinations.TvShowDetailsScreen>().tvShowId
    }

    init {
        getRestriction()
        loadTvShowDetails(mediaId)
        getInformationVideoTvShow()
    }

    private fun hideSnackBar() {
        viewModelScope.launch {
            if (screenState.value.showSnackBar) {
                delay(3000)
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
                errorMessage = error,
            )
        )
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
            onSuccess = { tvShow ->
                addWatchHistoryUseCase(tvShow.toMedia())
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
            },
            onError = { error ->
                updateState(
                    screenState.value.copy(
                        isLoading = false,
                        errorMessage = error
                    )
                )
            }
        )
    }


    private fun loadTvShowCast(mediaId: Int) {
        tryToExecute(
            execute = { getTvShowCastUseCase(mediaId) },
            onSuccess = { cast ->
                updateState(
                    screenState.value.copy(
                        tvShowDetailsUiState = screenState.value.tvShowDetailsUiState.copy(
                            cast = cast.map { it.toUi() }
                        )
                    )
                )
            },
            onError = { error ->
                updateState(
                    screenState.value.copy(
                        errorMessage = error
                    )
                )
            }
        )
    }


    private fun loadTvShowGallery(mediaId: Int) {
        tryToExecute(
            execute = { getTvShowGalleryUseCase(mediaId) },
            onSuccess = { gallery ->
                updateState(
                    screenState.value.copy(
                        tvShowDetailsUiState = screenState.value.tvShowDetailsUiState.copy(
                            gallery = gallery.toUi()
                        )
                    )
                )
            },
            onError = { error ->
                updateState(
                    screenState.value.copy(
                        errorMessage = error
                    )
                )
            }
        )
    }


    private fun loadTvShowRecommendations(mediaId: Int) {
        tryToExecute(
            execute = {
                Pager(
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
            }, onSuccess = { recommendations ->
                updateState(
                    screenState.value.copy(
                        tvShowDetailsUiState = screenState.value.tvShowDetailsUiState.copy(
                            recommendations = recommendations
                        )
                    )
                )
            },
            onError = { error ->
                updateState(
                    screenState.value.copy(
                        errorMessage = error
                    )
                )
            }
        )

    }


    private fun loadTvShowReviews(mediaId: Int) {
        tryToExecute(
            execute = {

                getTvShowReviewsUseCase(mediaId, 1).toListOfReviewUi()

            },
            onSuccess = { reviews ->
                updateState(
                    screenState.value.copy(
                        tvShowDetailsUiState = screenState.value.tvShowDetailsUiState.copy(
                            reviews = reviews
                        )
                    )
                )
            },
            onError = { error ->
                updateState(
                    screenState.value.copy(
                        errorMessage = error
                    )
                )
            }
        )
    }

    private fun loadTvShowsProductionCompanies(mediaId: Int) {
        tryToExecute(
            execute = { getTvShowProductionCompaniesUseCase(mediaId) },
            onSuccess = { productionCompanies ->
                updateState(
                    screenState.value.copy(
                        tvShowDetailsUiState = screenState.value.tvShowDetailsUiState.copy(
                            tvShowUi = screenState.value.tvShowDetailsUiState.tvShowUi.copy(
                                productionCompanies = productionCompanies.toListOfProductionCompanyUi()
                            )
                        )
                    )
                )
            },
            onError = { error ->
                updateState(
                    screenState.value.copy(
                        errorMessage = error
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
                } else {
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
            execute = {
                updateState(
                    screenState.value.copy(
                        seasonsLoadingStates = screenState.value.seasonsLoadingStates + (seasonNumber to true)
                    )
                )

                getSeasonDetailsUseCase(
                    screenState.value.tvShowDetailsUiState.tvShowUi.id,
                    seasonNumber
                )
            },
            onSuccess = { seasons ->
                updateState(
                    screenState.value.copy(
                        tvShowDetailsUiState = screenState.value.tvShowDetailsUiState.copy(
                            tvShowUi = screenState.value.tvShowDetailsUiState.tvShowUi.copy(
                                seasons = screenState.value.tvShowDetailsUiState.tvShowUi.seasons.map {
                                    if (it.seasonNumber == seasonNumber) {
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
                        seasonsLoadingStates = screenState.value.seasonsLoadingStates - seasonNumber
                    )
                )
            },
            onError = { error ->
                updateState(
                    screenState.value.copy(
                        errorMessage = error,
                        seasonsLoadingStates = screenState.value.seasonsLoadingStates - seasonNumber
                    )
                )
            }
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

    override fun onRatingSubmitted(movieId: Int, rating: Float) {
        tryToExecute(
            execute = {
                val step = 0.5f
                val roundedRating = ((rating / step).roundToInt() * step)
                addRatingToTvShowUseCase(movieId, roundedRating)
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

    override fun onClickPlayEpisodeTrailer(tvShowId: Int, seasonNumber: Int, episodeNumber: Int) {
        tryToExecute(
            execute = {
                getEpisodeVideoUseCase(
                    tvShowId,
                    seasonNumber,
                    episodeNumber,
                )
            },
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
        updateState(
            screenState.value.copy(
                tvShowDetailsUiState = screenState.value.tvShowDetailsUiState.copy(
                    isYoutubePlayerVisible = true,
                    youtubeVideoKey = videoKey
                )
            )
        )
    }

    override fun closeYoutubePlayer() {
        updateState(
            screenState.value.copy(
                tvShowDetailsUiState = screenState.value.tvShowDetailsUiState.copy(
                    isYoutubePlayerVisible = false,
                    youtubeVideoKey = null
                )
            )
        )
    }

    private fun onGetVideoTvShowSuccess(tvShowVideo: TvShowVideo) {
        updateState(
            screenState.value.copy(
                tvShowDetailsUiState = screenState.value.tvShowDetailsUiState.copy(
                    tvShowVideoUi = tvShowVideo.toUi()
                )
            )
        )
    }

    private fun onGetVideoEpisodeSuccess(episodeVideo: EpisodeVideo) {
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
                tvShowDetailsUiState = screenState.value.tvShowDetailsUiState.copy(episodeVideoUi = episodeVideo.toUi())
            )
        )
    }

    private fun onGetVideoError(error: String) {
        updateState(
            screenState.value.copy(
                errorMessage = error
            )
        )
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