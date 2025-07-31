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
import com.feature.search.searchUi.mapper.toMediaUiList
import com.feature.search.searchUi.navigation.SearchDestinations
import com.feature.search.searchUi.navigation.SearchNavigator
import com.feature.search.searchUi.pagging.PagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindByActorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMediaByActorNameUseCase: GetMediaByActorNameUseCase,
    private val incrementCategoryInteractionUseCase: IncrementCategoryInteractionUseCase,
    private val sortingMediaByCategoriesInteractionUseCase: SortingMediaByCategoriesInteractionUseCase,
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI,
    navigator: SearchNavigator,
) : FindByActorScreenInteractionListener, BaseViewModel<FindByActorScreenState>(
    FindByActorScreenState(
        uiState = FindByActorUiState(
            searchQuery = "",
            searchResult = flowOf(PagingData.empty()),
        ),
        errorMessage = null
    ),
    navigator
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
        updateState(
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
        } else {
            updateState(
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
                updateState(
                    screenState.value.copy(
                        errorMessage = null
                    )
                )
                Pager(
                    config = PagingConfig(pageSize = 10),
                    pagingSourceFactory = {
                        PagingSource(
                            searchUseCase = { page ->
                                sortingMediaByCategoriesInteractionUseCase(
                                    getMediaByActorNameUseCase(query, page)
                                ).toMediaUiList()
                            }

                        )
                    }
                ).flow.cachedIn(viewModelScope)

            },
            onSuccess = { searchResult ->
                updateState(
                    screenState.value.copy(
                        uiState = screenState.value.uiState.copy(
                            searchResult = searchResult,
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