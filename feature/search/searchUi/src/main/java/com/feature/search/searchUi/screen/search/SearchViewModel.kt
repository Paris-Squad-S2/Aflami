package com.feature.search.searchUi.screen.search

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.domain.search.model.Media
import com.domain.search.model.MediaType
import com.domain.search.model.SearchHistoryModel
import com.domain.search.useCases.AddRecentSearchUseCase
import com.domain.search.useCases.ClearAllRecentSearchesUseCase
import com.domain.search.useCases.ClearRecentSearchUseCase
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
)

class SearchViewModel(
    private val getAllRecentSearchesUseCase: GetAllRecentSearchesUseCase,
    private val clearAllRecentSearchesUseCase: ClearAllRecentSearchesUseCase,
    private val clearRecentSearchUseCase: ClearRecentSearchUseCase,
    private val searchByQueryUseCase: SearchByQueryUseCase,
    private val addRecentSearchesUseCase: AddRecentSearchUseCase
) : SearchScreenInteractionListener,
    BaseViewModel<SearchScreenState>(
        SearchScreenState(
            uiState = UIState(
                searchQuery = "",
                showFilterDialog = false,
                recentSearches = listOf(),
                moviesResult = listOf(),
                tvShowsResult = listOf(),
                selectedTabIndex = 0
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
                Log.d("TAG1", "loadRecentSearches: $recentSearches")
                clearAllRecentSearchesUseCase() //TODO: should Be removed after testing
                Log.d("TAG2", "loadRecentSearches: $recentSearches")
                // TODO: What is happening here? 😂
                emitState(
                    screenState.value.copy(
                        uiState = screenState.value.uiState.copy(
                            recentSearches = recentSearches
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
                emitState(
                    screenState.value.copy(
                        isLoading = false,
                        uiState = screenState.value.uiState.copy(
                            moviesResult = searchResult.filter { it.type == MediaType.MOVIE },
                            tvShowsResult = searchResult.filter { it.type == MediaType.TVSHOW },
                        )
                    )
                )
                addRecentSearchesUseCase(query)
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

    override fun onClearAllRecentSearches() {
        tryToExecute(
            execute = clearAllRecentSearchesUseCase::invoke,
            onSuccess = {
                loadRecentSearches() //TODO: there is somthing wrong in updating the RecentSearches again after delete
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
                loadRecentSearches() //TODO: there is somthing wrong in updating the RecentSearches again after delete
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

