package com.feature.search.searchUi.screen.search

import androidx.lifecycle.viewModelScope
import com.domain.search.model.CategoryModel
import com.domain.search.model.Media
import com.domain.search.model.MediaType
import com.domain.search.model.SearchHistoryModel
import com.domain.search.useCases.AddRecentSearchUseCase
import com.domain.search.useCases.ClearAllRecentSearchesUseCase
import com.domain.search.useCases.ClearRecentSearchUseCase
import com.domain.search.useCases.FilterByListOfCategoriesUseCase
import com.domain.search.useCases.FilterMediaByRatingUseCase
import com.domain.search.useCases.GetAllCategoriesUseCase
import com.domain.search.useCases.GetAllRecentSearchesUseCase
import com.domain.search.useCases.SearchByQueryUseCase
import com.feature.search.searchUi.comon.BaseViewModel
import com.feature.search.searchUi.navigation.Destinations
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
    val selectedRating: Float
)

class SearchViewModel(
    private val getAllRecentSearchesUseCase: GetAllRecentSearchesUseCase,
    private val clearAllRecentSearchesUseCase: ClearAllRecentSearchesUseCase,
    private val clearRecentSearchUseCase: ClearRecentSearchUseCase,
    private val searchByQueryUseCase: SearchByQueryUseCase,
    private val addRecentSearchesUseCase: AddRecentSearchUseCase,
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
                tvShowsResult = listOf()
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
                emitState(
                    screenState.value.copy(
                        uiState = screenState.value.uiState.copy(
                            recentSearches = recentSearches
                        )
                    )
                )
                loadCategories()
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
                            categories = categories.associateWith { true }.toMutableMap()
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
            Destinations.WorldTourScreen(
                name = "World Tour"
            )
        )
    }

    override fun onNavigateToFindByActorScreen() {
        navigate(
            Destinations.FindByActorScreen(
                name = "Find By Actor"
            )
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
        if (query.isNotEmpty()) {
            debounceJob = viewModelScope.launch {
                delay(500)
                searchQuery(query)
            }
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
                    moviesResult
                )
                val filteredMediaByCategories = filterMedByListOfCategoriesUseCase(
                    screenState.value.uiState.categories.filter { it.value }.keys.toList()
                        .map { it.id },
                    filteredMediaByRating
                )

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
                addRecentSearchesUseCase(query)
                loadRecentSearches()
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
                                .toMutableMap()
                        )
                    )
                )

                val filteredMovies = filterMediaByRatingUseCase(
                    screenState.value.uiState.selectedRating,
                    screenState.value.uiState.moviesResult
                )
                val filteredTvShows = filterMediaByRatingUseCase(
                    screenState.value.uiState.selectedRating,
                    screenState.value.uiState.tvShowsResult
                )
                val filteredByCategoriesMovies = filterMedByListOfCategoriesUseCase(
                    selectedCategories.map { it.id },
                    filteredMovies
                )
                val filteredByCategoriesTvShows = filterMedByListOfCategoriesUseCase(
                    selectedCategories.map { it.id },
                    filteredTvShows
                )
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
                    categories = screenState.value.uiState.categories.mapValues { true }
                        .toMutableMap(),
                    filteredMoviesResult = screenState.value.uiState.moviesResult,
                    filteredTvShowsResult = screenState.value.uiState.tvShowsResult
                )
            )
        )
    }

    override fun onClearAllRecentSearches() {
        tryToExecute(
            execute = clearAllRecentSearchesUseCase::invoke,
            onSuccess = {
                loadRecentSearches()
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

    override fun onClearRecentSearch(id: Long) {
        tryToExecute(
            execute = {
                clearRecentSearchUseCase(id)
            },
            onSuccess = {
                loadRecentSearches()
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
        //TODO: Navigate to media details screen
    }
}

