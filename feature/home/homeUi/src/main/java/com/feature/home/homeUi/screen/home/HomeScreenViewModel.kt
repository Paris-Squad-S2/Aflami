package com.feature.home.homeUi.screen.home

import com.domain.home.usecase.AddMediaToLocalUseCase
import com.domain.home.usecase.FilterUpComingMediaByCategoriesUseCase
import com.domain.home.usecase.GetMediaFromLocalUseCase
import com.domain.home.usecase.GetMoviesCategoriesUseCase
import com.domain.home.usecase.GetPopularMediaUseCase
import com.domain.home.usecase.GetTopRatingMediaUseCase
import com.domain.home.usecase.GetUpComingMediaUseCase
import com.feature.home.homeApi.HomeDestinations
import com.feature.home.homeUi.common.BaseViewModel
import com.feature.home.homeUi.mapper.nameToGenreId
import com.feature.home.homeUi.mapper.toCategoryUiList
import com.feature.home.homeUi.mapper.toMedia
import com.feature.home.homeUi.mapper.toMediaUiStateList
import com.feature.home.homeUi.mapper.toSliderMediaList
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.search.searchApi.SearchFeatureAPI
import com.paris_2.aflami.designsystem.components.SliderMedia
import com.paris_2.aflami.designsystem.components.SliderMediaTypeUi

class HomeScreenViewModel(
    private val getPopularMediaUseCase: GetPopularMediaUseCase,
    private val getTopRatingMediaUseCase: GetTopRatingMediaUseCase,
    private val getMoviesCategoriesUseCase: GetMoviesCategoriesUseCase,
    private val filterUpComingMediaByCategoriesUseCase: FilterUpComingMediaByCategoriesUseCase,
    private val getUpcomingMediaUseCase: GetUpComingMediaUseCase,
    private val addMediaToLocalDatabaseUseCase: AddMediaToLocalUseCase,
    private val getMediaFromLocalUseCase: GetMediaFromLocalUseCase,
    private val searchFeatureAPI: SearchFeatureAPI,
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI
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
        )
    ) {
    init {
        loadPopularMedia()
        loadTopRatingMedia()
        loadContinueWatchingMedia()
        loadCategories()
        onAllCategoriesSelect()
    }

     fun onRetry(){
        loadPopularMedia()
        loadTopRatingMedia()
        loadContinueWatchingMedia()
        loadCategories()
        onAllCategoriesSelect()
    }

    private fun loadCategories() {
        tryToExecute(
            execute ={
                emitState(
                    screenState.value.copy(
                        isCategoryLoading= true
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
                        isCategoryLoading = false
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
                        isPopularMediaLoading = false
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
            execute ={
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
                        isTopRatingLoading = false
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
            execute ={
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
                        isContinueWatchingLoading = false
                    )
                )
            },
            onError = {errorMessage ->
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
            execute ={
                emitState(
                    screenState.value.copy(
                        isCategoryLoading = true
                    )
                )
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
                        isContinueWatchingLoading = false
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

    override fun onSearchIconClick() {
        tryToExecute(
            execute = {
                searchFeatureAPI()
            },
            onError = {errorMessage ->
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

    override fun onMediaSliderClick(media: SliderMedia){
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
        ))
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
                filterUpComingMediaByCategoriesUseCase.invoke(moodCategories)
            },
            onSuccess = { filteredMovies ->
                emitState(
                    screenState.value.copy(
                        homeUIState = screenState.value.homeUIState.copy(
                            upComingMediaList = filteredMovies.toMediaUiStateList(),
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
                        isCategoryLoading = true
                    )
                )
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
                        isCategoryLoading = false
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