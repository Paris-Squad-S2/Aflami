package com.feature.search.searchUi.screen.findByActor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.domain.search.useCases.GetMediaByActorNameUseCase
import com.feature.search.searchUi.comon.BaseViewModel
import com.feature.search.searchUi.mapper.toMediaUiList
import com.feature.search.searchUi.navigation.Destinations
import com.feature.search.searchUi.screen.search.MediaUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class FindByActorScreenState(
    val uiState: FindByActorUiState,
    val errorMessage: String?
)
data class FindByActorUiState(
    val searchQuery: String,
    val searchResult: Flow<PagingData<MediaUiState>>,
)

class FindByActorViewModel(
    savedStateHandle: SavedStateHandle,
    private val getMediaByActorNameUseCase: GetMediaByActorNameUseCase,
) : FindByActorScreenInteractionListener, BaseViewModel<FindByActorScreenState>(
    FindByActorScreenState(
        uiState = FindByActorUiState(
            searchQuery = "",
            searchResult = flowOf(PagingData.empty()),
        ),
        errorMessage = null
    )
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
                searchQuery(query)
            }
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
                     FindByActorPagingSource(query,getMediaByActorNameUseCase)
                 }
                ).flow.cachedIn(viewModelScope)

            },
            onSuccess = { searchResult ->
                emitState(
                    screenState.value.copy(
                        uiState = screenState.value.uiState.copy(
                            searchResult = searchResult
                            ,
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