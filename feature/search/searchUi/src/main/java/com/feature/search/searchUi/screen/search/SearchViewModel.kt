package com.feature.search.searchUi.screen.search

import CategoryUiState
import MediaTypeUi
import MediaUiState
import SearchScreenState
import SearchTypeUi
import SearchUiState
import androidx.lifecycle.viewModelScope
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.domain.media.model.Media
import com.domain.media.useCase.ClearAllRecentSearchesUseCase
import com.domain.media.useCase.ClearRecentSearchUseCase
import com.domain.media.useCase.FilterMediaByRatingUseCase
import com.domain.media.useCase.FilterMediaUseCase
import com.domain.media.useCase.GetAllCategoriesUseCase
import com.domain.media.useCase.GetAllRecentSearchesUseCase
import com.domain.media.useCase.IncrementCategoryInteractionUseCase
import com.domain.media.useCase.SearchByQueryUseCase
import com.domain.media.useCase.SortingMediaByCategoriesInteractionUseCase
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.search.searchUi.comon.BaseViewModel
import com.feature.search.searchUi.mapper.toCategoryUiList
import com.feature.search.searchUi.mapper.toDomainList
import com.feature.search.searchUi.mapper.toDomainModel
import com.feature.search.searchUi.mapper.toMediaUiList
import com.feature.search.searchUi.mapper.toSearchHistoryUiList
import com.feature.search.searchUi.navigation.SearchDestinations
import com.feature.search.searchUi.navigation.SearchNavigator
import com.feature.search.searchUi.pagging.PagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getAllRecentSearchesUseCase: GetAllRecentSearchesUseCase,
    private val clearAllRecentSearchesUseCase: ClearAllRecentSearchesUseCase,
    private val clearRecentSearchUseCase: ClearRecentSearchUseCase,
    private val searchByQueryUseCase: SearchByQueryUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val filterMediaByRatingUseCase: FilterMediaByRatingUseCase,
    private val filterMedByListOfCategoriesUseCase: FilterMediaUseCase,
    private val incrementCategoryInteractionUseCase: IncrementCategoryInteractionUseCase,
    private val sortingMediaByCategoriesInteractionUseCase: SortingMediaByCategoriesInteractionUseCase,
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI,
    navigator: SearchNavigator
) : SearchScreenInteractionListener,
    BaseViewModel<SearchScreenState>(
        SearchScreenState(
            searchUiState = SearchUiState(
                searchQuery = "",
                showFilterDialog = false,
                recentSearches = listOf(),
                filteredMoviesResult = flowOf(PagingData.empty()),
                filteredTvShowsResult = flowOf(PagingData.empty()),
                selectedTabIndex = 0,
                categories = mapOf(),
                selectedRating = 0f,
                moviesResult = flowOf(PagingData.empty()),
                tvShowsResult = flowOf(PagingData.empty()),
                isAllCategories = true,
                isApplyFilter = false
            ),
            isLoading = false,
            errorMessage = null
        ),
        navigator
    ) {

    init {
        loadRecentSearches()
    }

    private fun loadRecentSearches() {
        tryToExecute(
            execute = getAllRecentSearchesUseCase::invoke,
            onSuccess = { recentSearches ->
                recentSearches.collect { recentSearchesList ->
                    updateState(
                        screenState.value.copy(
                            isLoading = false,
                            searchUiState = screenState.value.searchUiState.copy(
                                recentSearches = recentSearchesList.toSearchHistoryUiList()
                            )
                        )
                    )
                    loadCategories()
                }
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

    fun loadCategories() {
        tryToExecute(
            execute = getAllCategoriesUseCase::invoke,
            onSuccess = { categories ->
                updateState(
                    screenState.value.copy(
                        searchUiState = screenState.value.searchUiState.copy(
                            categories = categories.toCategoryUiList().associateWith { false }
                                .toMutableMap()
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

    override fun onNavigateToWorldTourScreen() {
        navigate(
            SearchDestinations.WorldTourScreen()
        )
    }

    override fun onNavigateToFindByActorScreen() {
        navigate(
            SearchDestinations.FindByActorScreen()
        )
    }

    private var debounceJob: Job? = null

    override fun onSearchQueryChange(query: String) {
        updateState(
            screenState.value.copy(
                searchUiState = screenState.value.searchUiState.copy(
                    searchQuery = query,
                ),
                isLoading = true
            )
        )
        debounceJob?.cancel()
        if (query.isNotBlank()) {
            debounceJob = viewModelScope.launch {
                delay(1000)
                searchQuery(query)
            }
        }else{
            updateState(
                screenState.value.copy(
                    isLoading = false
                )
            )
        }
    }

    private fun searchQuery(query: String): Job {
        return tryToExecute(
            execute = {
                updateState(
                    screenState.value.copy(
                        errorMessage = null,
                    )
                )
                Pager(
                    config = PagingConfig(pageSize = 10),
                    pagingSourceFactory = {
                        PagingSource(
                            searchUseCase = {page ->
                                sortingMediaByCategoriesInteractionUseCase(
                                    searchByQueryUseCase(
                                        query,
                                        page
                                    )
                                ).toMediaUiList()
                            }
                        )
                    }
                ).flow.cachedIn(viewModelScope)
            },
            onSuccess = { searchResult ->
                val moviesResult =
                    searchResult.map { pagingData  -> pagingData .filter { it.type == MediaTypeUi.MOVIE } }
                val tvShowsResult =
                    searchResult.map { pagingData -> pagingData .filter { it.type == MediaTypeUi.TVSHOW } }
                val filteredMediaByRating = flowOf(PagingData.from(filterMediaByRatingUseCase(
                    screenState.value.searchUiState.selectedRating,
                    searchResult.collectAllItems().map { it.toDomainModel() }
                )))
                val filteredMediaByCategories =
                    if (!screenState.value.searchUiState.isAllCategories) flowOf (PagingData.from(filterMedByListOfCategoriesUseCase(
                        screenState.value.searchUiState.categories.filter { it.value }.keys.toList().map { it.id },
                        filteredMediaByRating.collectItems()
                    ).toMediaUiList())) else searchResult

                val filteredMoviesResult =
                    filteredMediaByCategories.map { pagingData  -> pagingData
                        .filter { it.type == MediaTypeUi.MOVIE }}
                val filteredTvShowsResult =
                    filteredMediaByCategories.map { pagingData  -> pagingData
                        .filter { it.type == MediaTypeUi.TVSHOW }}
                updateState(
                    screenState.value.copy(
                        isLoading = false,
                        searchUiState = screenState.value.searchUiState.copy(
                            moviesResult = moviesResult,
                            tvShowsResult = tvShowsResult,
                            filteredMoviesResult = filteredMoviesResult,
                            filteredTvShowsResult = filteredTvShowsResult,
                        )
                    )
                )
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        errorMessage = errorMessage,
                        isLoading = false
                    )
                )
            }
        )
    }


    override fun onSelectTab(tabIndex: Int) {
        updateState(
            screenState.value.copy(
                searchUiState = screenState.value.searchUiState.copy(
                    selectedTabIndex = tabIndex
                )
            )
        )
    }

    override fun onFilterButtonClick() {
        updateState(
            screenState.value.copy(
                searchUiState = screenState.value.searchUiState.copy(
                    showFilterDialog = !screenState.value.searchUiState.showFilterDialog
                )
            )
        )
    }

    override fun onApplyFilterButtonClick(
        selectedRating: Float,
        isAllCategories: Boolean,
        selectedCategories: List<CategoryUiState>,
    ) {
        tryToExecute(
            execute = {
                updateState(
                    screenState.value.copy(
                        searchUiState = screenState.value.searchUiState.copy(
                            showFilterDialog = false,
                            selectedRating = selectedRating,
                            categories = screenState.value.searchUiState.categories.mapValues { it.key in selectedCategories }
                                .toMutableMap(),
                            isAllCategories = isAllCategories,
                            isApplyFilter = false
                        ),
                        isLoading = true
                    )
                )

                val filteredMovies = filterMediaByRatingUseCase(
                    selectedRating,
                    screenState.value.searchUiState.moviesResult.collectAllItems().toDomainList()
                )
                val filteredTvShows = filterMediaByRatingUseCase(
                    selectedRating,
                    screenState.value.searchUiState.tvShowsResult.collectAllItems().toDomainList()
                )
                val filteredByCategoriesMovies = if (isAllCategories) {
                    filteredMovies
                } else {
                    filterMedByListOfCategoriesUseCase(
                        selectedCategories.map { it.id },
                        filteredMovies
                    )
                }
                val filteredByCategoriesTvShows = if (isAllCategories) {
                    filteredTvShows
                } else {
                    filterMedByListOfCategoriesUseCase(
                        selectedCategories.map { it.id },
                        filteredTvShows
                    )
                }
                Pair(filteredByCategoriesMovies, filteredByCategoriesTvShows)
            },
            onSuccess = { (filteredByCategoriesMovies, filteredByCategoriesTvShows) ->
                updateState(
                    screenState.value.copy(
                        searchUiState = screenState.value.searchUiState.copy(
                            filteredMoviesResult = flow {
                                emit(
                                    PagingData.from(
                                        filteredByCategoriesMovies.toMediaUiList()
                                    )
                                )
                            },
                            filteredTvShowsResult = flow {
                                emit(
                                    PagingData.from(
                                        filteredByCategoriesTvShows.toMediaUiList()
                                    )
                                )
                            },
                            isApplyFilter = true
                        ),
                        isLoading = false
                    )
                )
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        errorMessage = errorMessage,
                        isLoading = false,
                        searchUiState = screenState.value.searchUiState.copy(
                            isApplyFilter = false
                        )
                    )
                )
            }
        )
    }

    override fun onClearFilterClick() {
        updateState(
            screenState.value.copy(
                searchUiState = screenState.value.searchUiState.copy(
                    showFilterDialog = false,
                    selectedRating = 0f,
                    isAllCategories = true,
                    categories = screenState.value.searchUiState.categories.mapValues { false }
                        .toMutableMap(),
                    filteredMoviesResult = screenState.value.searchUiState.moviesResult,
                    filteredTvShowsResult = screenState.value.searchUiState.tvShowsResult,
                    isApplyFilter = false
                )
            )
        )
    }

    override fun onRecentSearchClick(searchTitle: String, searchTypeUi: SearchTypeUi) {
        when (searchTypeUi) {
            SearchTypeUi.Query -> onSearchQueryChange(searchTitle)
            SearchTypeUi.Country -> {
                tryToExecute(
                    execute = {
                        navigate(
                            SearchDestinations.WorldTourScreen(
                                name = searchTitle
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

            SearchTypeUi.Actor -> {
                tryToExecute(
                    execute = {
                        navigate(
                            SearchDestinations.FindByActorScreen(
                                name = searchTitle
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
        }
    }

    override fun onClearAllRecentSearches() {
        tryToExecute(
            execute = clearAllRecentSearchesUseCase::invoke,
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        errorMessage = errorMessage
                    )
                )
            }
        )
    }

    override fun onClearRecentSearch(id: String, searchTypeUi: SearchTypeUi) {
        tryToExecute(
            execute = {
                clearRecentSearchUseCase(id, searchTypeUi.toDomainModel())
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

    override fun onRetryRecentSearches() {
        loadRecentSearches()
    }

    override fun onRetrySearchQuery() {
        searchQuery(screenState.value.searchUiState.searchQuery)
    }

    override fun onMediaCardClick(mediaUiState: MediaUiState) {
        tryToExecute(
            execute = {
                incrementCategoryInteractionUseCase(mediaUiState.categories)

                when (mediaUiState.type) {
                    MediaTypeUi.MOVIE -> mediaDetailsFeatureAPI.startMovieDetails(
                        movieId = mediaUiState.id
                    )

                    MediaTypeUi.TVSHOW -> mediaDetailsFeatureAPI.startTvShowDetails(
                        tvShowId = mediaUiState.id
                    )
                }

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

    private suspend fun Flow<PagingData<Media>>.collectItems(): List<Media> {
        val differ = AsyncPagingDataDiffer(
            diffCallback = object : DiffUtil.ItemCallback<Media>() {
                override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean =
                    oldItem == newItem
            },
            updateCallback = NoopListUpdateCallback(),
            mainDispatcher = Dispatchers.Main,
            workerDispatcher = Dispatchers.Default
        )

        val job = CoroutineScope(Dispatchers.Main).launch {
            collectLatest { pagingData ->
                differ.submitData(pagingData)
            }
        }

        delay(1000)
        job.cancel()

        return differ.snapshot().items
    }

    private suspend fun Flow<PagingData<MediaUiState>>.collectAllItems(): List<MediaUiState> {
        val differ = AsyncPagingDataDiffer(
            diffCallback = object : DiffUtil.ItemCallback<MediaUiState>() {
                override fun areItemsTheSame(
                    oldItem: MediaUiState,
                    newItem: MediaUiState,
                ): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: MediaUiState,
                    newItem: MediaUiState,
                ): Boolean =
                    oldItem == newItem
            },
            updateCallback = NoopListUpdateCallback(),
            mainDispatcher = Dispatchers.Main,
            workerDispatcher = Dispatchers.Default
        )

        val job = CoroutineScope(Dispatchers.Main).launch {
            collectLatest { pagingData ->
                differ.submitData(pagingData)
            }
        }

        delay(1000)
        job.cancel()

        return differ.snapshot().items
    }

    class NoopListUpdateCallback : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }


}