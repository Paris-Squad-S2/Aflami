package com.feature.search.searchUi.screen.findByActor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.domain.search.model.Media
import com.domain.search.useCases.GetMediaByActorNameUseCase
import com.feature.search.searchUi.comon.BaseViewModel
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
) : FindByActorScreenInteractionListener,
    BaseViewModel<FindByActorScreenState, FindByActorUiEffect>(
        FindByActorScreenState(
            uiState = FindByActorUiState(
                searchQuery = "",
                searchResult = listOf()
            ),
            isLoading = false,
            errorMessage = null
        )
    ) {

    private val args = FindByActorArgs(savedStateHandle)

    init {
        onSearchQueryChange(args.query)
    }

    override fun onNavigateBack() {
        sendUiEffect(FindByActorUiEffect.NavigateToBack)
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
            debounceJob = viewModelScope.launch {
                delay(1000)
                emitState(
                    screenState.value.copy(
                        isLoading = true,
                    )
                )
                searchQuery(query)
            }
        } else {
            emitState(
                screenState.value.copy(
                    isLoading = false,
                    uiState = screenState.value.uiState.copy(
                        searchResult = listOf(),
                    )
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