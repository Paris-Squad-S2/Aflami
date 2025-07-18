package com.feature.search.searchUi.screen.search


import com.domain.search.model.CategoryModel
import com.domain.search.model.Media
import com.domain.search.model.MediaType
import com.domain.search.model.SearchHistoryModel
import com.domain.search.model.SearchType
import com.domain.search.useCases.ClearAllRecentSearchesUseCase
import com.domain.search.useCases.ClearRecentSearchUseCase
import com.domain.search.useCases.FilterByListOfCategoriesUseCase
import com.domain.search.useCases.FilterMediaByRatingUseCase
import com.domain.search.useCases.GetAllCategoriesUseCase
import com.domain.search.useCases.GetAllRecentSearchesUseCase
import com.domain.search.useCases.IncrementCategoryInteractionUseCase
import com.domain.search.useCases.SearchByQueryUseCase
import com.domain.search.useCases.SortingMediaByCategoriesInteractionUseCase
import com.feature.search.searchUi.mapper.toCategoryUiList
import com.feature.search.searchUi.mapper.toMediaUiList
import com.feature.search.searchUi.mapper.toUi
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {
    private val getAllRecentSearchesUseCase: GetAllRecentSearchesUseCase = mockk()
    private val clearAllRecentSearchesUseCase: ClearAllRecentSearchesUseCase = mockk()
    private val clearRecentSearchUseCase: ClearRecentSearchUseCase = mockk()
    private val searchByQueryUseCase: SearchByQueryUseCase = mockk()
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase = mockk()
    private val filterMediaByRatingUseCase: FilterMediaByRatingUseCase = mockk()
    private val filterMedByListOfCategoriesUseCase: FilterByListOfCategoriesUseCase = mockk()
    private val incrementCategoryInteractionUseCase: IncrementCategoryInteractionUseCase = mockk()
    private val sortingMediaByCategoriesInteractionUseCase: SortingMediaByCategoriesInteractionUseCase = mockk()

    private lateinit var viewModel: SearchViewModel

    private val testDispatcher = StandardTestDispatcher()

    private val mockMovie1 = Media(
        id = 1,
        imageUri = "http://image.url/movie1.jpg",
        title = "The Great Adventure",
        type = MediaType.MOVIE,
        categories = listOf(1, 3), // Action, Adventure
        yearOfRelease = LocalDate(2022, 10, 26),
        rating = 8.5
    )

    private val mockMovie2 = Media(
        id = 2,
        imageUri = "http://image.url/movie2.jpg",
        title = "Comedy Night",
        type = MediaType.MOVIE,
        categories = listOf(2),
        yearOfRelease = LocalDate(2023, 3, 15),
        rating = 6.8
    )

    private val mockTvShow1 = Media(
        id = 3,
        imageUri = "http://image.url/tvshow1.jpg",
        title = "Space Explorers",
        type = MediaType.TVSHOW,
        categories = listOf(4),
        yearOfRelease = LocalDate(2021, 1, 10),
        rating = 9.1
    )

    private val mockTvShow2 = Media(
        id = 4,
        imageUri = "http://image.url/tvshow2.jpg",
        title = "Mystery Lane",
        type = MediaType.TVSHOW,
        categories = listOf(5),
        yearOfRelease = LocalDate(2024, 6, 1),
        rating = 7.9
    )

    private val mockSearchHistory1 = SearchHistoryModel(
        searchTitle = "Lord of the Rings", searchDate = "2023-10-26", SearchType.Query
    )

    private val mockSearchHistory2 = SearchHistoryModel(
        searchTitle = "Inception", searchDate = "2023-11-15", SearchType.Query
    )

    private val mockCategory1 = CategoryModel(id = 1, name = "Action")
    private val mockCategory2 = CategoryModel(id = 2, name = "Comedy")
    private val mockCategory3 = CategoryModel(id = 3, name = "Adventure")
    private val mockCategory4 = CategoryModel(id = 4, name = "Sci-Fi")
    private val mockCategory5 = CategoryModel(id = 5, name = "Thriller")


    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        coEvery { getAllRecentSearchesUseCase() } returns flowOf(emptyList())
        coEvery { getAllCategoriesUseCase() } returns emptyList()

        viewModel = spyk(
            SearchViewModel(
                getAllRecentSearchesUseCase,
                clearAllRecentSearchesUseCase,
                clearRecentSearchUseCase,
                searchByQueryUseCase,
                getAllCategoriesUseCase,
                filterMediaByRatingUseCase,
                filterMedByListOfCategoriesUseCase,
                incrementCategoryInteractionUseCase,
                sortingMediaByCategoriesInteractionUseCase,
                appNavigator = mockk(relaxed = true)
            )
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `init should load recent searches and categories`() = runTest {
        val recentSearches = listOf(mockSearchHistory1)
        val categories = listOf(mockCategory1, mockCategory2)

        coEvery { getAllRecentSearchesUseCase() } returns flowOf(recentSearches)
        coEvery { getAllCategoriesUseCase() } returns categories

        viewModel = SearchViewModel(
            getAllRecentSearchesUseCase,
            clearAllRecentSearchesUseCase,
            clearRecentSearchUseCase,
            searchByQueryUseCase,
            getAllCategoriesUseCase,
            filterMediaByRatingUseCase,
            filterMedByListOfCategoriesUseCase,
            incrementCategoryInteractionUseCase,
            sortingMediaByCategoriesInteractionUseCase,
            appNavigator = mockk(relaxed = true)
        )

        advanceUntilIdle()

        assertThat(viewModel.screenState.value.searchUiState.recentSearches.map { it.searchTitle }).isEqualTo(
            recentSearches.map { it.searchTitle })
        assertThat(viewModel.screenState.value.errorMessage).isNull()
    }

    @Test
    fun `init should handle error when loading recent searches`() = runTest {
        val errorMessage = "Failed to load recent searches"
        coEvery { getAllRecentSearchesUseCase() } throws RuntimeException(errorMessage)

        viewModel = SearchViewModel(
            getAllRecentSearchesUseCase,
            clearAllRecentSearchesUseCase,
            clearRecentSearchUseCase,
            searchByQueryUseCase,
            getAllCategoriesUseCase,
            filterMediaByRatingUseCase,
            filterMedByListOfCategoriesUseCase,
            incrementCategoryInteractionUseCase,
            sortingMediaByCategoriesInteractionUseCase,
            appNavigator = mockk(relaxed = true)
        )

        advanceUntilIdle()

        assertThat(viewModel.screenState.value.errorMessage).isEqualTo(errorMessage)
    }

    @Test
    fun `init should handle error when loading categories`() = runTest {
        val errorMessage = "Failed to load categories"
        coEvery { getAllCategoriesUseCase() } throws RuntimeException(errorMessage)

        viewModel = SearchViewModel(
            getAllRecentSearchesUseCase,
            clearAllRecentSearchesUseCase,
            clearRecentSearchUseCase,
            searchByQueryUseCase,
            getAllCategoriesUseCase,
            filterMediaByRatingUseCase,
            filterMedByListOfCategoriesUseCase,
            incrementCategoryInteractionUseCase,
            sortingMediaByCategoriesInteractionUseCase,
            appNavigator = mockk(relaxed = true)
        )

        advanceUntilIdle()

        assertThat(viewModel.screenState.value.errorMessage).isEqualTo(errorMessage)
    }

    @Test
    fun `onSearchQueryChange should cancel previous debounce job if new query comes quickly`() =
        runTest {
            val query1 = "first_query"
            val query2 = "second_query"
            viewModel.onSearchQueryChange(query1)
            assertThat(viewModel.screenState.value.searchUiState.searchQuery).isEqualTo(query1)
            advanceTimeBy(200)


            viewModel.onSearchQueryChange(query2)
            assertThat(viewModel.screenState.value.searchUiState.searchQuery).isEqualTo(query2)
            advanceTimeBy(400)

            advanceUntilIdle()

            coVerify(exactly = 0) { searchByQueryUseCase(query1) }
            coVerify(exactly = 1) { searchByQueryUseCase(query2) }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `searchQuery should handle error during search and update error message`() = runTest {
        val query = "error_query"
        val errorMessage = "Network unavailable"

        coEvery { searchByQueryUseCase(query) } throws RuntimeException(errorMessage)

        viewModel.onSearchQueryChange(query)
        advanceUntilIdle()

        assertThat(viewModel.screenState.value.isLoading).isFalse()
        assertThat(viewModel.screenState.value.errorMessage).isEqualTo(errorMessage)

        assertThat(viewModel.screenState.value.searchUiState.moviesResult).isEmpty()
        assertThat(viewModel.screenState.value.searchUiState.tvShowsResult).isEmpty()
        assertThat(viewModel.screenState.value.searchUiState.filteredMoviesResult).isEmpty()
        assertThat(viewModel.screenState.value.searchUiState.filteredTvShowsResult).isEmpty()

        coVerify(exactly = 1) { searchByQueryUseCase(query) }
        coVerify(exactly = 1) { getAllRecentSearchesUseCase() }
        coVerify(exactly = 0) { filterMediaByRatingUseCase(any(), any()) }
        coVerify(exactly = 0) { filterMedByListOfCategoriesUseCase(any(), any()) }
    }

    @Test
    fun `onSelectTab updates selectedTabIndex in uiState`() = runTest {
        assertThat(viewModel.screenState.value.searchUiState.selectedTabIndex).isEqualTo(0)

        val newTabIndex1 = 1
        viewModel.onSelectTab(newTabIndex1)
        assertThat(viewModel.screenState.value.searchUiState.selectedTabIndex).isEqualTo(
            newTabIndex1
        )

        val newTabIndex2 = 2
        viewModel.onSelectTab(newTabIndex2)
        assertThat(viewModel.screenState.value.searchUiState.selectedTabIndex).isEqualTo(
            newTabIndex2
        )

        val newTabIndex0 = 0
        viewModel.onSelectTab(newTabIndex0)
        assertThat(viewModel.screenState.value.searchUiState.selectedTabIndex).isEqualTo(
            newTabIndex0
        )
    }

    @Test
    fun `onFilterButtonClick toggles showFilterDialog state`() = runTest {
        assertThat(viewModel.screenState.value.searchUiState.showFilterDialog).isFalse()

        viewModel.onFilterButtonClick()
        assertThat(viewModel.screenState.value.searchUiState.showFilterDialog).isTrue()

        viewModel.onFilterButtonClick()
        assertThat(viewModel.screenState.value.searchUiState.showFilterDialog).isFalse()

        viewModel.onFilterButtonClick()
        assertThat(viewModel.screenState.value.searchUiState.showFilterDialog).isTrue()
    }

    @Test
    fun `onApplyFilterButtonClick applies filters and updates state on success`() = runTest {

        val initialMovies = listOf(mockMovie1, mockMovie2)
        val initialTvShows = listOf(mockTvShow1, mockTvShow2)

        viewModel.emitState(
            viewModel.screenState.value.copy(
                searchUiState = viewModel.screenState.value.searchUiState.copy(
                    moviesResult = initialMovies.toMediaUiList(),
                    tvShowsResult = initialTvShows.toMediaUiList(),
                    filteredMoviesResult = initialMovies.toMediaUiList(),
                    filteredTvShowsResult = initialTvShows.toMediaUiList(),
                    showFilterDialog = true,
                    categories = mapOf(
                        mockCategory1.toUi() to true, mockCategory2.toUi() to true,
                        mockCategory3.toUi() to true, mockCategory4.toUi() to true,
                        mockCategory5.toUi() to true
                    ),
                    isAllCategories = false
                )
            )
        )
        advanceUntilIdle()

        val selectedRating = 7.0f
        val selectedCategories = listOf(mockCategory1, mockCategory4)

        val filteredByRatingMovies = listOf(mockMovie1)
        val filteredByRatingTvShows = listOf(mockTvShow1, mockTvShow2)

        val finalFilteredMovies = listOf(mockMovie1)
        val finalFilteredTvShows = listOf(mockTvShow1)

        every {
            filterMediaByRatingUseCase(
                selectedRating,
                initialMovies
            )
        } returns filteredByRatingMovies
        every {
            filterMediaByRatingUseCase(
                selectedRating,
                initialTvShows
            )
        } returns filteredByRatingTvShows
        every {
            filterMedByListOfCategoriesUseCase(
                selectedCategories.map { it.id },
                filteredByRatingMovies
            )
        } returns finalFilteredMovies
        every {
            filterMedByListOfCategoriesUseCase(
                selectedCategories.map { it.id },
                filteredByRatingTvShows
            )
        } returns finalFilteredTvShows

        viewModel.onApplyFilterButtonClick(
            selectedRating = selectedRating,
            selectedCategories = selectedCategories.toCategoryUiList(),
            isAllCategories = false
        )
        advanceUntilIdle()

        assertThat(viewModel.screenState.value.isLoading).isFalse()
        assertThat(viewModel.screenState.value.searchUiState.selectedRating).isEqualTo(
            selectedRating
        )
        assertThat(viewModel.screenState.value.searchUiState.filteredMoviesResult.map { it.title }).isEqualTo(
            finalFilteredMovies.map { it.title }
        )
        assertThat(viewModel.screenState.value.searchUiState.filteredTvShowsResult.map { it.title }).isEqualTo(
            finalFilteredTvShows.map { it.title }
        )
    }

    @Test
    fun `onApplyFilterButtonClick handles error and updates error message`() = runTest {
        val errorMessage = "Failed to apply filters"
        val selectedRating = 7.0f
        val selectedCategories = listOf(mockCategory1)

        viewModel.emitState(
            viewModel.screenState.value.copy(
                searchUiState = viewModel.screenState.value.searchUiState.copy(
                    moviesResult = listOf(mockMovie1.toUi()),
                    tvShowsResult = listOf(mockTvShow1.toUi()),
                    showFilterDialog = false
                )
            )
        )
        advanceUntilIdle()

        every { filterMediaByRatingUseCase(any(), any()) } throws RuntimeException(errorMessage)


        viewModel.onApplyFilterButtonClick(
            selectedRating,
            selectedCategories = selectedCategories.toCategoryUiList(),
            isAllCategories = false
        )
        advanceUntilIdle()

        // Then

        assertThat(viewModel.screenState.value.errorMessage).isEqualTo(errorMessage)
        assertThat(viewModel.screenState.value.searchUiState.showFilterDialog).isFalse()
    }

    @Test
    fun `onClearFilterClick resets filter states and filtered results`() = runTest {
        val initialMovies = listOf(mockMovie1, mockMovie2)
        val initialTvShows = listOf(mockTvShow1, mockTvShow2)

        viewModel.emitState(
            viewModel.screenState.value.copy(
                searchUiState = viewModel.screenState.value.searchUiState.copy(
                    showFilterDialog = true,
                    selectedRating = 8.0f,
                    categories = mapOf(
                        mockCategory1.toUi() to true,
                        mockCategory2.toUi() to false
                    ),
                    moviesResult = initialMovies.toMediaUiList(),
                    tvShowsResult = initialTvShows.toMediaUiList(),
                    filteredMoviesResult = listOf(mockMovie1.toUi()),
                    filteredTvShowsResult = listOf(mockTvShow1.toUi())
                )
            )
        )
        advanceUntilIdle()


        viewModel.onClearFilterClick()
        advanceUntilIdle()


        assertThat(viewModel.screenState.value.searchUiState.showFilterDialog).isFalse()
        assertThat(viewModel.screenState.value.searchUiState.selectedRating).isEqualTo(0f)
        viewModel.screenState.value.searchUiState.categories.forEach { (_, isSelected) ->
            assertThat(isSelected).isTrue()
        }
        assertThat(viewModel.screenState.value.searchUiState.filteredMoviesResult.map { it.title }).isEqualTo(
            initialMovies.map { it.title })
        assertThat(viewModel.screenState.value.searchUiState.filteredTvShowsResult.map { it.title }).isEqualTo(
            initialTvShows.map { it.title }
        )
    }

    @Test
    fun `onClearAllRecentSearches calls use case and reloads recent searches on success`() =
        runTest {
            val initialRecentSearches = listOf(mockSearchHistory1, mockSearchHistory2)

            // Always emit the current state of recent searches, which will be updated after clear
            val recentSearchesFlow = MutableStateFlow(initialRecentSearches)
            coEvery { getAllRecentSearchesUseCase() } returns recentSearchesFlow

            coEvery { clearAllRecentSearchesUseCase() } answers {
                recentSearchesFlow.value = emptyList()
            }

            viewModel = SearchViewModel(
                getAllRecentSearchesUseCase,
                clearAllRecentSearchesUseCase,
                clearRecentSearchUseCase,
                searchByQueryUseCase,
                getAllCategoriesUseCase,
                filterMediaByRatingUseCase,
                filterMedByListOfCategoriesUseCase,
                incrementCategoryInteractionUseCase,
                sortingMediaByCategoriesInteractionUseCase,
                appNavigator = mockk(relaxed = true)
            )
            advanceUntilIdle()

            assertThat(viewModel.screenState.value.searchUiState.recentSearches.map { it.searchTitle }).isEqualTo(
                initialRecentSearches.map { it.searchTitle })

            viewModel.onClearAllRecentSearches()
            advanceUntilIdle()

            coVerify(exactly = 1) { clearAllRecentSearchesUseCase() }
            assertThat(viewModel.screenState.value.searchUiState.recentSearches).isEmpty()
            assertThat(viewModel.screenState.value.errorMessage).isNull()
        }

    @Test
    fun `onClearAllRecentSearches handles error and updates error message`() = runTest {
        val errorMessage = "Failed to clear all recent searches"
        coEvery { clearAllRecentSearchesUseCase() } throws RuntimeException(errorMessage)
        coEvery { getAllRecentSearchesUseCase() } returns flowOf(listOf(mockSearchHistory1))

        viewModel = SearchViewModel(
            getAllRecentSearchesUseCase,
            clearAllRecentSearchesUseCase,
            clearRecentSearchUseCase,
            searchByQueryUseCase,
            getAllCategoriesUseCase,
            filterMediaByRatingUseCase,
            filterMedByListOfCategoriesUseCase,
            incrementCategoryInteractionUseCase,
            sortingMediaByCategoriesInteractionUseCase,
            appNavigator = mockk(relaxed = true)
        )
        advanceUntilIdle()

        assertThat(viewModel.screenState.value.searchUiState.recentSearches.map { it.searchTitle }).isEqualTo(
            listOf(mockSearchHistory1.searchTitle)
        )

        viewModel.onClearAllRecentSearches()
        advanceUntilIdle()

        coVerify(exactly = 1) { clearAllRecentSearchesUseCase() }
        assertThat(viewModel.screenState.value.errorMessage).isEqualTo(errorMessage)
        assertThat(viewModel.screenState.value.searchUiState.recentSearches.map { it.searchTitle }).isEqualTo(
            listOf(
                mockSearchHistory1.searchTitle
            )
        )
    }


    @Test
    fun `onClearRecentSearch calls use case with id and reloads recent searches on success`() =
        runTest {
            val idToClear = mockSearchHistory1.searchTitle
            val initialRecentSearches = listOf(mockSearchHistory1, mockSearchHistory2)
            val afterClearRecentSearches = listOf(mockSearchHistory2)

            val recentSearchesFlow = MutableStateFlow(initialRecentSearches)
            coEvery { getAllRecentSearchesUseCase() } returns recentSearchesFlow
            coEvery { clearRecentSearchUseCase(idToClear, SearchType.Query) } answers {
                recentSearchesFlow.value = afterClearRecentSearches
            }


            viewModel = SearchViewModel(
                getAllRecentSearchesUseCase,
                clearAllRecentSearchesUseCase,
                clearRecentSearchUseCase,
                searchByQueryUseCase,
                getAllCategoriesUseCase,
                filterMediaByRatingUseCase,
                filterMedByListOfCategoriesUseCase,
                incrementCategoryInteractionUseCase,
                sortingMediaByCategoriesInteractionUseCase,
                appNavigator = mockk(relaxed = true)
            )
            advanceUntilIdle()

            assertThat(viewModel.screenState.value.searchUiState.recentSearches.map { it.searchTitle }).isEqualTo(
                initialRecentSearches.map { it.searchTitle }
            )


            viewModel.onClearRecentSearch(idToClear, SearchTypeUi.Query)
            advanceUntilIdle()


            coVerify(exactly = 1) { clearRecentSearchUseCase(idToClear, SearchType.Query) }
            assertThat(viewModel.screenState.value.searchUiState.recentSearches.map { it.searchTitle }).isEqualTo(
                afterClearRecentSearches.map { it.searchTitle }
            )
            assertThat(viewModel.screenState.value.errorMessage).isNull()
        }
}