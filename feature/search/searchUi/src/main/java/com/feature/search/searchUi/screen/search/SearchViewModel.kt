package com.feature.search.searchUi.screen.search

import CategoryUiState
import MediaTypeUi
import MediaUiState
import SearchScreenState
import SearchTypeUi
import androidx.lifecycle.viewModelScope
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.recyclerview.widget.DiffUtil
import com.domain.search.model.Category
import com.domain.search.model.Media
import com.domain.search.model.SearchHistoryModel
import com.domain.search.useCase.ClearAllRecentSearchesUseCase
import com.domain.search.useCase.ClearRecentSearchUseCase
import com.domain.search.useCase.FilterMediaByRatingUseCase
import com.domain.search.useCase.FilterMediaUseCase
import com.domain.search.useCase.GetAllCategoriesUseCase
import com.domain.search.useCase.GetAllRecentSearchesUseCase
import com.domain.search.useCase.IncrementCategoryInteractionUseCase
import com.domain.search.useCase.SearchByQueryUseCase
import com.domain.search.useCase.SortingMediaByCategoriesInteractionUseCase
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.search.searchUi.comon.BaseViewModel
import com.feature.search.searchUi.mapper.toCategoryUiList
import com.feature.search.searchUi.mapper.toDomainList
import com.feature.search.searchUi.mapper.toDomainModel
import com.feature.search.searchUi.mapper.toMediaUiList
import com.feature.search.searchUi.mapper.toSearchHistoryUiList
import com.feature.search.searchUi.navigation.SearchDestination
import com.feature.search.searchUi.navigation.SearchDestinations
import com.feature.search.searchUi.pagging.SearchByQueryPagingSource
import com.feature.search.searchUi.screen.search.SearchStateDefaults.initialSearchScreenState
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

class SearchViewModel(
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
) : SearchScreenInteractionListener,
    BaseViewModel<SearchScreenState>(
        initialSearchScreenState()
    ) {

    init {
        loadRecentSearches()
    }

    private fun loadRecentSearches() {
        tryToExecute(
            execute = getAllRecentSearchesUseCase::invoke,
            onSuccess = ::handleRecentSearchesSuccess,
            onError = ::updateErrorMessage
        )
    }


    fun loadCategories() {
        tryToExecute(
            execute = getAllCategoriesUseCase::invoke,
            onSuccess = ::handleCategoriesSuccess,
            onError = ::updateErrorMessage
        )
    }


    private var debounceJob: Job? = null

    override fun onSearchQueryChange(query: String) {
        updateSearchQuery(query)
        restartDebounceIfNeeded(query)
    }


    private fun searchQuery(query: String): Job {
        return tryToExecute(
            execute = {
                clearErrorMessage()
                createSearchPager(query)
            },
            onSuccess = { searchResult ->
                handleSearchSuccess(searchResult)
            },
            onError = { errorMessage ->
                updateErrorMessage(errorMessage)
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
                updateFilterState(selectedRating, isAllCategories, selectedCategories)

                val movies = filterByCategoryAndRating(
                    selectedRating,
                    isAllCategories,
                    selectedCategories,
                    screenState.value.searchUiState.moviesResult
                )

                val tvShows = filterByCategoryAndRating(
                    selectedRating,
                    isAllCategories,
                    selectedCategories,
                    screenState.value.searchUiState.tvShowsResult
                )

                Pair(movies, tvShows)
            },
            onSuccess = { (movies, tvShows) -> emitFilteredResults(movies, tvShows) },
            onError = ::handleFilterError
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

            SearchTypeUi.Country -> navigateWithErrorHandling {
                SearchDestinations.WorldTourScreen(name = searchTitle)
            }

            SearchTypeUi.Actor -> navigateWithErrorHandling {
                SearchDestinations.FindByActorScreen(name = searchTitle)
            }
        }
    }

    override fun onClearAllRecentSearches() {
        tryToExecute(
            execute = clearAllRecentSearchesUseCase::invoke,
            onError = ::updateErrorMessage
        )
    }


    override fun onClearRecentSearch(id: String, searchTypeUi: SearchTypeUi) {
        tryToExecute(
            execute = {
                clearRecentSearchUseCase(id, searchTypeUi.toDomainModel())
            },
            onError = ::updateErrorMessage
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
                handleMediaCardClick(mediaUiState)
            },
            onError = ::updateErrorMessage
        )
    }

    private suspend fun handleMediaCardClick(mediaUiState: MediaUiState) {
        incrementCategoryInteractionUseCase(mediaUiState.categories)

        when (mediaUiState.type) {
            MediaTypeUi.MOVIE -> mediaDetailsFeatureAPI.startMovieDetails(
                movieId = mediaUiState.id
            )

            MediaTypeUi.TVSHOW -> mediaDetailsFeatureAPI.startTvShowDetails(
                tvShowId = mediaUiState.id
            )
        }
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


    private suspend fun handleRecentSearchesSuccess(recentSearches: Flow<List<SearchHistoryModel>>) {
        recentSearches.collect { recentSearchesList ->
            updateSearchUiStateWithRecentSearches(recentSearchesList)
            loadCategories()
        }
    }

    private fun updateSearchUiStateWithRecentSearches(recentSearchesList: List<SearchHistoryModel>) {
        updateState(
            screenState.value.copy(
                isLoading = false,
                searchUiState = screenState.value.searchUiState.copy(
                    recentSearches = recentSearchesList.toSearchHistoryUiList()
                )
            )
        )
    }

    private fun updateErrorMessage(errorMessage: String) {
        updateState(
            screenState.value.copy(
                errorMessage = errorMessage
            )
        )
    }

    private fun handleCategoriesSuccess(categories: List<Category>) {
        val categoryMap = categories
            .toCategoryUiList()
            .associateWith { false }
            .toMutableMap()

        updateState(
            screenState.value.copy(
                searchUiState = screenState.value.searchUiState.copy(
                    categories = categoryMap
                )
            )
        )
    }


    private fun updateSearchQuery(query: String) {
        updateState(
            screenState.value.copy(
                searchUiState = screenState.value.searchUiState.copy(
                    searchQuery = query
                )
            )
        )
    }

    private fun restartDebounceIfNeeded(query: String) {
        debounceJob?.cancel()
        if (query.isNotBlank()) {
            debounceJob = viewModelScope.launch {
                delay(1000)
                searchQuery(query)
            }
        }
    }

    private fun clearErrorMessage() {
        updateState(
            screenState.value.copy(errorMessage = null)
        )
    }

    private fun createSearchPager(query: String): Flow<PagingData<MediaUiState>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                SearchByQueryPagingSource(
                    query = query,
                    searchByQueryUseCase = searchByQueryUseCase,
                    sortingMediaByCategoriesInteractionUseCase = sortingMediaByCategoriesInteractionUseCase
                )
            }
        ).flow.cachedIn(viewModelScope)
    }

    private suspend fun handleSearchSuccess(searchResult: Flow<PagingData<MediaUiState>>) {
        val moviesResult =
            searchResult.map { it.filter { media -> media.type == MediaTypeUi.MOVIE } }
        val tvShowsResult =
            searchResult.map { it.filter { media -> media.type == MediaTypeUi.TVSHOW } }

        val filteredMediaByRating = flowOf(
            PagingData.from(
                filterMediaByRatingUseCase(
                    screenState.value.searchUiState.selectedRating,
                    searchResult.collectAllItems().map { it.toDomainModel() }
                )
            )
        )

        val filteredMediaByCategories = if (!screenState.value.searchUiState.isAllCategories) {
            flowOf(
                PagingData.from(
                    filterMedByListOfCategoriesUseCase(
                        screenState.value.searchUiState.categories.filter { it.value }.keys.map { it.id },
                        filteredMediaByRating.collectItems()
                    ).toMediaUiList()
                )
            )
        } else {
            searchResult
        }

        val filteredMoviesResult =
            filteredMediaByCategories.map { it.filter { media -> media.type == MediaTypeUi.MOVIE } }
        val filteredTvShowsResult =
            filteredMediaByCategories.map { it.filter { media -> media.type == MediaTypeUi.TVSHOW } }

        updateState(
            screenState.value.copy(
                isLoading = false,
                searchUiState = screenState.value.searchUiState.copy(
                    moviesResult = moviesResult,
                    tvShowsResult = tvShowsResult,
                    filteredMoviesResult = filteredMoviesResult,
                    filteredTvShowsResult = filteredTvShowsResult
                )
            )
        )
    }

    private fun updateFilterState(
        rating: Float,
        isAllCategories: Boolean,
        selectedCategories: List<CategoryUiState>,
    ) {
        val updatedCategories = screenState.value.searchUiState.categories.mapValues {
            it.key in selectedCategories
        }.toMutableMap()

        updateState(
            screenState.value.copy(
                isLoading = true,
                searchUiState = screenState.value.searchUiState.copy(
                    showFilterDialog = false,
                    selectedRating = rating,
                    categories = updatedCategories,
                    isAllCategories = isAllCategories,
                    isApplyFilter = false
                )
            )
        )
    }

    private suspend fun filterByCategoryAndRating(
        rating: Float,
        isAllCategories: Boolean,
        selectedCategories: List<CategoryUiState>,
        resultFlow: Flow<PagingData<MediaUiState>>,
    ): List<Media> {
        val mediaList = filterMediaByRatingUseCase(
            rating,
            resultFlow.collectAllItems().toDomainList()
        )

        return if (isAllCategories) mediaList
        else filterMedByListOfCategoriesUseCase(selectedCategories.map { it.id }, mediaList)
    }

    private fun emitFilteredResults(movies: List<Media>, tvShows: List<Media>) {
        updateState(
            screenState.value.copy(
                isLoading = false,
                searchUiState = screenState.value.searchUiState.copy(
                    filteredMoviesResult = movies.toPagingDataFlow(),
                    filteredTvShowsResult = tvShows.toPagingDataFlow(),
                    isApplyFilter = true
                )
            )
        )
    }

    private fun handleFilterError(message: String) {
        updateState(
            screenState.value.copy(
                isLoading = false,
                errorMessage = message,
                searchUiState = screenState.value.searchUiState.copy(isApplyFilter = false)
            )
        )
    }

    private fun List<Media>.toPagingDataFlow(): Flow<PagingData<MediaUiState>> =
        flow { emit(PagingData.from(this@toPagingDataFlow.toMediaUiList())) }


    private fun navigateWithErrorHandling(destinationProvider: () -> SearchDestination) {
        tryToExecute(
            execute = {
                navigate(destinationProvider())
            },
            onError = ::updateErrorMessage
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


}