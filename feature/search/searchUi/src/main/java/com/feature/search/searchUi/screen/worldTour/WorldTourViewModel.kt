package com.feature.search.searchUi.screen.worldTour

import MediaUiState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.domain.search.useCase.AutoCompleteCountryUseCase
import com.domain.search.useCase.GetCountryCodeByNameUseCase
import com.domain.search.useCase.GetMoviesOnlyByCountryNameUseCase
import com.domain.search.useCase.IncrementCategoryInteractionUseCase
import com.domain.search.useCase.SortingMediaByCategoriesInteractionUseCase
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.search.searchUi.comon.BaseViewModel
import com.feature.search.searchUi.navigation.SearchDestinations
import com.feature.search.searchUi.navigation.SearchNavigator
import com.feature.search.searchUi.pagging.WorldTourPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorldTourViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val autoCompleteCountryUseCase: AutoCompleteCountryUseCase,
    private val getCountryCodeByNameUseCase: GetCountryCodeByNameUseCase,
    private val getMoviesByCountryUseCase: GetMoviesOnlyByCountryNameUseCase,
    private val incrementCategoryInteractionUseCase: IncrementCategoryInteractionUseCase,
    private val sortingMediaByCategoriesInteractionUseCase: SortingMediaByCategoriesInteractionUseCase,
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI,
    navigator: SearchNavigator
) : WorldTourScreenInteractionListener,
    BaseViewModel<WorldTourScreenState>(
        WorldTourScreenState(
            uiState = WorldTourUiState(
                searchQuery = "",
                searchResult = flowOf(PagingData.empty()),
                hints = listOf()
            ),
            errorMessage = null
        ), navigator
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
        updateState(
            screenState.value.copy(
                uiState = screenState.value.uiState.copy(
                    searchQuery = query,
                ),
                errorMessage = null
            )
        )
        debounceJob?.cancel()
        if (query.isNotBlank()) {
            debounceJob = viewModelScope.launch {
                val hints = autoCompleteCountryUseCase(query)
                updateState(
                    screenState.value.copy(
                        uiState = screenState.value.uiState.copy(
                            hints = hints
                        )
                    )
                )
                delay(1000)
                val countryCode = getCountryCodeByNameUseCase(query)
                if (countryCode != null) {
                    searchQuery(countryCode)
                } else if (screenState.value.uiState.hints.isNotEmpty()) {
                    searchQuery(screenState.value.uiState.hints.first().countryCode)
                } else {
                    updateState(
                        screenState.value.copy(
                            uiState = screenState.value.uiState.copy(
                                searchResult = flowOf(PagingData.empty())
                            ),
                            errorMessage = "no data found"
                        )
                    )
                }
            }
        } else {
            updateState(
                screenState.value.copy(
                    uiState = screenState.value.uiState.copy(
                        searchResult = flowOf(PagingData.empty()),
                        hints = emptyList()
                    ),
                    errorMessage = null
                )
            )
        }
    }

    private fun searchQuery(query: String): Job {
        return tryToExecute(
            execute = {
                updateState(
                    screenState.value.copy(
                        errorMessage = null
                    )
                )
                Pager(
                    config = PagingConfig(pageSize = 10),
                    pagingSourceFactory = {
                        WorldTourPagingSource(
                            countryName = query,
                            getMoviesByCountryUseCase = getMoviesByCountryUseCase,
                            sortingMediaByCategoriesInteractionUseCase
                        )
                    }
                ).flow.cachedIn(viewModelScope)
            },
            onSuccess = { searchResult ->
                updateState(
                    screenState.value.copy(
                        uiState = screenState.value.uiState.copy(
                            searchResult = searchResult
                        )
                    )
                )
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        errorMessage = errorMessage
                    )
                )
            }
        )
    }

    override fun onRetrySearchQuery() {
        searchQuery(screenState.value.uiState.searchQuery)
    }

    override fun onMediaCardClick(media: MediaUiState) {
        tryToExecute(
            execute = {
                incrementCategoryInteractionUseCase.invoke(media.categories)
                mediaDetailsFeatureAPI.startMovieDetails(
                    movieId = media.id
                )
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        errorMessage = errorMessage
                    )
                )
            }
        )
    }
}