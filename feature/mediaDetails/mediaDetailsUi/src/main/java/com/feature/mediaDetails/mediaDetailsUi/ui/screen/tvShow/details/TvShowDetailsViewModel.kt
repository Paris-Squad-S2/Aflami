package com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.domain.mediaDetails.useCases.tvShows.AddTvShowToFavoriteUseCase
import com.domain.mediaDetails.useCases.tvShows.GetSeasonDetailsUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowCastUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowDetailsUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowGalleryUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowRecommendationsUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowReviewsUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowsProductionCompaniesUseCase
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.BaseViewModel
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfEpisodeUi
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfProductionCompanyUi
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toUi
import com.feature.mediaDetails.mediaDetailsUi.ui.paging.ReviewTvShowPagingSource
import com.feature.mediaDetails.mediaDetailsUi.ui.paging.SimilarTvShowPageSource
import com.paris_2.aflami.appnavigation.AppNavigator
import kotlinx.coroutines.flow.flowOf

class TvShowDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val getTvShowDetailsUseCase: GetTvShowDetailsUseCase,
    private val getTvShowCastUseCase: GetTvShowCastUseCase,
    private val getTvShowGalleryUseCase: GetTvShowGalleryUseCase,
    private val getTvShowRecommendationsUseCase: GetTvShowRecommendationsUseCase,
    private val getTvShowReviewsUseCase: GetTvShowReviewsUseCase,
    private val getTvShowProductionCompaniesUseCase: GetTvShowsProductionCompaniesUseCase,
    private val addTvShowToFavoriteUseCase: AddTvShowToFavoriteUseCase,
    private val getSeasonDetailsUseCase: GetSeasonDetailsUseCase,
    private val appNavigator: AppNavigator,
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
                Log.d("TAG111", "onClickOnSeason: $error")
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
}