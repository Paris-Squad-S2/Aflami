package com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.domain.mediaDetails.model.TvShowVideo
import com.domain.mediaDetails.useCase.tvShows.AddRatingToTvShowUseCase
import com.domain.mediaDetails.useCase.tvShows.GetSeasonDetailsUseCase
import com.domain.mediaDetails.useCase.tvShows.GetTvShowCastUseCase
import com.domain.mediaDetails.useCase.tvShows.GetTvShowDetailsUseCase
import com.domain.mediaDetails.useCase.tvShows.GetTvShowGalleryUseCase
import com.domain.mediaDetails.useCase.tvShows.GetTvShowRecommendationsUseCase
import com.domain.mediaDetails.useCase.tvShows.GetTvShowReviewsUseCase
import com.domain.mediaDetails.useCase.tvShows.GetTvShowsProductionCompaniesUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowVideoUseCase
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.BaseViewModel
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfEpisodeUi
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfProductionCompanyUi
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toUi
import com.feature.mediaDetails.mediaDetailsUi.ui.paging.ReviewTvShowPagingSource
import com.feature.mediaDetails.mediaDetailsUi.ui.paging.SimilarTvShowPageSource
import com.paris_2.domain.authentication.usecase.IsLoggedInUseCase
import kotlinx.coroutines.flow.flowOf

class TvShowDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val getTvShowDetailsUseCase: GetTvShowDetailsUseCase,
    private val getTvShowCastUseCase: GetTvShowCastUseCase,
    private val getTvShowGalleryUseCase: GetTvShowGalleryUseCase,
    private val getTvShowRecommendationsUseCase: GetTvShowRecommendationsUseCase,
    private val getTvShowReviewsUseCase: GetTvShowReviewsUseCase,
    private val getTvShowProductionCompaniesUseCase: GetTvShowsProductionCompaniesUseCase,
    private val getSeasonDetailsUseCase: GetSeasonDetailsUseCase,
    private val getTvShowVideoUseCase: GetTvShowVideoUseCase,
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI,
    private val isLoggedInUseCase: IsLoggedInUseCase,
    private val addRatingToTvShowUseCase: AddRatingToTvShowUseCase
) : TvShowScreenInteractionListener, BaseViewModel<TvShowDetailsScreenState>(
    TvShowDetailsScreenState(
        TvShowDetailsUiState(
            tvShowUi = TvShowUi(
                id = 0,
                posterUrl = "",
                rating = 0f,
                title = "",
                genres = emptyList(),
                releaseDate = "",
                runtime = "",
                country = "",
                description = "",
                seasons = emptyList(),
                productionCompanies = emptyList()
            ),
            cast = emptyList(),
            reviews = flowOf(PagingData.empty()),
            gallery = emptyList(),
            recommendations = flowOf(PagingData.empty()),
            tvShowVideoUi = TvShowVideoUi(
                key = "",
                name = "",
                site = ""
            ),
            selectedRating = 0f
        ),
        isLoading = true,
        errorMessage = null,
        isEpisodesLoading = true,
        seasonsLoadingStates = emptyMap()
    )
) {


    private val mediaId by lazy {
        savedStateHandle.toRoute<MediaDetailsDestinations.TvShowDetailsScreen>().tvShowId

    }
    init {
        loadTvShowDetails(mediaId)
        getInformationVideoTvShow()
    }

    private fun getInformationVideoTvShow() {
        tryToExecute(
            execute = { getTvShowVideoUseCase(mediaId) },
            onSuccess = ::onGetVideoTvShowSuccess,
            onError = ::onGetVideoTvShowError,
        )
    }

    private fun loadTvShowDetails(mediaId: Int) {
        tryToExecute(
            execute = { getTvShowDetailsUseCase(mediaId) },
            onSuccess = { tvShow ->
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
                        SimilarTvShowPageSource(
                            movieId = mediaId,
                            getTvShowRecommendationsUseCase = getTvShowRecommendationsUseCase
                        )
                    }
                ).flow.cachedIn(viewModelScope)
            },            onSuccess = { recommendations ->
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
                Pager(
                    config = PagingConfig(pageSize = 10),
                    pagingSourceFactory = {
                        ReviewTvShowPagingSource(
                            mediaId = mediaId,
                            getTvShowReviewsUseCase = getTvShowReviewsUseCase
                        )
                    }
                ).flow.cachedIn(viewModelScope) },
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

    override fun onFavouriteClick(title: Int) {
        tryToExecute(
            execute = { isLoggedInUseCase() },
            onSuccess = { isLoggedIn ->
                if (isLoggedIn) {
                    tryToExecute(
                        execute = { addRatingToTvShowUseCase() },
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

    override fun onClickPlayTrailer() {
        if (screenState.value.tvShowDetailsUiState.tvShowVideoUi.key.isEmpty() ||
            screenState.value.tvShowDetailsUiState.tvShowVideoUi.site.isEmpty()) {
            updateState(
                screenState.value.copy(
                    errorMessage = "No video available"
                )
            )
            return
        }
        navigate(
            MediaDetailsDestinations.VideosScreen(
                site = screenState.value.tvShowDetailsUiState.tvShowVideoUi.site,
                key = screenState.value.tvShowDetailsUiState.tvShowVideoUi.key
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

    override fun onRatingSubmitted(rating: Float) {}

    override fun onSimilarTvShowClick(mediaId: Int) {
        mediaDetailsFeatureAPI.startTvShowDetails(
            tvShowId = mediaId
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

    private fun onGetVideoTvShowError(error: String) {
        updateState(
            screenState.value.copy(
                errorMessage = error
            )
        )
    }
}