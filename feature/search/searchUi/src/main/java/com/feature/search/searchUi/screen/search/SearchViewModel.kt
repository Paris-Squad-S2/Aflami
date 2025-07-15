package com.feature.search.searchUi.screen.search

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.domain.search.model.CategoryModel
import com.domain.search.model.Media
import com.domain.search.model.MediaType
import com.domain.search.model.SearchHistoryModel
import com.domain.search.model.SearchType
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
import com.feature.search.searchUi.comon.BaseViewModel
import com.paris_2.aflami.appnavigation.AppDestinations
import com.paris_2.aflami.appnavigation.AppNavigator
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class SearchScreenState(
    val uiState: UIState,
    val isLoading: Boolean,
    val errorMessage: String?
)

data class UIState(
    val searchQuery: String,
    val showFilterDialog: Boolean,
    val recentSearches: List<SearchHistoryModel>,
    val selectedTabIndex: Int,
    val moviesResult: List<Media>,
    val tvShowsResult: List<Media>,
    val filteredMoviesResult: List<Media>,
    val filteredTvShowsResult: List<Media>,
    val categories: Map<CategoryModel, Boolean>,
    val selectedRating: Float,
    val isAllCategories: Boolean
)

class SearchViewModel(
    private val getAllRecentSearchesUseCase: GetAllRecentSearchesUseCase,
    private val clearAllRecentSearchesUseCase: ClearAllRecentSearchesUseCase,
    private val clearRecentSearchUseCase: ClearRecentSearchUseCase,
    private val searchByQueryUseCase: SearchByQueryUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val filterMediaByRatingUseCase: FilterMediaByRatingUseCase,
    private val filterMedByListOfCategoriesUseCase: FilterByListOfCategoriesUseCase,
) : SearchScreenInteractionListener,
    BaseViewModel<SearchScreenState>(
        SearchScreenState(
            uiState = UIState(
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

    val appNavigator: AppNavigator = getKoin().get()

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
                            uiState = screenState.value.uiState.copy(
                                recentSearches = recentSearchesList
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

    private fun loadCategories() {
        tryToExecute(
            execute = getAllCategoriesUseCase::invoke,
            onSuccess = { categories ->
                emitState(
                    screenState.value.copy(
                        uiState = screenState.value.uiState.copy(
                            categories = categories.associateWith { false }.toMutableMap()
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
                uiState = screenState.value.uiState.copy(
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
                val moviesResult = searchResult.filter { it.type == MediaType.MOVIE }
                val tvShowsResult = searchResult.filter { it.type == MediaType.TVSHOW }

                val filteredMediaByRating = filterMediaByRatingUseCase(
                    screenState.value.uiState.selectedRating,
                    searchResult
                )
                val filteredMediaByCategories =
                    if (!screenState.value.uiState.isAllCategories) filterMedByListOfCategoriesUseCase(
                        screenState.value.uiState.categories.filter { it.value }.keys.toList()
                            .map { it.id },
                        filteredMediaByRating
                    ) else searchResult

                val filteredMoviesResult =
                    filteredMediaByCategories.filter { it.type == MediaType.MOVIE }
                val filteredTvShowsResult =
                    filteredMediaByCategories.filter { it.type == MediaType.TVSHOW }
                emitState(
                    screenState.value.copy(
                        isLoading = false,
                        uiState = screenState.value.uiState.copy(
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
                uiState = screenState.value.uiState.copy(
                    selectedTabIndex = tabIndex
                )
            )
        )
    }

    override fun onFilterButtonClick() {
        emitState(
            screenState.value.copy(
                uiState = screenState.value.uiState.copy(
                    showFilterDialog = !screenState.value.uiState.showFilterDialog
                )
            )
        )
    }

    override fun onApplyFilterButtonClick(
        selectedRating: Float,
        isAllCategories: Boolean,
        selectedCategories: List<CategoryModel>,
    ) {
        tryToExecute(
            execute = {
                emitState(
                    screenState.value.copy(
                        isLoading = true,
                        uiState = screenState.value.uiState.copy(
                            showFilterDialog = false,
                            selectedRating = selectedRating,
                            categories = screenState.value.uiState.categories.mapValues { it.key in selectedCategories }
                                .toMutableMap(),
                            isAllCategories = isAllCategories
                        )
                    )
                )

                val filteredMovies = filterMediaByRatingUseCase(
                    selectedRating,
                    screenState.value.uiState.moviesResult
                )
                val filteredTvShows = filterMediaByRatingUseCase(
                    selectedRating,
                    screenState.value.uiState.tvShowsResult
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
                        uiState = screenState.value.uiState.copy(
                            filteredMoviesResult = filteredByCategoriesMovies,
                            filteredTvShowsResult = filteredByCategoriesTvShows
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
                uiState = screenState.value.uiState.copy(
                    showFilterDialog = false,
                    selectedRating = 0f,
                    isAllCategories = true,
                    categories = screenState.value.uiState.categories.mapValues { false }
                        .toMutableMap(),
                    filteredMoviesResult = screenState.value.uiState.moviesResult,
                    filteredTvShowsResult = screenState.value.uiState.tvShowsResult
                )
            )
        )
    }

    override fun onRecentSearchClick(searchTitle: String, searchType: SearchType) {
        when (searchType) {
            SearchType.Query -> onSearchQueryChange(searchTitle)
            SearchType.Country -> {
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

            SearchType.Actor -> {
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

    override fun onClearRecentSearch(id: String, searchType: SearchType) {
        tryToExecute(
            execute = {
                clearRecentSearchUseCase(id, searchType)
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
        //TODO: Implement navigation back to home feature
    }

    override fun onRetryRecentSearches() {
        loadRecentSearches()
    }

    override fun onRetrySearchQuery() {
        searchQuery(screenState.value.uiState.searchQuery)
    }

    override fun onMediaCardClick(id: Int) {
        tryToExecute(
            execute = {
                appNavigator.navigate(
                    AppDestinations.MediaDetailsFeature(
                        MediaDetailsDestinations.MediaDetailsScreen(
                            mediaId = id
                        ).toJson()
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