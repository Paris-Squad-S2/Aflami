package com.feature.search.searchUi.screen.search

import androidx.lifecycle.viewModelScope
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.domain.search.model.Media
import com.domain.search.useCases.ClearAllRecentSearchesUseCase
import com.domain.search.useCases.ClearRecentSearchUseCase
import com.domain.search.useCases.FilterByListOfCategoriesUseCase
import com.domain.search.useCases.FilterMediaByRatingUseCase
import com.domain.search.useCases.GetAllCategoriesUseCase
import com.domain.search.useCases.GetAllRecentSearchesUseCase
import com.domain.search.useCases.SearchByQueryUseCase
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsApi.toJson
import com.feature.search.searchApi.SearchDestinations
import com.feature.search.searchUi.R
import com.feature.search.searchUi.comon.BaseViewModel
import com.feature.search.searchUi.mapper.toCategoryUiList
import com.feature.search.searchUi.mapper.toDomainList
import com.feature.search.searchUi.mapper.toDomainModel
import com.feature.search.searchUi.mapper.toMediaUiList
import com.feature.search.searchUi.mapper.toSearchHistoryUiList
import com.feature.search.searchUi.pagging.SearchByQueryPagingSource
import com.paris_2.aflami.appnavigation.AppDestinations
import com.paris_2.aflami.appnavigation.AppNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import org.koin.java.KoinJavaComponent.getKoin

data class SearchScreenState(
    val searchUiState: SearchUiState,
    val isLoading: Boolean,
    val errorMessage: String?
)


data class SearchUiState(
    val searchQuery: String,
    val showFilterDialog: Boolean,
    val recentSearches: List<SearchHistoryUiState>,
    val selectedTabIndex: Int,
    val moviesResult: Flow<PagingData<MediaUiState>>,
    val tvShowsResult: Flow<PagingData<MediaUiState>>,
    val filteredMoviesResult: Flow<PagingData<MediaUiState>>,
    val filteredTvShowsResult: Flow<PagingData<MediaUiState>>,
    val categories: Map<CategoryUiState, Boolean>,
    val selectedRating: Float,
    val isAllCategories: Boolean
)


data class MediaUiState(
    val id: Int,
    val imageUri: String,
    val title: String,
    val type: MediaTypeUi,
    val categories: List<Int>,
    val yearOfRelease: LocalDate,
    val rating: Double,
)

enum class MediaTypeUi(val mediaName: String){
    TVSHOW("TV Show"),
    MOVIE("Movie")
}

data class CategoryUiState(
    val id: Int,
    val name: String,
)

data class SearchHistoryUiState(
    val searchTitle: String,
    val searchDate:String,
    val searchType: SearchTypeUi
)

enum class SearchTypeUi(val displayNameResId: Int) {
    Query(R.string.query),
    Country(R.string.country),
    Actor(R.string.actor);
}

class SearchViewModel(
    private val getAllRecentSearchesUseCase: GetAllRecentSearchesUseCase,
    private val clearAllRecentSearchesUseCase: ClearAllRecentSearchesUseCase,
    private val clearRecentSearchUseCase: ClearRecentSearchUseCase,
    private val searchByQueryUseCase: SearchByQueryUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val filterMediaByRatingUseCase: FilterMediaByRatingUseCase,
    private val filterMedByListOfCategoriesUseCase: FilterByListOfCategoriesUseCase,
    private val appNavigator: AppNavigator = getKoin().get()
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
                isAllCategories = true
            ),
            isLoading = false,
            errorMessage = null
        )
    ) {

    init {
        loadRecentSearches()
    }

    private fun loadRecentSearches() {
        tryToExecute(
            execute = getAllRecentSearchesUseCase::invoke,
            onSuccess = { recentSearches ->
                recentSearches.collect { recentSearchesList ->
                    emitState(
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
                emitState(
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
                emitState(
                    screenState.value.copy(
                        searchUiState = screenState.value.searchUiState.copy(
                            categories = categories.toCategoryUiList().associateWith { false }.toMutableMap()
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
        emitState(
            screenState.value.copy(
                searchUiState = screenState.value.searchUiState.copy(
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
                        SearchByQueryPagingSource(
                            query = query,
                            searchByQueryUseCase = searchByQueryUseCase
                        )
                    }
                ).flow.cachedIn(viewModelScope)
            },
            onSuccess = { searchResult ->
                val moviesResult = searchResult.map { pagingData  -> pagingData .filter { it.type == MediaTypeUi.MOVIE } }
                val tvShowsResult = searchResult.map { pagingData -> pagingData .filter { it.type == MediaTypeUi.TVSHOW } }
                val filteredMediaByRating = flowOf(PagingData.from(filterMediaByRatingUseCase(
                    screenState.value.searchUiState.selectedRating,
                    searchResult.collectAllItems().map { it.toDomainModel() }
                )))
                val filteredMediaByCategories =
                    if (!screenState.value.searchUiState.isAllCategories) flowOf (PagingData.from(filterMedByListOfCategoriesUseCase(
                        screenState.value.searchUiState.categories.filter { it.value }.keys.toList().map { it.id },
                        filteredMediaByRating.collectAllItems()
                    ).toMediaUiList())) else searchResult

                val filteredMoviesResult =
                    filteredMediaByCategories.map { pagingData  -> pagingData .filter{ it.type == MediaTypeUi.MOVIE }}
                val filteredTvShowsResult =
                    filteredMediaByCategories.map { pagingData  -> pagingData .filter{ it.type == MediaTypeUi.TVSHOW }}
                emitState(
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
                emitState(
                    screenState.value.copy(
                        errorMessage = errorMessage
                    )
                )
            }
        )
    }

    override fun onSelectTab(tabIndex: Int) {
        emitState(
            screenState.value.copy(
                searchUiState = screenState.value.searchUiState.copy(
                    selectedTabIndex = tabIndex
                )
            )
        )
    }

    override fun onFilterButtonClick() {
        emitState(
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
                emitState(
                    screenState.value.copy(
                        searchUiState = screenState.value.searchUiState.copy(
                            showFilterDialog = false,
                            selectedRating = selectedRating,
                            categories = screenState.value.searchUiState.categories.mapValues { it.key in selectedCategories }
                                .toMutableMap(),
                            isAllCategories = isAllCategories
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
                emitState(
                    screenState.value.copy(
                        searchUiState = screenState.value.searchUiState.copy(
                            filteredMoviesResult = flowOf(PagingData.from(filteredByCategoriesMovies.toMediaUiList())),
                            filteredTvShowsResult = flowOf(PagingData.from(filteredByCategoriesTvShows.toMediaUiList()))
                        ),
                        isLoading = false
                    )
                )
            },
            onError = { errorMessage ->
                emitState(
                    screenState.value.copy(
                        errorMessage = errorMessage,
                        isLoading = false
                    )
                )
            }
        )
    }

    override fun onClearFilterClick() {
        emitState(
            screenState.value.copy(
                searchUiState = screenState.value.searchUiState.copy(
                    showFilterDialog = false,
                    selectedRating = 0f,
                    isAllCategories = true,
                    categories = screenState.value.searchUiState.categories.mapValues { false }
                        .toMutableMap(),
                    filteredMoviesResult = screenState.value.searchUiState.moviesResult,
                    filteredTvShowsResult = screenState.value.searchUiState.tvShowsResult
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
                        emitState(
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
                        emitState(
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
                emitState(
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
                emitState(
                    screenState.value.copy(
                        errorMessage = errorMessage
                    )
                )
            }
        )
    }

    override fun onNavigateBack() {
        tryToExecute(
            execute = {
                appNavigator.navigateUp()
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

    override fun onRetryRecentSearches() {
        loadRecentSearches()
    }

    override fun onRetrySearchQuery() {
        searchQuery(screenState.value.searchUiState.searchQuery)
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

    private suspend fun Flow<PagingData<Media>>.collectAllItems(): List<Media> {
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
                override fun areItemsTheSame(oldItem: MediaUiState, newItem: MediaUiState): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: MediaUiState, newItem: MediaUiState): Boolean =
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
        override fun onInserted(position: Int, count: Int){}
        override fun onRemoved(position: Int, count: Int){}
        override fun onMoved(fromPosition: Int, toPosition: Int){}
        override fun onChanged(position: Int, count: Int, payload: Any?){}
    }



}