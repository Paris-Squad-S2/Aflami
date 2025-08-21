package com.feature.search.searchUi.screen.findByActor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.search.searchUi.comon.BaseViewModel
import com.feature.search.searchUi.mapper.toMediaUiList
import com.feature.search.searchUi.navigation.SearchDestinations
import com.feature.search.searchUi.pagging.PagingSource
import com.feature.search.searchUi.screen.search.ContentRestriction
import com.feature.search.searchUi.screen.search.MediaUiState
import com.paris.domain.media.useCase.GetMediaByActorNameUseCase
import com.paris.domain.media.useCase.IncrementCategoryInteractionUseCase
import com.paris.domain.media.useCase.SortingMediaByCategoriesInteractionUseCase
import com.paris.domain.user.usecase.SettingsUseCase
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
    private val settingsUseCase: SettingsUseCase,
) : FindByActorScreenInteractionListener, BaseViewModel<FindByActorScreenState>(
    FindByActorScreenState(),
) {


    init {
        getRestriction()
        val initialQuery = savedStateHandle.toRoute<SearchDestinations.FindByActorScreen>().name
        if (initialQuery != null) {
            onSearchQueryChange(initialQuery)
        }
    }

    private fun getRestriction() {
        viewModelScope.launch {
            val restriction = settingsUseCase.getRestriction()
            updateState(
                screenState.value.copy(
                    screenState.value.uiState.copy(
                        contentRestriction = ContentRestriction.valueOf(restriction)
                    )

                )
            )
            when (screenState.value.uiState.contentRestriction) {
                ContentRestriction.Strict -> updateState(
                    screenState.value.copy(
                        screenState.value.uiState.copy(
                            nsfwThreshold = 0.8f,
                            genderThreshold = 0.6f
                        )
                    )
                )

                ContentRestriction.Moderate -> updateState(
                    screenState.value.copy(
                        screenState.value.uiState.copy(
                            nsfwThreshold = 0.4f,
                            genderThreshold = 0.6f
                        )
                    )
                )

                ContentRestriction.Off -> updateState(
                    screenState.value.copy(
                        screenState.value.uiState.copy(
                            nsfwThreshold = 0f,
                            genderThreshold = 0f
                        )
                    )
                )
            }
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