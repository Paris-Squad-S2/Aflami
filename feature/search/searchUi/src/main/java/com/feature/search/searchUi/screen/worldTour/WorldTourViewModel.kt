package com.feature.search.searchUi.screen.worldTour

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.domain.search.model.Media
import com.domain.search.useCases.AddRecentSearchUseCase
import com.domain.search.useCases.AutoCompleteCountryUseCase
import com.domain.search.useCases.GetCountryCodeByNameUseCase
import com.domain.search.useCases.GetMoviesOnlyByCountryNameUseCase
import com.feature.search.searchUi.comon.BaseViewModel
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
    val searchResult: List<Media>,
    val hints: List<String>
)

class WorldTourViewModel(
    private val autoCompleteCountryUseCase: AutoCompleteCountryUseCase,
    private val getCountryCodeByNameUseCase: GetCountryCodeByNameUseCase,
    private val getMoviesByCountryUseCase: GetMoviesOnlyByCountryNameUseCase,
    private val addRecentSearchesUseCase: AddRecentSearchUseCase,
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
                val hints = autoCompleteCountryUseCase(query)
                emitState(
                    screenState.value.copy(
                        uiState = screenState.value.uiState.copy(
                            hints = hints.map { it.countryName }
                        )
                    )
                )
                delay(500)
                val countryCode = getCountryCodeByNameUseCase(query)
                if (countryCode != null) {
                    searchQuery(countryCode)
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
                            searchResult = searchResult,
                        )
                    )
                )
                addRecentSearchesUseCase(screenState.value.uiState.searchQuery)
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