package com.feature.search.searchUi.screen.worldTour

import MediaUiState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.domain.search.model.Country
import com.domain.search.useCase.AutoCompleteCountryUseCase
import com.domain.search.useCase.GetCountryCodeByNameUseCase
import com.domain.search.useCase.GetMoviesOnlyByCountryNameUseCase
import com.domain.search.useCase.IncrementCategoryInteractionUseCase
import com.domain.search.useCase.SortingMediaByCategoriesInteractionUseCase
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.search.searchUi.comon.BaseViewModel
import com.feature.search.searchUi.navigation.SearchDestinations
import com.feature.search.searchUi.pagging.WorldTourPagingSource
import com.feature.search.searchUi.screen.worldTour.WorldTourDefaults.initialWorldTourScreenStata
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class WorldTourViewModel(
    savedStateHandle: SavedStateHandle,
    private val autoCompleteCountryUseCase: AutoCompleteCountryUseCase,
    private val getCountryCodeByNameUseCase: GetCountryCodeByNameUseCase,
    private val getMoviesByCountryUseCase: GetMoviesOnlyByCountryNameUseCase,
    private val incrementCategoryInteractionUseCase: IncrementCategoryInteractionUseCase,
    private val sortingMediaByCategoriesInteractionUseCase: SortingMediaByCategoriesInteractionUseCase,
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI,
) : WorldTourScreenInteractionListener,
    BaseViewModel<WorldTourScreenState>(
        initialWorldTourScreenStata()
    ) {

    init {
        val initialQuery = savedStateHandle.toRoute<SearchDestinations.WorldTourScreen>().name
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

        if (query.isBlank()) {
            clearSearchResults()
            return
        }

        debounceJob = viewModelScope.launch {
            handleAutoCompleteAndSearch(query)
        }
    }

    private fun searchQuery(query: String): Job {
        return tryToExecute(
            execute = {
                clearErrorMessage()
                createWorldTourPagerFlow(query)
            },
            onSuccess = { result ->
                updateSearchResult(result)
            },
            onError = { error ->
                updateErrorMessage(error)
            }
        )
    }


    override fun onRetrySearchQuery() {
        searchQuery(screenState.value.uiState.searchQuery)
    }


    override fun onMediaCardClick(media: MediaUiState) {
        tryToExecute(
            execute = { handleMediaClick(media) },
            onError = { updateErrorMessage(it) }
        )
    }


    private fun clearSearchResults() {
        updateState(
            screenState.value.copy(
                uiState = screenState.value.uiState.copy(
                    searchResult = flowOf(PagingData.empty()),
                    hints = emptyList()
                )
            )
        )
    }

    private suspend fun handleAutoCompleteAndSearch(query: String) {
        val hints = autoCompleteCountryUseCase(query)
        updateHints(hints)

        delay(1000)

        val countryCode = getCountryCodeByNameUseCase(query)
            ?: hints.firstOrNull()?.countryCode

        if (countryCode != null) {
            searchQuery(countryCode)
        } else {
            clearSearchResults()
        }
    }

    private fun updateHints(hints: List<Country>) {
        updateState(
            screenState.value.copy(
                uiState = screenState.value.uiState.copy(hints = hints)
            )
        )
    }

    private fun createWorldTourPagerFlow(query: String): Flow<PagingData<MediaUiState>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {
            WorldTourPagingSource(
                countryName = query,
                getMoviesByCountryUseCase = getMoviesByCountryUseCase,
                sortingMediaByCategoriesInteractionUseCase
            )
        }
    ).flow.cachedIn(viewModelScope)

    private fun clearErrorMessage() {
        updateState(screenState.value.copy(errorMessage = null))
    }

    private fun updateSearchResult(result: Flow<PagingData<MediaUiState>>) {
        updateState(
            screenState.value.copy(
                uiState = screenState.value.uiState.copy(searchResult = result)
            )
        )
    }

    private suspend fun handleMediaClick(media: MediaUiState) {
        incrementCategoryInteractionUseCase.invoke(media.categories)
        mediaDetailsFeatureAPI.startMovieDetails(media.id)
    }

    private fun updateErrorMessage(errorMessage: String) {
        updateState(screenState.value.copy(errorMessage = errorMessage))
    }

    private fun updateSearchQuery(query: String) {
        updateState(
            screenState.value.copy(
                uiState = screenState.value.uiState.copy(searchQuery = query)
            )
        )
    }

}