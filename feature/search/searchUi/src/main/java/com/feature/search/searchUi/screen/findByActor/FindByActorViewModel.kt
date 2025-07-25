package com.feature.search.searchUi.screen.findByActor

import MediaUiState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.domain.search.useCase.GetMediaByActorNameUseCase
import com.domain.search.useCase.IncrementCategoryInteractionUseCase
import com.domain.search.useCase.SortingMediaByCategoriesInteractionUseCase
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.search.searchUi.comon.BaseViewModel
import com.feature.search.searchUi.navigation.SearchDestinations
import com.feature.search.searchUi.pagging.FindByActorPagingSource
import com.feature.search.searchUi.screen.findByActor.FindByActorDefaults.initialFindByActorScreenState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class FindByActorViewModel(
    savedStateHandle: SavedStateHandle,
    private val getMediaByActorNameUseCase: GetMediaByActorNameUseCase,
    private val incrementCategoryInteractionUseCase: IncrementCategoryInteractionUseCase,
    private val sortingMediaByCategoriesInteractionUseCase: SortingMediaByCategoriesInteractionUseCase,
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI,
) : FindByActorScreenInteractionListener, BaseViewModel<FindByActorScreenState>(
    initialFindByActorScreenState()
) {

    init {
        val initialQuery = savedStateHandle.toRoute<SearchDestinations.FindByActorScreen>().name
        if (initialQuery != null) {
            onSearchQueryChange(initialQuery)
        }
    }

    override fun onNavigateBack() {
        navigateUp()
    }

    private var debounceJob: Job? = null


    override fun onSearchQueryChange(query: String) {
        updateSearchQuery(query)
        debounceJob?.cancel()

        if (query.isNotBlank()) {
            startDebouncedSearch(query)
        } else {
            clearSearchResults()
        }
    }

    private fun searchQuery(query: String): Job {
        return viewModelScope.launch {
            tryToExecute(
                execute = {
                    updateState(screenState.value.copy(errorMessage = null))
                    createSearchPager(query)
                },
                onSuccess = ::handleSearchSuccess,
                onError = ::handleSearchError
            )
        }
    }


    override fun onRetrySearchQuery() {
        searchQuery(screenState.value.uiState.searchQuery)
    }

    override fun onMediaCardClick(media: MediaUiState) {
        tryToExecute(
            execute = {
                incrementCategoryInteractionUseCase.invoke(media.categories)
                mediaDetailsFeatureAPI.startMovieDetails(movieId = media.id)
            },
            onError = ::handleMediaCardClickError
        )
    }


    private fun createSearchPager(query: String): Flow<PagingData<MediaUiState>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                FindByActorPagingSource(
                    query,
                    getMediaByActorNameUseCase,
                    sortingMediaByCategoriesInteractionUseCase
                )
            }
        ).flow.cachedIn(viewModelScope)
    }

    private fun handleSearchSuccess(searchResultFlow: Flow<PagingData<MediaUiState>>) {
        updateState(
            screenState.value.copy(
                uiState = screenState.value.uiState.copy(
                    searchResult = searchResultFlow
                )
            )
        )
    }

    private fun handleSearchError(errorMessage: String) {
        updateState(
            screenState.value.copy(
                errorMessage = errorMessage
            )
        )
    }

    private fun updateSearchQuery(query: String) {
        updateState(
            screenState.value.copy(
                uiState = screenState.value.uiState.copy(
                    searchQuery = query
                )
            )
        )
    }

    private fun startDebouncedSearch(query: String) {
        debounceJob = viewModelScope.launch {
            delay(1000)
            searchQuery(query)
        }
    }

    private fun clearSearchResults() {
        updateState(
            screenState.value.copy(
                uiState = screenState.value.uiState.copy(
                    searchResult = flowOf(PagingData.empty())
                )
            )
        )
    }

    private fun handleMediaCardClickError(errorMessage: String) {
        updateState(
            screenState.value.copy(
                errorMessage = errorMessage
            )
        )
    }

}