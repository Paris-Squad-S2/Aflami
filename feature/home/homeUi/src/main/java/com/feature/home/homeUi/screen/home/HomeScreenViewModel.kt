package com.feature.home.homeUi.screen.home

import androidx.lifecycle.viewModelScope
import com.feature.home.homeUi.common.BaseViewModel
import com.feature.home.homeUi.common.ContentRestriction
import com.feature.home.homeUi.mapper.toMediaUiStateList
import com.feature.home.homeUi.mapper.toSliderMediaList
import com.feature.home.homeUi.screen.home.components.SliderMedia
import com.feature.home.homeUi.screen.home.components.SliderMediaTypeUi
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.search.searchApi.SearchFeatureAPI
import com.paris_2.domain.media.entity.Category
import com.paris_2.domain.media.useCase.FilterUpComingMediaByCategoriesUseCase
import com.paris_2.domain.media.useCase.GetMoviesCategoriesUseCase
import com.paris_2.domain.media.useCase.GetPopularMediaUseCase
import com.paris_2.domain.media.useCase.GetTopRatingMediaUseCase
import com.paris_2.domain.media.useCase.GetUpComingMediaUseCase
import com.paris_2.domain.media.useCase.GetWatchHistoryUseCase
import com.paris_2.domain.user.usecase.SettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getPopularMediaUseCase: GetPopularMediaUseCase,
    private val getTopRatingMediaUseCase: GetTopRatingMediaUseCase,
    private val getMoviesCategoriesUseCase: GetMoviesCategoriesUseCase,
    private val filterUpComingMediaByCategoriesUseCase: FilterUpComingMediaByCategoriesUseCase,
    private val getUpcomingMediaUseCase: GetUpComingMediaUseCase,
    private val getWatchHistoryUseCase: GetWatchHistoryUseCase,
    private val searchFeatureAPI: SearchFeatureAPI,
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI,
    private val settingsUseCase: SettingsUseCase,
) : HomeScreenInteractionListener,
    BaseViewModel<HomeScreenUIState>(
        HomeScreenUIState()
    ) {
    init {
        getRestriction()
        loadPopularMedia()
        loadTopRatingMedia()
        loadContinueWatchingMedia()
        loadCategories()
        onAllCategoriesSelect()
    }

    override fun onRetry() {
        loadPopularMedia()
        loadTopRatingMedia()
        loadContinueWatchingMedia()
        loadCategories()
        onAllCategoriesSelect()
    }

    private fun getRestriction() {
        viewModelScope.launch {
            val restriction = settingsUseCase.getRestriction()
            updateState(
                screenState.value.copy(
                    homeUIState = screenState.value.homeUIState.copy(
                        contentRestriction = ContentRestriction.valueOf(restriction)
                    ),
                )
            )
            when (screenState.value.homeUIState.contentRestriction) {
                ContentRestriction.Strict -> updateState(
                    screenState.value.copy(
                        homeUIState = screenState.value.homeUIState.copy(
                            nsfwThreshold = 0.8f,
                            genderThreshold = 0.6f
                        ),
                    )
                )

                ContentRestriction.Moderate -> updateState(
                    screenState.value.copy(
                        homeUIState = screenState.value.homeUIState.copy(
                            nsfwThreshold = 0.4f,
                            genderThreshold = 0.6f
                        ),
                    )
                )

                ContentRestriction.Off -> updateState(
                    screenState.value.copy(
                        homeUIState = screenState.value.homeUIState.copy(
                            nsfwThreshold = 0f,
                            genderThreshold = 0f
                        ),
                    )
                )
            }
        }
    }

    private fun loadCategories() {
        tryToExecute(
            execute = {
                updateState(
                    screenState.value.copy(
                        isCategoryLoading = true
                    )
                )
                getMoviesCategoriesUseCase()
            },
            onSuccess = { categories ->
                updateState(
                    screenState.value.copy(
                        homeUIState = screenState.value.homeUIState.copy(
                            categories = categories.associateWith { false }
                                .toMutableMap()
                        ),
                        isCategoryLoading = false,
                        errorMessage = null
                    )
                )
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        errorMessage = errorMessage,
                        isCategoryLoading = false
                    )
                )
            }
        )
    }

    private fun loadPopularMedia() {
        tryToExecute(
            execute = {
                updateState(
                    screenState.value.copy(
                        isPopularMediaLoading = true
                    )
                )
                getPopularMediaUseCase()
            },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        homeUIState = screenState.value.homeUIState.copy(
                            popularMediaList = it.toSliderMediaList()
                        ),
                        isPopularMediaLoading = false,
                        errorMessage = null

                    )
                )
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        errorMessage = errorMessage,
                        isPopularMediaLoading = false
                    )
                )
            }
        )
    }

    private fun loadTopRatingMedia() {
        tryToExecute(
            execute = {
                updateState(
                    screenState.value.copy(
                        isTopRatingLoading = true
                    )
                )
                getTopRatingMediaUseCase()
            },
            onSuccess = { topRatingMedia ->
                updateState(
                    screenState.value.copy(
                        homeUIState = screenState.value.homeUIState.copy(
                            topRatedMediaList = topRatingMedia.toMediaUiStateList()
                        ),
                        isTopRatingLoading = false,
                        errorMessage = null

                    )
                )
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        errorMessage = errorMessage,
                        isTopRatingLoading = false
                    )
                )
            }
        )
    }

    private fun loadContinueWatchingMedia() {
        tryToExecute(
            execute = {
                updateState(
                    screenState.value.copy(
                        isContinueWatchingLoading = true
                    )
                )
                getWatchHistoryUseCase()
            },
            onSuccess = { mediaListFlow ->
                mediaListFlow.collect { mediaList ->
                    updateState(
                        screenState.value.copy(
                            homeUIState = screenState.value.homeUIState.copy(
                                continueWatchingMediaList = mediaList.toMediaUiStateList()
                            ),
                            isContinueWatchingLoading = false,
                            errorMessage = null

                        )
                    )
                }
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        errorMessage = errorMessage,
                        isContinueWatchingLoading = false
                    )
                )
            }
        )
    }

    override fun onAllCategoriesSelect() {
        tryToExecute(
            execute = {
                getUpcomingMediaUseCase()
            },
            onSuccess = { upcomingMovies ->
                updateState(
                    screenState.value.copy(
                        homeUIState = screenState.value.homeUIState.copy(
                            upComingMediaList = upcomingMovies.toMediaUiStateList(),
                            categories = screenState.value.homeUIState.categories.toMutableMap()
                                .apply {
                                    this.keys.forEach { this[it] = false }
                                },
                        ),
                        errorMessage = null

                    )
                )
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        errorMessage = errorMessage,
                    )
                )
            }
        )
    }

    override fun onSearchIconClick() {
        tryToExecute(
            execute = {
                searchFeatureAPI()
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        errorMessage = errorMessage,
                    )
                )
            }
        )
    }

    override fun onMediaCardClick(media: MediaUiState) {
        when (media.type) {
            MediaTypeUi.MOVIE -> mediaDetailsFeatureAPI.startMovieDetails(
                movieId = media.id
            )

            MediaTypeUi.TV_SHOW -> mediaDetailsFeatureAPI.startTvShowDetails(
                tvShowId = media.id
            )
        }
    }

    override fun onMediaSliderClick(media: SliderMedia) {
        tryToExecute(
            execute = {
                when (media.type) {
                    SliderMediaTypeUi.Movie -> mediaDetailsFeatureAPI.startMovieDetails(
                        movieId = media.id
                    )

                    SliderMediaTypeUi.TvShow -> mediaDetailsFeatureAPI.startTvShowDetails(
                        tvShowId = media.id
                    )
                }
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

    override fun getRandomMoodPickerMovie() {
        val movies = screenState.value.homeUIState.moodPickerFilteredMovies
        if (movies.isNotEmpty()) {
            updateState(
                screenState.value.copy(
                    homeUIState = screenState.value.homeUIState.copy(
                        moodPickerMovie = movies.random(),
                    )
                )
            )
        }
    }

    override fun moodPickerSelected(mood: List<Category>) {
        tryToExecute(
            execute = {
                getTopRatingMediaUseCase().filter { movie ->
                    movie.categories.any { mood.contains(it) }
                }
            },
            onSuccess = { filteredMovies ->
                updateState(
                    screenState.value.copy(
                        homeUIState = screenState.value.homeUIState.copy(
                            moodPickerFilteredMovies = filteredMovies.toMediaUiStateList() ,
                            moodPickerMovie = filteredMovies.toMediaUiStateList().random(),
                            showMoodPickerDialog = true
                        ),
                    )
                )
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        errorMessage = errorMessage,
                    )
                )
            }
        )

    }

    override fun onCategorySelect(category: Category) {
        tryToExecute(
            execute = {
                updateState(
                    screenState.value.copy(
                        homeUIState = screenState.value.homeUIState.copy(
                            categories = screenState.value.homeUIState.categories.toMutableMap()
                                .apply {
                                    this[category] = !(this[category] ?: false)
                                }
                        )
                    )
                )
                filterUpComingMediaByCategoriesUseCase(
                    screenState.value.homeUIState.categories
                        .filter { it.value }
                        .keys
                        .toList()
                )
            },
            onSuccess = { filteredMovies ->
                updateState(
                    screenState.value.copy(
                        homeUIState = screenState.value.homeUIState.copy(
                            upComingMediaList = filteredMovies.toMediaUiStateList(),
                        ),
                    )
                )
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        errorMessage = errorMessage,
                    )
                )
            }
        )
    }

    override fun onDismissMoodPicker() {
        updateState(
            screenState.value.copy(
                homeUIState = screenState.value.homeUIState.copy(
                    showMoodPickerDialog = false,
                    moodPickerMovie = MediaUiState(
                        id = 0,
                        title = "",
                        imageUri = "",
                        type = MediaTypeUi.MOVIE,
                        categories = emptyList(),
                        yearOfRelease = kotlinx.datetime.LocalDate(2023, 1, 1),
                        rating = 0.0,
                    )
                )
            )
        )
    }
}