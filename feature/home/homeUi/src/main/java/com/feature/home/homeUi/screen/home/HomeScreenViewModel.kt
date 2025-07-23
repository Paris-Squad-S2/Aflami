package com.feature.home.homeUi.screen.home

import com.domain.home.usecase.AddMediaToLocalUseCase
import com.domain.home.usecase.FilterUpComingMediaByCategoriesUseCase
import com.domain.home.usecase.GetMediaFromLocalUseCase
import com.domain.home.usecase.GetMoviesCategoriesUseCase
import com.domain.home.usecase.GetPopularMediaUseCase
import com.domain.home.usecase.GetTopRatingMediaUseCase
import com.domain.home.usecase.GetUpComingMediaUseCase
import com.feature.home.homeApi.HomeDestinations
import com.feature.home.homeApi.toJson
import com.feature.home.homeUi.common.BaseViewModel
import com.feature.home.homeUi.mapper.nameToGenreId
import com.feature.home.homeUi.mapper.toCategoryUiList
import com.feature.home.homeUi.mapper.toMedia
import com.feature.home.homeUi.mapper.toMediaUiStateList
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsApi.toJson
import com.paris_2.aflami.appnavigation.AppDestinations
import com.paris_2.aflami.appnavigation.AppNavigator

class HomeScreenViewModel(
    private val getPopularMediaUseCase: GetPopularMediaUseCase,
    private val getTopRatingMediaUseCase: GetTopRatingMediaUseCase,
    private val getMoviesCategoriesUseCase: GetMoviesCategoriesUseCase,
    private val filterUpComingMediaByCategoriesUseCase: FilterUpComingMediaByCategoriesUseCase,
    private val getUpcomingMediaUseCase: GetUpComingMediaUseCase,
    private val addMediaToLocalDatabaseUseCase: AddMediaToLocalUseCase,
    private val getMediaFromLocalUseCase: GetMediaFromLocalUseCase,
    private val appNavigator: AppNavigator,
) : HomeScreenInteractionListener,
    BaseViewModel<HomeScreenUIState>(
        HomeScreenUIState(
            homeUIState = HomeUIState(
                popularMediaList = emptyList(),
                continueWatchingMediaList = emptyList(),
                topRatedMediaList = emptyList(),
                moviesBirthdayMediaList = emptyList(),
                categories = mapOf(),
                upComingMediaList = emptyList(),
                showMoodPickerDialog = false,
                isAllCategories = true,
                moodPickerMovie = MediaUiState(
                    id = 0,
                    title = "",
                    imageUri = "",
                    type = MediaTypeUi.MOVIE,
                    categories = emptyList(),
                    yearOfRelease = kotlinx.datetime.LocalDate(2023, 1, 1),
                    rating = 0.0,
                )
            ),
            isLoading = false,
            errorMessage = null
        )
    ) {
    init {
        loadPopularMedia()
        loadTopRatingMedia()
        loadContinueWatchingMedia()
        loadCategories()
        onAllCategoriesSelect()
    }

    private fun loadCategories() {
        tryToExecute(
            execute = getMoviesCategoriesUseCase::invoke,
            onSuccess = { categories ->
                emitState(
                    screenState.value.copy(
                        homeUIState = screenState.value.homeUIState.copy(
                            categories = categories.toCategoryUiList().associateWith { false }
                                .toMutableMap()
                        )
                    )
                )
            },
            onError = { errorMessage ->
                emitState(
                    screenState.value.copy(
                        errorMessage = errorMessage
                    )
                )
            }
        )
    }

    private fun loadPopularMedia() {
        tryToExecute(
            execute = getPopularMediaUseCase::invoke,
            onSuccess = {
                emitState(
                    screenState.value.copy(
                        homeUIState = screenState.value.homeUIState.copy(
                            popularMediaList = it.toMediaUiStateList()
                        )
                    )
                )
            },
            onError = { errorMessage ->
                emitState(
                    screenState.value.copy(
                        errorMessage = errorMessage
                    )
                )
            }
        )
    }

    private fun loadTopRatingMedia() {
        tryToExecute(
            execute = getTopRatingMediaUseCase::invoke,
            onSuccess = { topRatingMedia ->
                emitState(
                    screenState.value.copy(
                        homeUIState = screenState.value.homeUIState.copy(
                            topRatedMediaList = topRatingMedia.toMediaUiStateList()
                        )
                    )
                )
            },
            onError = { errorMessage ->
                emitState(
                    screenState.value.copy(
                        errorMessage = errorMessage
                    )
                )
            }
        )
    }

    private fun loadContinueWatchingMedia() {
        tryToExecute(
            execute = getMediaFromLocalUseCase::invoke,
            onSuccess = { mediaList ->
                emitState(
                    screenState.value.copy(
                        homeUIState = screenState.value.homeUIState.copy(
                            continueWatchingMediaList = mediaList.toMediaUiStateList()
                        )
                    )
                )
            },
            onError = {}
        )
    }

    override fun onAllCategoriesSelect() {
        tryToExecute(
            execute = getUpcomingMediaUseCase::invoke,
            onSuccess = { upcomingMovies ->
                emitState(
                    screenState.value.copy(
                        homeUIState = screenState.value.homeUIState.copy(
                            upComingMediaList = upcomingMovies.toMediaUiStateList(),
                            categories = screenState.value.homeUIState.categories.toMutableMap()
                                .apply {
                                    this.keys.forEach { this[it] = false }
                                },
                        )
                    )
                )
            },
            onError = { errorMessage ->
                emitState(
                    screenState.value.copy(
                        errorMessage = errorMessage
                    )
                )
            }
        )
    }

    override fun onSearchIconClick() {
        tryToExecute(
            execute = {
                appNavigator.navigate(
                    destination = AppDestinations.SearchFeature()
                )
            },
            onError = {}
        )
    }

    override fun onMediaCardClick(media: MediaUiState) {
        tryToExecute(
            execute = {
                addMediaToLocalDatabaseUseCase.invoke(media.toMedia())
                loadContinueWatchingMedia()
                appNavigator.navigate(
                    AppDestinations.MediaDetailsFeature(
                        when (media.type) {
                            MediaTypeUi.MOVIE -> MediaDetailsDestinations.MovieDetailsScreen(
                                movieId = media.id
                            )

                            MediaTypeUi.TVSHOW -> MediaDetailsDestinations.TvShowDetailsScreen(
                                tvShowId = media.id
                            )
                        }.toJson()
                    )
                )
            },
            onError = { errorMessage ->
                emitState(
                    screenState.value.copy(
                        errorMessage = errorMessage
                    )
                )
            }
        )
    }

    override fun navigateToContinueWatchingScreen() {
        tryToExecute(
            execute = {
                appNavigator.navigate(
                    destination = AppDestinations.HomeFeature(
                        homeDestination = HomeDestinations.ContinueWatchingScreen.toJson(),
                    )
                )
            },
            onError = {}
        )
    }

    override fun navigateToTopRatingScreen() {
        tryToExecute(
            execute = {
                appNavigator.navigate(
                    destination = AppDestinations.HomeFeature(
                        homeDestination = HomeDestinations.TopRatingMoviesScreen.toJson(),
                    )
                )
            },
            onError = {}
        )
    }


    override fun getRandomMoodPickerMovie() {
        val movies = screenState.value.homeUIState.upComingMediaList
        emitState(
            screenState.value.copy(
                homeUIState = screenState.value.homeUIState.copy(
                    moodPickerMovie = movies.random(),
                )
            )
        )
    }

    override fun moodPickerSelected(mood: List<String>) {
        tryToExecute(
            execute = {
                val moodCategories = mood.map { mood ->
                    mood.nameToGenreId()
                }
                filterUpComingMediaByCategoriesUseCase.invoke(moodCategories)
            },
            onSuccess = { filteredMovies ->
                emitState(
                    screenState.value.copy(
                        homeUIState = screenState.value.homeUIState.copy(
                            upComingMediaList = filteredMovies.toMediaUiStateList(),
                            moodPickerMovie = filteredMovies.toMediaUiStateList().random(),
                            showMoodPickerDialog = true
                        )
                    )
                )
            },
            onError = { errorMessage ->
                emitState(
                    screenState.value.copy(
                        errorMessage = errorMessage
                    )
                )
            }
        )

    }

    override fun onCategorySelect(category: CategoryUiState) {
        tryToExecute(
            execute = {
                emitState(
                    screenState.value.copy(
                        homeUIState = screenState.value.homeUIState.copy(
                            categories = screenState.value.homeUIState.categories.toMutableMap()
                                .apply {
                                    this[category] = !(this[category] ?: false)
                                }
                        )
                    )
                )
                filterUpComingMediaByCategoriesUseCase.invoke(
                    screenState.value.homeUIState.categories
                        .filter { it.value }
                        .keys
                        .map { it.id })
            },
            onSuccess = { filteredMovies ->
                emitState(
                    screenState.value.copy(
                        homeUIState = screenState.value.homeUIState.copy(
                            upComingMediaList = filteredMovies.toMediaUiStateList(),
                        )
                    )
                )
            },
            onError = { errorMessage ->
                emitState(
                    screenState.value.copy(
                        errorMessage = errorMessage
                    )
                )
            }
        )
    }

    override fun onDismissMoodPicker() {
        emitState(
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
