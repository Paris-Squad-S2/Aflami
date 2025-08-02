package com.feature.home.homeUi.screen.home

import com.paris_2.domain.media.useCase.AddMediaToLocalUseCase
import com.paris_2.domain.media.useCase.FilterUpComingMediaByCategoriesUseCase
import com.paris_2.domain.media.useCase.GetMediaFromLocalUseCase
import com.paris_2.domain.media.useCase.GetMoviesCategoriesUseCase
import com.paris_2.domain.media.useCase.GetPopularMediaUseCase
import com.paris_2.domain.media.useCase.GetTopRatingMediaUseCase
import com.paris_2.domain.media.useCase.GetUpComingMediaUseCase
import com.feature.home.homeUi.navigation.HomeDestinations
import com.feature.home.homeUi.common.BaseViewModel
import com.feature.home.homeUi.mapper.nameToGenreId
import com.feature.home.homeUi.mapper.toCategoryUiList
import com.feature.home.homeUi.mapper.toMedia
import com.feature.home.homeUi.mapper.toMediaUiStateList
import com.feature.home.homeUi.mapper.toSliderMediaList
import com.feature.home.homeUi.navigation.HomeNavigator
import com.feature.home.homeUi.screen.home.components.SliderMedia
import com.feature.home.homeUi.screen.home.components.SliderMediaTypeUi
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.search.searchApi.SearchFeatureAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getPopularMediaUseCase: GetPopularMediaUseCase,
    private val getTopRatingMediaUseCase: GetTopRatingMediaUseCase,
    private val getMoviesCategoriesUseCase: GetMoviesCategoriesUseCase,
    private val filterUpComingMediaByCategoriesUseCase: FilterUpComingMediaByCategoriesUseCase,
    private val getUpcomingMediaUseCase: GetUpComingMediaUseCase,
    private val addMediaToLocalDatabaseUseCase: AddMediaToLocalUseCase,
    private val getMediaFromLocalUseCase: GetMediaFromLocalUseCase,
    private val searchFeatureAPI: SearchFeatureAPI,
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI,
    navigator: HomeNavigator,
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
            isPopularMediaLoading = false,
            isTopRatingLoading = false,
            isContinueWatchingLoading = false,
            isCategoryLoading = false,
            errorMessage = null
        ), navigator
    ) {
    init {
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

    private fun loadCategories() {
        tryToExecute(
            execute = {
                emitState(
                    screenState.value.copy(
                        isCategoryLoading = true
                    )
                )
                getMoviesCategoriesUseCase.invoke()
            },
            onSuccess = { categories ->
                emitState(
                    screenState.value.copy(
                        homeUIState = screenState.value.homeUIState.copy(
                            categories = categories.toCategoryUiList().associateWith { false }
                                .toMutableMap()
                        ),
                        isCategoryLoading = false,
                        errorMessage = null
                    )
                )
            },
            onError = { errorMessage ->
                emitState(
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
                emitState(
                    screenState.value.copy(
                        isPopularMediaLoading = true
                    )
                )
                getPopularMediaUseCase.invoke()
            },
            onSuccess = {
                emitState(
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
                emitState(
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
                emitState(
                    screenState.value.copy(
                        isTopRatingLoading = true
                    )
                )
                getTopRatingMediaUseCase.invoke()
            },
            onSuccess = { topRatingMedia ->
                emitState(
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
                emitState(
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
                emitState(
                    screenState.value.copy(
                        isContinueWatchingLoading = true
                    )
                )
                getMediaFromLocalUseCase.invoke()
            },
            onSuccess = { mediaList ->
                emitState(
                    screenState.value.copy(
                        homeUIState = screenState.value.homeUIState.copy(
                            continueWatchingMediaList = mediaList.toMediaUiStateList()
                        ),
                        isContinueWatchingLoading = false,
                        errorMessage = null

                    )
                )
            },
            onError = { errorMessage ->
                emitState(
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
                getUpcomingMediaUseCase.invoke()
            },
            onSuccess = { upcomingMovies ->
                emitState(
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
                emitState(
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
                emitState(
                    screenState.value.copy(
                        errorMessage = errorMessage,
                    )
                )
            }
        )
    }

    override fun onMediaCardClick(media: MediaUiState) {
        tryToExecute(
            execute = {
                addMediaToLocalDatabaseUseCase.invoke(media.toMedia())
                loadContinueWatchingMedia()
                when (media.type) {
                    MediaTypeUi.MOVIE -> mediaDetailsFeatureAPI.startMovieDetails(
                        movieId = media.id
                    )

                    MediaTypeUi.TVSHOW -> mediaDetailsFeatureAPI.startTvShowDetails(
                        tvShowId = media.id
                    )
                }
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

    override fun onMediaSliderClick(media: SliderMedia) {
        tryToExecute(
            execute = {
                addMediaToLocalDatabaseUseCase.invoke(media.toMedia())
                loadContinueWatchingMedia()
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
                navigate(
                    destination = HomeDestinations.ContinueWatchingScreen,
                )
            },
            onError = { errorMessage ->
                emitState(
                    screenState.value.copy(
                        errorMessage = errorMessage,
                    )
                )
            }
        )
    }

    override fun navigateToTopRatingScreen() {
        tryToExecute(
            execute = {
                navigate(
                    destination = HomeDestinations.TopRatingMoviesScreen,
                )
            },
            onError = { errorMessage ->
                emitState(
                    screenState.value.copy(
                        errorMessage = errorMessage,
                    )
                )
            }
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
                emitState(
                    screenState.value.copy(
                    )
                )
                val moodCategories = mood.map { mood ->
                    mood.nameToGenreId()
                }
                val moodPickerMovies = getTopRatingMediaUseCase.invoke()
                moodPickerMovies.filter { movie ->
                    movie.categoryIds.any { moodCategories.contains(it) }
                }
            },
            onSuccess = { filteredMovies ->
                emitState(
                    screenState.value.copy(
                        homeUIState = screenState.value.homeUIState.copy(
                            moodPickerMovie = filteredMovies.toMediaUiStateList().random(),
                            showMoodPickerDialog = true
                        ),
                    )
                )
            },
            onError = { errorMessage ->
                emitState(
                    screenState.value.copy(
                        errorMessage = errorMessage,
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
                        ),
                    )
                )
            },
            onError = { errorMessage ->
                emitState(
                    screenState.value.copy(
                        errorMessage = errorMessage,
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