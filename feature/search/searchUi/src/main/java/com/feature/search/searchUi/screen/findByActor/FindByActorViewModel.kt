package com.feature.search.searchUi.screen.findByActor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.domain.search.model.Media
import com.domain.search.useCases.GetMediaByActorNameUseCase
import com.feature.search.searchUi.comon.BaseViewModel
import com.feature.search.searchUi.navigation.Destinations
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class FindByActorScreenState(
    val uiState: FindByActorUiState,
    val isLoading: Boolean,
    val errorMessage: String?
)
data class FindByActorUiState(
    val searchQuery: String,
    val searchResult: List<Media>,
)

class FindByActorViewModel(
    savedStateHandle: SavedStateHandle,
    private val getMediaByActorNameUseCase: GetMediaByActorNameUseCase,
) : FindByActorScreenInteractionListener, BaseViewModel<FindByActorScreenState>(
    FindByActorScreenState(
        uiState = FindByActorUiState(
            searchQuery = "",
            searchResult = listOf()
        ),
        isLoading = false,
        errorMessage = null
    )
) {

    init {
        val initialQuery = savedStateHandle.toRoute<Destinations.FindByActorScreen>().name
        if (initialQuery != null) {
            onSearchQueryChange(initialQuery)
        }
    }

    override fun onNavigateBack() {
        navigateUp()
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
                getMediaByActorNameUseCase(query)
            },
            onSuccess = { searchResult ->
                emitState(
                    screenState.value.copy(
                        isLoading = false,
                        uiState = screenState.value.uiState.copy(
                            searchResult = searchResult,
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

    override fun onRetrySearchQuery() {
        searchQuery(screenState.value.uiState.searchQuery)
    }

    override fun onMediaCardClick(id: Int) {
        //TODO: Navigate to media details screen
    }


}