package com.feature.search.searchUi.screen.findByActor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.domain.search.useCases.GetMediaByActorNameUseCase
import com.domain.search.useCases.IncrementCategoryInteractionUseCase
import com.domain.search.useCases.SortingMediaByCategoriesInteractionUseCase
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsApi.toJson
import com.feature.search.searchApi.SearchDestinations
import com.feature.search.searchUi.comon.BaseViewModel
import com.feature.search.searchUi.mapper.toMediaUiList
import com.feature.search.searchUi.screen.search.MediaTypeUi
import com.feature.search.searchUi.screen.search.MediaUiState
import com.paris_2.aflami.appnavigation.AppDestinations
import com.paris_2.aflami.appnavigation.AppNavigator
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.getKoin

data class FindByActorScreenState(
    val uiState: FindByActorUiState,
    val isLoading: Boolean,
    val errorMessage: String?
)

data class FindByActorUiState(
    val searchQuery: String,
    val searchResult: List<MediaUiState>,
)

class FindByActorViewModel(
    savedStateHandle: SavedStateHandle,
    private val getMediaByActorNameUseCase: GetMediaByActorNameUseCase,
    private val incrementCategoryInteractionUseCase: IncrementCategoryInteractionUseCase,
    private val sortingMediaByCategoriesInteractionUseCase: SortingMediaByCategoriesInteractionUseCase,
    private val appNavigator: AppNavigator = getKoin().get()
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
                sortingMediaByCategoriesInteractionUseCase(getMediaByActorNameUseCase(query))
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

    override fun onMediaCardClick(media: MediaUiState) {
        tryToExecute(
            execute = {
                incrementCategoryInteractionUseCase.invoke(media.categories)
                appNavigator.navigate(
                    AppDestinations.MediaDetailsFeature(
                        when (media.type) {
                            MediaTypeUi.MOVIE -> MediaDetailsDestinations.MovieDetailsScreen(
                                movieId = media.id
                            )

                            MediaTypeUi.TVSHOW -> MediaDetailsDestinations.TvShowDetailsScreen(
                                tvShowId = media.id
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