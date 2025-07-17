package com.feature.search.searchUi.screen.search

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.domain.search.useCases.ClearAllRecentSearchesUseCase
import com.domain.search.useCases.ClearRecentSearchUseCase
import com.domain.search.useCases.FilterByListOfCategoriesUseCase
import com.domain.search.useCases.FilterMediaByRatingUseCase
import com.domain.search.useCases.GetAllCategoriesUseCase
import com.domain.search.useCases.GetAllRecentSearchesUseCase
import com.domain.search.useCases.SearchByQueryUseCase
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsApi.toJson
import com.feature.search.searchApi.SearchDestinations
import com.feature.search.searchUi.R
import com.feature.search.searchUi.comon.BaseViewModel
import com.feature.search.searchUi.mapper.toDomainList
import com.feature.search.searchUi.mapper.toDomainModel
import com.feature.search.searchUi.mapper.toMediaUiList
import com.feature.search.searchUi.mapper.toSearchHistoryUiList
import com.feature.search.searchUi.mapper.toCategoryUiList
import com.paris_2.aflami.appnavigation.AppDestinations
import com.paris_2.aflami.appnavigation.AppNavigator
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import org.koin.mp.KoinPlatform.getKoin

data class SearchScreenState(
    val searchUiState: SearchUiState,
    val isLoading: Boolean,
    val errorMessage: String?
)


data class SearchUiState(
    val searchQuery: String,
    val showFilterDialog: Boolean,
    val recentSearches: List<SearchHistoryUiState>,
    val selectedTabIndex: Int,
    val moviesResult: List<MediaUiState>,
    val tvShowsResult: List<MediaUiState>,
    val filteredMoviesResult: List<MediaUiState>,
    val filteredTvShowsResult: List<MediaUiState>,
    val categories: Map<CategoryUiState, Boolean>,
    val selectedRating: Float,
    val isAllCategories: Boolean
)


data class MediaUiState(
    val id: Int,
    val imageUri: String,
    val title: String,
    val type: MediaTypeUi,
    val categories: List<Int>,
    val yearOfRelease: LocalDate,
    val rating: Double,
)

enum class MediaTypeUi(val mediaName: String){
    TVSHOW("TV Show"),
    MOVIE("Movie")
}

data class CategoryUiState(
    val id: Int,
    val name: String,
)

data class SearchHistoryUiState(
    val searchTitle: String,
    val searchDate:String,
    val searchType: SearchTypeUi
)

enum class SearchTypeUi(val displayNameResId: Int) {
    Query(R.string.query),
    Country(R.string.country),
    Actor(R.string.actor);
}

class SearchViewModel(
    private val getAllRecentSearchesUseCase: GetAllRecentSearchesUseCase,
    private val clearAllRecentSearchesUseCase: ClearAllRecentSearchesUseCase,
    private val clearRecentSearchUseCase: ClearRecentSearchUseCase,
    private val searchByQueryUseCase: SearchByQueryUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val filterMediaByRatingUseCase: FilterMediaByRatingUseCase,
    private val filterMedByListOfCategoriesUseCase: FilterByListOfCategoriesUseCase,
    private val appNavigator: AppNavigator = getKoin().get()
) : SearchScreenInteractionListener,
    BaseViewModel<SearchScreenState>(
        SearchScreenState(
            searchUiState = SearchUiState(
                searchQuery = "",
                showFilterDialog = false,
                recentSearches = listOf(),
                filteredMoviesResult = listOf(),
                filteredTvShowsResult = listOf(),
                selectedTabIndex = 0,
                categories = mapOf(),
                selectedRating = 0f,
                moviesResult = listOf(),
                tvShowsResult = listOf(),
                isAllCategories = true
            ),
            isLoading = false,
            errorMessage = null
        )
    ) {

    init {
        loadRecentSearches()
    }

    private fun loadRecentSearches() {
        tryToExecute(
            execute = getAllRecentSearchesUseCase::invoke,
            onSuccess = { recentSearches ->
                recentSearches.collect { recentSearchesList ->
                    emitState(
                        screenState.value.copy(
                            isLoading = false,
                            searchUiState = screenState.value.searchUiState.copy(
                                recentSearches = recentSearchesList.toSearchHistoryUiList()
                            )
                        )
                    )
                    loadCategories()
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

    fun loadCategories() {
        tryToExecute(
            execute = getAllCategoriesUseCase::invoke,
            onSuccess = { categories ->
                emitState(
                    screenState.value.copy(
                        searchUiState = screenState.value.searchUiState.copy(
                            categories = categories.toCategoryUiList().associateWith { false }.toMutableMap()
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

    override fun onNavigateToWorldTourScreen() {
        navigate(
            SearchDestinations.WorldTourScreen()
        )
    }

    override fun onNavigateToFindByActorScreen() {
        navigate(
            SearchDestinations.FindByActorScreen()
        )
    }

    private var debounceJob: Job? = null

    override fun onSearchQueryChange(query: String) {
        emitState(
            screenState.value.copy(
                searchUiState = screenState.value.searchUiState.copy(
                    searchQuery = query,
                )
            )
        )
        debounceJob?.cancel()
        if (query.isNotBlank()) {
            emitState(
                screenState.value.copy(
                    isLoading = true,
                )
            )
            debounceJob = viewModelScope.launch {
                delay(1000)
                searchQuery(query)
            }
        } else {
            emitState(
                screenState.value.copy(
                    isLoading = false,
                )
            )
        }
    }


    private fun searchQuery(query: String): Job {
        return tryToExecute(
            execute = {
                emitState(
                    screenState.value.copy(
                        isLoading = true,
                        errorMessage = null
                    )
                )
                searchByQueryUseCase(query)
            },
            onSuccess = { searchResult ->
                val moviesResult = searchResult.toMediaUiList().filter { it.type == MediaTypeUi.MOVIE }
                val tvShowsResult = searchResult.toMediaUiList().filter { it.type == MediaTypeUi.TVSHOW }

                val filteredMediaByRating = filterMediaByRatingUseCase(
                    screenState.value.searchUiState.selectedRating,
                    searchResult
                )
                val filteredMediaByCategories =
                    if (!screenState.value.searchUiState.isAllCategories) filterMedByListOfCategoriesUseCase(
                        screenState.value.searchUiState.categories.filter { it.value }.keys.toList()
                            .map { it.id },
                        filteredMediaByRating
                    ) else searchResult

                val filteredMoviesResult =
                    filteredMediaByCategories.toMediaUiList().filter { it.type == MediaTypeUi.MOVIE }
                val filteredTvShowsResult =
                    filteredMediaByCategories.toMediaUiList().filter { it.type == MediaTypeUi.TVSHOW }
                emitState(
                    screenState.value.copy(
                        isLoading = false,
                        searchUiState = screenState.value.searchUiState.copy(
                            moviesResult = moviesResult,
                            tvShowsResult = tvShowsResult,
                            filteredMoviesResult = filteredMoviesResult,
                            filteredTvShowsResult = filteredTvShowsResult,
                        )
                    )
                )
            },
            onError = { errorMessage ->
                emitState(
                    screenState.value.copy(
                        isLoading = false,
                        errorMessage = errorMessage
                    )
                )
            }
        )
    }

    override fun onSelectTab(tabIndex: Int) {
        emitState(
            screenState.value.copy(
                searchUiState = screenState.value.searchUiState.copy(
                    selectedTabIndex = tabIndex
                )
            )
        )
    }

    override fun onFilterButtonClick() {
        emitState(
            screenState.value.copy(
                searchUiState = screenState.value.searchUiState.copy(
                    showFilterDialog = !screenState.value.searchUiState.showFilterDialog
                )
            )
        )
    }

    override fun onApplyFilterButtonClick(
        selectedRating: Float,
        isAllCategories: Boolean,
        selectedCategories: List<CategoryUiState>,
    ) {
        tryToExecute(
            execute = {
                emitState(
                    screenState.value.copy(
                        isLoading = true,
                        searchUiState = screenState.value.searchUiState.copy(
                            showFilterDialog = false,
                            selectedRating = selectedRating,
                            categories = screenState.value.searchUiState.categories.mapValues { it.key in selectedCategories }
                                .toMutableMap(),
                            isAllCategories = isAllCategories
                        )
                    )
                )

                val filteredMovies = filterMediaByRatingUseCase(
                    selectedRating,
                    screenState.value.searchUiState.moviesResult.toDomainList()
                )
                val filteredTvShows = filterMediaByRatingUseCase(
                    selectedRating,
                    screenState.value.searchUiState.tvShowsResult.toDomainList()
                )
                val filteredByCategoriesMovies = if (isAllCategories) {
                    filteredMovies
                } else {
                    filterMedByListOfCategoriesUseCase(
                        selectedCategories.map { it.id },
                        filteredMovies
                    )
                }
                val filteredByCategoriesTvShows = if (isAllCategories) {
                    filteredTvShows
                } else {
                    filterMedByListOfCategoriesUseCase(
                        selectedCategories.map { it.id },
                        filteredTvShows
                    )
                }
                Pair(filteredByCategoriesMovies, filteredByCategoriesTvShows)
            },
            onSuccess = { (filteredByCategoriesMovies, filteredByCategoriesTvShows) ->
                emitState(
                    screenState.value.copy(
                        isLoading = false,
                        searchUiState = screenState.value.searchUiState.copy(
                            filteredMoviesResult = filteredByCategoriesMovies.toMediaUiList(),
                            filteredTvShowsResult = filteredByCategoriesTvShows.toMediaUiList()
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

    override fun onClearFilterClick() {
        emitState(
            screenState.value.copy(
                searchUiState = screenState.value.searchUiState.copy(
                    showFilterDialog = false,
                    selectedRating = 0f,
                    isAllCategories = true,
                    categories = screenState.value.searchUiState.categories.mapValues { false }
                        .toMutableMap(),
                    filteredMoviesResult = screenState.value.searchUiState.moviesResult,
                    filteredTvShowsResult = screenState.value.searchUiState.tvShowsResult
                )
            )
        )
    }

    override fun onRecentSearchClick(searchTitle: String, searchTypeUi: SearchTypeUi) {
        when (searchTypeUi) {
            SearchTypeUi.Query -> onSearchQueryChange(searchTitle)
            SearchTypeUi.Country -> {
                tryToExecute(
                    execute = {
                        navigate(
                            SearchDestinations.WorldTourScreen(
                                name = searchTitle
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

            SearchTypeUi.Actor -> {
                tryToExecute(
                    execute = {
                        navigate(
                            SearchDestinations.FindByActorScreen(
                                name = searchTitle
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
        }
    }

    override fun onClearAllRecentSearches() {
        tryToExecute(
            execute = clearAllRecentSearchesUseCase::invoke,
            onError = { errorMessage ->
                emitState(
                    screenState.value.copy(
                        errorMessage = errorMessage
                    )
                )
            }
        )
    }

    override fun onClearRecentSearch(id: String, searchTypeUi: SearchTypeUi) {
        tryToExecute(
            execute = {
                clearRecentSearchUseCase(id, searchTypeUi.toDomainModel())
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

    override fun onNavigateBack() {
        tryToExecute(
            execute = {
                appNavigator.navigateUp()
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

    override fun onRetryRecentSearches() {
        loadRecentSearches()
    }

    override fun onRetrySearchQuery() {
        searchQuery(screenState.value.searchUiState.searchQuery)
    }

    override fun onMediaCardClick(id: Int, mediaType: MediaTypeUi) {
        tryToExecute(
            execute = {
                appNavigator.navigate(
                    AppDestinations.MediaDetailsFeature(
                        when (mediaType) {
                            MediaTypeUi.MOVIE -> MediaDetailsDestinations.MovieDetailsScreen(
                                movieId = id
                            )
                            MediaTypeUi.TVSHOW -> MediaDetailsDestinations.TvShowDetailsScreen(
                                tvShowId = id
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
}