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
    val errorMessage: String?
)

data class WorldTourUiState(
    val searchQuery: String,
    val searchResult: Flow<PagingData<MediaUiState>>,
    val hints: List<Country>
)

class WorldTourViewModel(
    savedStateHandle: SavedStateHandle,
    private val autoCompleteCountryUseCase: AutoCompleteCountryUseCase,
    private val getCountryCodeByNameUseCase: GetCountryCodeByNameUseCase,
    private val getMoviesByCountryUseCase: GetMoviesOnlyByCountryNameUseCase,
    private val appNavigator: AppNavigator = getKoin().get()
) : WorldTourScreenInteractionListener,
    BaseViewModel<WorldTourScreenState>(
        WorldTourScreenState(
            uiState = WorldTourUiState(
                searchQuery = "",
                searchResult = flowOf(PagingData.empty()),
                hints = listOf()
            ),
            errorMessage = null
        )
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
                            uiState = screenState.value.uiState.copy(
                                searchResult = flowOf(PagingData.empty()),
                            )
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
                        errorMessage = null
                    )
                )
                Pager(
                    config = PagingConfig(pageSize = 10),
                    pagingSourceFactory = {
                        WorldTourPagingSource(
                            countryName = query,
                            getMoviesByCountryUseCase = getMoviesByCountryUseCase
                        )
                    }
                ).flow.cachedIn(viewModelScope)
            },
            onSuccess = { searchResult ->
                emitState(
                    screenState.value.copy(
                        uiState = screenState.value.uiState.copy(
                            searchResult = searchResult,
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

    override fun onRetrySearchQuery() {
        searchQuery(screenState.value.uiState.searchQuery)
    }

    override fun onMediaCardClick(id: Int, mediaType: MediaTypeUi) {
        tryToExecute(
            execute = {
                appNavigator.navigate(
                    AppDestinations.MediaDetailsFeature(
                        when (mediaType) {
                            MediaTypeUi.MOVIE -> MediaDetailsDestinations.MovieDetailsScreen(
                                movieId = id
                            )
                            MediaTypeUi.TVSHOW -> MediaDetailsDestinations.TvShowDetailsScreen(
                                tvShowId = id
                            )
                        }.toJson()
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