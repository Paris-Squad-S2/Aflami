package com.feature.search.searchUi.screen.worldTour

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.domain.search.model.Country
import com.domain.search.useCases.AutoCompleteCountryUseCase
import com.domain.search.useCases.GetCountryCodeByNameUseCase
import com.domain.search.useCases.GetMoviesOnlyByCountryNameUseCase
import com.feature.search.searchUi.comon.BaseViewModel
import com.feature.search.searchUi.mapper.toMediaUiList
import com.feature.search.searchUi.navigation.Destinations
import com.feature.search.searchUi.screen.search.MediaUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class WorldTourScreenState(
    val uiState: WorldTourUiState,
    val isLoading: Boolean,
    val errorMessage: String?
)

data class WorldTourUiState(
    val searchQuery: String,
    val searchResult: List<MediaUiState>,
    val hints: List<Country>
)

class WorldTourViewModel(
    savedStateHandle: SavedStateHandle,
    private val autoCompleteCountryUseCase: AutoCompleteCountryUseCase,
    private val getCountryCodeByNameUseCase: GetCountryCodeByNameUseCase,
    private val getMoviesByCountryUseCase: GetMoviesOnlyByCountryNameUseCase,
) : WorldTourScreenInteractionListener,
    BaseViewModel<WorldTourScreenState>(
        WorldTourScreenState(
            uiState = WorldTourUiState(
                searchQuery = "",
                searchResult = listOf(),
                hints = listOf()
            ),
            isLoading = false,
            errorMessage = null
        )
    ) {

    init {
        val initialQuery = savedStateHandle.toRoute<Destinations.WorldTourScreen>().name
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
        if (query.isNotBlank()) {
            debounceJob = viewModelScope.launch {
                val hints = autoCompleteCountryUseCase(query)
                emitState(
                    screenState.value.copy(
                        isLoading = true,
                        uiState = screenState.value.uiState.copy(
                            hints = hints
                        )
                    )
                )
                delay(1000)
                val countryCode = getCountryCodeByNameUseCase(query)
                if (countryCode != null) {
                    searchQuery(countryCode)
                }
                else if (screenState.value.uiState.hints.isNotEmpty()){
                    searchQuery(screenState.value.uiState.hints.first().countryName)
                }
                else {
                    emitState(
                        screenState.value.copy(
                            isLoading = false,
                        )
                    )
                }
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
                getMoviesByCountryUseCase(query)
            },
            onSuccess = { searchResult ->
                emitState(
                    screenState.value.copy(
                        isLoading = false,
                        uiState = screenState.value.uiState.copy(
                            searchResult = searchResult.toMediaUiList(),
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