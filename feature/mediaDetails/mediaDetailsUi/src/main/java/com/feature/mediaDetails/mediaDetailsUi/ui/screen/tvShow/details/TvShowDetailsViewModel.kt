package com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.domain.mediaDetails.model.Cast
import com.domain.mediaDetails.model.EpisodeVideo
import com.domain.mediaDetails.model.Gallery
import com.domain.mediaDetails.model.ProductionCompany
import com.domain.mediaDetails.model.Season
import com.domain.mediaDetails.model.TvShow
import com.domain.mediaDetails.model.TvShowVideo
import com.domain.mediaDetails.useCase.tvShows.AddRatingToTvShowUseCase
import com.domain.mediaDetails.useCase.tvShows.GetEpisodeVideoUseCase
import com.domain.mediaDetails.useCase.tvShows.GetSeasonDetailsUseCase
import com.domain.mediaDetails.useCase.tvShows.GetTvShowCastUseCase
import com.domain.mediaDetails.useCase.tvShows.GetTvShowDetailsUseCase
import com.domain.mediaDetails.useCase.tvShows.GetTvShowGalleryUseCase
import com.domain.mediaDetails.useCase.tvShows.GetTvShowRecommendationsUseCase
import com.domain.mediaDetails.useCase.tvShows.GetTvShowReviewsUseCase
import com.domain.mediaDetails.useCase.tvShows.GetTvShowVideoUseCase
import com.domain.mediaDetails.useCase.tvShows.GetTvShowsProductionCompaniesUseCase
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.mediaDetails.mediaDetailsUi.R
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.BaseViewModel
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfEpisodeUi
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfProductionCompanyUi
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toUi
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.paging.ReviewTvShowPagingSource
import com.feature.mediaDetails.mediaDetailsUi.ui.paging.SimilarTvShowPageSource
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.SimilarMediaUI
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.ReviewUi
import com.paris_2.domain.authentication.usecase.IsLoggedInUseCase
import kotlinx.coroutines.flow.Flow

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
    private val getEpisodeVideoUseCase: GetEpisodeVideoUseCase,
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI,
    private val isLoggedInUseCase: IsLoggedInUseCase,
    private val addRatingToTvShowUseCase: AddRatingToTvShowUseCase,
) : TvShowScreenInteractionListener, BaseViewModel<TvShowDetailsScreenState>(
    TvShowDetailsScreenState()
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
            onError = ::onGetVideoError,
        )
    }

    private fun loadTvShowDetails(mediaId: Int) {

        tryToExecute(
            execute = { getTvShowDetailsUseCase(mediaId) },
            onSuccess = { tvShow -> handleTvShowDetailsSuccess(mediaId, tvShow) },
            onError = { error -> handleTvShowDetailsError(error) }
        )
    }

    private fun loadTvShowCast(mediaId: Int) {
        tryToExecute(
            execute = { getTvShowCastUseCase(mediaId) },
            onSuccess = ::handleTvShowCastSuccess,
            onError = ::handleTvShowCastError
        )
    }

    private fun loadTvShowGallery(mediaId: Int) {
        tryToExecute(
            execute = { getTvShowGalleryUseCase(mediaId) },
            onSuccess = ::handleTvShowGallerySuccess,
            onError = ::handleTvShowGalleryError
        )
    }


    private fun loadTvShowRecommendations(mediaId: Int) {
        tryToExecute(
            execute = { createTvShowRecommendationsPager(mediaId) },
            onSuccess = ::handleTvShowRecommendationsSuccess,
            onError = ::handleTvShowRecommendationsError
        )
    }

    private fun loadTvShowReviews(mediaId: Int) {
        tryToExecute(
            execute = { createTvShowReviewsPager(mediaId) },
            onSuccess = ::handleTvShowReviewsSuccess,
            onError = ::handleTvShowReviewsError
        )
    }

    private fun loadTvShowsProductionCompanies(mediaId: Int) {
        tryToExecute(
            execute = { getTvShowProductionCompaniesUseCase(mediaId) },
            onSuccess = ::handleTvShowProductionCompaniesSuccess,
            onError = ::handleTvShowProductionCompaniesError
        )
    }

    override fun onFavouriteClick(title: Int) {
        tryToExecute(
            execute = { isLoggedInUseCase() },
            onSuccess = { isLoggedIn -> handleLoginCheck(isLoggedIn, title) },
            onError = ::handleError
        )
    }

    private fun handleLoginCheck(isLoggedIn: Boolean, title: Int) {
        if (isLoggedIn) {
            tryToExecute(
                execute = { addRatingToTvShowUseCase() },
                onSuccess = ::handleRatingSuccess,
                onError = ::handleError
            )
        } else {
            navigate(MediaDetailsDestinations.LoginDialogDestination(title))
        }
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

        if (currentSeason?.isExpanded == true && currentSeason.episodes.isNotEmpty()) return

        tryToExecute(
            execute = {
                setSeasonLoadingState(seasonNumber, isLoading = true)

                getSeasonDetailsUseCase(
                    screenState.value.tvShowDetailsUiState.tvShowUi.id,
                    seasonNumber
                )
            },
            onSuccess = { season ->
                handleSeasonDetailsSuccess(seasonNumber, season)
            },
            onError = { error ->
                handleSeasonDetailsError(seasonNumber, error)
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

    override fun onRatingSubmitted(rating: Float) {}

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

    private fun setSeasonLoadingState(seasonNumber: Int, isLoading: Boolean) {
        updateState(
            screenState.value.copy(
                seasonsLoadingStates = if (isLoading)
                    screenState.value.seasonsLoadingStates + (seasonNumber to true)
                else
                    screenState.value.seasonsLoadingStates - seasonNumber
            )
        )
    }

    private fun handleSeasonDetailsSuccess(seasonNumber: Int, season: Season) {
        updateState(
            screenState.value.copy(
                tvShowDetailsUiState = screenState.value.tvShowDetailsUiState.copy(
                    tvShowUi = screenState.value.tvShowDetailsUiState.tvShowUi.copy(
                        seasons = screenState.value.tvShowDetailsUiState.tvShowUi.seasons.map {
                            if (it.seasonNumber == seasonNumber) {
                                it.copy(
                                    isExpanded = true,
                                    episodes = season.episodes.toListOfEpisodeUi()
                                )
                            } else it
                        }
                    )
                ),
                seasonsLoadingStates = screenState.value.seasonsLoadingStates - seasonNumber
            )
        )
    }

    private fun handleSeasonDetailsError(seasonNumber: Int, error: String) {
        updateState(
            screenState.value.copy(
                errorMessage = error,
                seasonsLoadingStates = screenState.value.seasonsLoadingStates - seasonNumber
            )
        )
    }

    private fun handleTvShowDetailsSuccess(mediaId: Int, tvShow: TvShow) {
        updateTvShowUi(tvShow)
        loadAllTvShowData(mediaId)
    }

    private fun handleTvShowDetailsError(error: String) {
        updateState(
            screenState.value.copy(
                isLoading = false,
                errorMessage = error
            )
        )
    }

    private fun updateTvShowUi(tvShow: TvShow) {
        updateState(
            screenState.value.copy(
                tvShowDetailsUiState = screenState.value.tvShowDetailsUiState.copy(
                    tvShowUi = tvShow.toUi()
                ),
                isLoading = false,
                errorMessage = null
            )
        )
    }

    private fun loadAllTvShowData(mediaId: Int) {
        loadTvShowCast(mediaId)
        loadTvShowGallery(mediaId)
        loadTvShowRecommendations(mediaId)
        loadTvShowReviews(mediaId)
        loadTvShowsProductionCompanies(mediaId)
    }


    private fun handleTvShowCastSuccess(cast: List<Cast>) {
        val updatedUiState = screenState.value.tvShowDetailsUiState.copy(
            cast = cast.map { it.toUi() }
        )
        updateState(
            screenState.value.copy(
                tvShowDetailsUiState = updatedUiState
            )
        )
    }

    private fun handleTvShowCastError(error: String?) {
        updateState(
            screenState.value.copy(
                errorMessage = error
            )
        )
    }

    private fun handleTvShowGallerySuccess(gallery: Gallery) {
        val updatedUiState = screenState.value.tvShowDetailsUiState.copy(
            gallery = gallery.toUi()
        )
        updateState(
            screenState.value.copy(
                tvShowDetailsUiState = updatedUiState
            )
        )
    }

    private fun handleTvShowGalleryError(error: String?) {
        updateState(
            screenState.value.copy(
                errorMessage = error
            )
        )
    }

    private fun createTvShowRecommendationsPager(mediaId: Int): Flow<PagingData<SimilarMediaUI>> =
        Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                SimilarTvShowPageSource(
                    movieId = mediaId,
                    getTvShowRecommendationsUseCase = getTvShowRecommendationsUseCase
                )
            }
        ).flow.cachedIn(viewModelScope)

    private fun handleTvShowRecommendationsSuccess(recommendations: Flow<PagingData<SimilarMediaUI>>) {
        updateState(
            screenState.value.copy(
                tvShowDetailsUiState = screenState.value.tvShowDetailsUiState.copy(
                    recommendations = recommendations
                )
            )
        )
    }

    private fun handleTvShowRecommendationsError(error: String?) {
        updateState(
            screenState.value.copy(
                errorMessage = error
            )
        )
    }

    private fun createTvShowReviewsPager(mediaId: Int): Flow<PagingData<ReviewUi>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {
            ReviewTvShowPagingSource(
                mediaId = mediaId,
                getTvShowReviewsUseCase = getTvShowReviewsUseCase
            )
        }
    ).flow.cachedIn(viewModelScope)

    private fun handleTvShowReviewsSuccess(reviews: Flow<PagingData<ReviewUi>>) {
        updateState(
            screenState.value.copy(
                tvShowDetailsUiState = screenState.value.tvShowDetailsUiState.copy(
                    reviews = reviews
                )
            )
        )
    }

    private fun handleTvShowReviewsError(error: String) {
        updateState(
            screenState.value.copy(
                errorMessage = error
            )
        )
    }

    private fun handleTvShowProductionCompaniesSuccess(companies: List<ProductionCompany>) {
        updateState(
            screenState.value.copy(
                tvShowDetailsUiState = screenState.value.tvShowDetailsUiState.copy(
                    tvShowUi = screenState.value.tvShowDetailsUiState.tvShowUi.copy(
                        productionCompanies = companies.toListOfProductionCompanyUi()
                    )
                )
            )
        )
    }

    private fun handleTvShowProductionCompaniesError(error: String) {
        updateState(
            screenState.value.copy(
                errorMessage = error
            )
        )
    }

    private fun handleRatingSuccess(@Suppress("UNUSED_PARAMETER") result: Unit) {
        updateState(screenState.value.copy(showRatingDialog = true))
    }


    private fun handleError(error: String) {
        updateState(screenState.value.copy(errorMessage = error))
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

    private fun onGetVideoError(error: String) {
        updateState(
            screenState.value.copy(
                errorMessage = error
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
        }

        updateState(
            screenState.value.copy(
                showSnackBar = false,
                tvShowDetailsUiState = screenState.value.tvShowDetailsUiState.copy(episodeVideoUi = episodeVideo.toUi())
            )
        )
    }

    private fun onGetVideoEpisodeError(@Suppress("UNUSED_PARAMETER") error: String) {
        updateState(
            screenState.value.copy(
                snackBarMessage = R.string.not_found_video,
                showSnackBar = true
            )
        )
    }
}
