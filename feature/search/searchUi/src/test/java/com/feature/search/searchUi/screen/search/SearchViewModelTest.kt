package com.feature.search.searchUi.screen.search


import com.domain.search.model.CategoryModel
import com.domain.search.model.Media
import com.domain.search.model.MediaType
import com.domain.search.model.SearchHistoryModel
import com.domain.search.useCases.ClearAllRecentSearchesUseCase
import com.domain.search.useCases.ClearRecentSearchUseCase
import com.domain.search.useCases.FilterByListOfCategoriesUseCase
import com.domain.search.useCases.FilterMediaByRatingUseCase
import com.domain.search.useCases.GetAllCategoriesUseCase
import com.domain.search.useCases.GetAllRecentSearchesUseCase
import com.domain.search.useCases.SearchByQueryUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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

    val mockSearchHistory1 = SearchHistoryModel(
        searchTitle = "Lord of the Rings", searchDate = "2023-10-26"
    )

    val mockSearchHistory2 = SearchHistoryModel(
        searchTitle = "Inception", searchDate = "2023-11-15"
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

        coEvery { getAllRecentSearchesUseCase.invoke() } returns flowOf(emptyList())
        coEvery { getAllCategoriesUseCase.invoke() } returns emptyList()

        viewModel = spyk(
            SearchViewModel(
                getAllRecentSearchesUseCase,
                clearAllRecentSearchesUseCase,
                clearRecentSearchUseCase,
                searchByQueryUseCase,
                getAllCategoriesUseCase,
                filterMediaByRatingUseCase,
                filterMedByListOfCategoriesUseCase
            )
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `init should load recent searches and categories`() = runTest {
        val recentSearches = listOf(mockSearchHistory1)
        val categories = listOf(mockCategory1, mockCategory2)

        coEvery { getAllRecentSearchesUseCase.invoke() } returns flowOf(recentSearches)
        coEvery { getAllCategoriesUseCase.invoke() } returns categories

        viewModel = SearchViewModel(
            getAllRecentSearchesUseCase,
            clearAllRecentSearchesUseCase,
            clearRecentSearchUseCase,
            searchByQueryUseCase,
            getAllCategoriesUseCase,
            filterMediaByRatingUseCase,
            filterMedByListOfCategoriesUseCase
        )

        advanceUntilIdle()

        assertThat(viewModel.screenState.value.uiState.recentSearches).isEqualTo(recentSearches)
        assertThat(viewModel.screenState.value.errorMessage).isNull()
    }

    @Test
    fun `init should handle error when loading recent searches`() = runTest {
        val errorMessage = "Failed to load recent searches"
        coEvery { getAllRecentSearchesUseCase.invoke() } throws RuntimeException(errorMessage)

        viewModel = SearchViewModel(
            getAllRecentSearchesUseCase,
            clearAllRecentSearchesUseCase,
            clearRecentSearchUseCase,
            searchByQueryUseCase,
            getAllCategoriesUseCase,
            filterMediaByRatingUseCase,
            filterMedByListOfCategoriesUseCase
        )

        advanceUntilIdle()

        assertThat(viewModel.screenState.value.errorMessage).isEqualTo(errorMessage)
    }

    @Test
    fun `init should handle error when loading categories`() = runTest {
        val errorMessage = "Failed to load categories"
        coEvery { getAllCategoriesUseCase.invoke() } throws RuntimeException(errorMessage)

        viewModel = SearchViewModel(
            getAllRecentSearchesUseCase,
            clearAllRecentSearchesUseCase,
            clearRecentSearchUseCase,
            searchByQueryUseCase,
            getAllCategoriesUseCase,
            filterMediaByRatingUseCase,
            filterMedByListOfCategoriesUseCase
        )

        advanceUntilIdle()

        assertThat(viewModel.screenState.value.errorMessage).isEqualTo(errorMessage)
    }

    @Test
    fun `onSearchQueryChange should cancel previous debounce job if new query comes quickly`() =
        runTest {
            val query1 = "first_query"
            val query2 = "second_query"

            coEvery { searchByQueryUseCase(any()) } returns emptyList()
            every {
                filterMediaByRatingUseCase(
                    any(), any()
                )
            } answers { it.invocation.args[1] as List<Media> }
            every {
                filterMedByListOfCategoriesUseCase(
                    any(), any()
                )
            } answers { it.invocation.args[1] as List<Media> }
            coEvery { getAllRecentSearchesUseCase.invoke() } returns flowOf(emptyList())


            viewModel.onSearchQueryChange(query1)
            assertThat(viewModel.screenState.value.uiState.searchQuery).isEqualTo(query1)
            advanceTimeBy(200)


            viewModel.onSearchQueryChange(query2)
            assertThat(viewModel.screenState.value.uiState.searchQuery).isEqualTo(query2)
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

        assertThat(viewModel.screenState.value.uiState.moviesResult).isEmpty()
        assertThat(viewModel.screenState.value.uiState.tvShowsResult).isEmpty()
        assertThat(viewModel.screenState.value.uiState.filteredMoviesResult).isEmpty()
        assertThat(viewModel.screenState.value.uiState.filteredTvShowsResult).isEmpty()

        coVerify(exactly = 1) { searchByQueryUseCase(query) }
        coVerify(exactly = 1) { getAllRecentSearchesUseCase.invoke() }
        coVerify(exactly = 0) { filterMediaByRatingUseCase(any(), any()) }
        coVerify(exactly = 0) { filterMedByListOfCategoriesUseCase(any(), any()) }
    }

    @Test
    fun `onSelectTab updates selectedTabIndex in uiState`() = runTest {
        assertThat(viewModel.screenState.value.uiState.selectedTabIndex).isEqualTo(0)

        val newTabIndex1 = 1
        viewModel.onSelectTab(newTabIndex1)
        assertThat(viewModel.screenState.value.uiState.selectedTabIndex).isEqualTo(newTabIndex1)

        val newTabIndex2 = 2
        viewModel.onSelectTab(newTabIndex2)
        assertThat(viewModel.screenState.value.uiState.selectedTabIndex).isEqualTo(newTabIndex2)

        val newTabIndex0 = 0
        viewModel.onSelectTab(newTabIndex0)
        assertThat(viewModel.screenState.value.uiState.selectedTabIndex).isEqualTo(newTabIndex0)
    }

    @Test
    fun `onFilterButtonClick toggles showFilterDialog state`() = runTest {
        assertThat(viewModel.screenState.value.uiState.showFilterDialog).isFalse()

        viewModel.onFilterButtonClick()
        assertThat(viewModel.screenState.value.uiState.showFilterDialog).isTrue()

        viewModel.onFilterButtonClick()
        assertThat(viewModel.screenState.value.uiState.showFilterDialog).isFalse()

        viewModel.onFilterButtonClick()
        assertThat(viewModel.screenState.value.uiState.showFilterDialog).isTrue()
    }

    @Test
    fun `onApplyFilterButtonClick applies filters and updates state on success`() = runTest {

        val initialMovies = listOf(mockMovie1, mockMovie2)
        val initialTvShows = listOf(mockTvShow1, mockTvShow2)

        viewModel.emitState(
            viewModel.screenState.value.copy(
                uiState = viewModel.screenState.value.uiState.copy(
                    moviesResult = initialMovies,
                    tvShowsResult = initialTvShows,
                    filteredMoviesResult = initialMovies,
                    filteredTvShowsResult = initialTvShows,
                    showFilterDialog = true,
                    categories = mapOf(
                        mockCategory1 to true, mockCategory2 to true,
                        mockCategory3 to true, mockCategory4 to true,
                        mockCategory5 to true
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
            selectedCategories = selectedCategories,
            isAllCategories = false
        )
        advanceUntilIdle()

        assertThat(viewModel.screenState.value.isLoading).isFalse()
        assertThat(viewModel.screenState.value.uiState.selectedRating).isEqualTo(selectedRating)
        assertThat(viewModel.screenState.value.uiState.filteredMoviesResult).isEqualTo(
            finalFilteredMovies
        )
        assertThat(viewModel.screenState.value.uiState.filteredTvShowsResult).isEqualTo(
            finalFilteredTvShows
        )
    }

    @Test
    fun `onApplyFilterButtonClick handles error and updates error message`() = runTest {
        val errorMessage = "Failed to apply filters"
        val selectedRating = 7.0f
        val selectedCategories = listOf(mockCategory1)

        viewModel.emitState(
            viewModel.screenState.value.copy(
                uiState = viewModel.screenState.value.uiState.copy(
                    moviesResult = listOf(mockMovie1),
                    tvShowsResult = listOf(mockTvShow1),
                    showFilterDialog = false
                )
            )
        )
        advanceUntilIdle()

        every { filterMediaByRatingUseCase(any(), any()) } throws RuntimeException(errorMessage)


        viewModel.onApplyFilterButtonClick(
            selectedRating,
            selectedCategories = selectedCategories,
            isAllCategories = false
        )
        advanceUntilIdle()

        // Then

        assertThat(viewModel.screenState.value.errorMessage).isEqualTo(errorMessage)
        assertThat(viewModel.screenState.value.uiState.showFilterDialog).isFalse()
    }

    @Test
    fun `onClearFilterClick resets filter states and filtered results`() = runTest {
        val initialMovies = listOf(mockMovie1, mockMovie2)
        val initialTvShows = listOf(mockTvShow1, mockTvShow2)

        viewModel.emitState(
            viewModel.screenState.value.copy(
                uiState = viewModel.screenState.value.uiState.copy(
                    showFilterDialog = true,
                    selectedRating = 8.0f,
                    categories = mapOf(
                        mockCategory1 to true,
                        mockCategory2 to false
                    ),
                    moviesResult = initialMovies,
                    tvShowsResult = initialTvShows,
                    filteredMoviesResult = listOf(mockMovie1),
                    filteredTvShowsResult = listOf(mockTvShow1)
                )
            )
        )
        advanceUntilIdle()


        viewModel.onClearFilterClick()
        advanceUntilIdle()


        assertThat(viewModel.screenState.value.uiState.showFilterDialog).isFalse()
        assertThat(viewModel.screenState.value.uiState.selectedRating).isEqualTo(0f)
        viewModel.screenState.value.uiState.categories.forEach { (category, isSelected) ->
            assertThat(isSelected).isTrue()
        }
        assertThat(viewModel.screenState.value.uiState.filteredMoviesResult).isEqualTo(initialMovies)
        assertThat(viewModel.screenState.value.uiState.filteredTvShowsResult).isEqualTo(
            initialTvShows
        )
    }

    @Test
    fun `onClearAllRecentSearches calls use case and reloads recent searches on success`() =
        runTest {
            val initialRecentSearches = listOf(mockSearchHistory1, mockSearchHistory2)

            // Mock getAllRecentSearchesUseCase to emit initial list, then empty list on subsequent collection
            // This setup is correct *if* the ViewModel re-collects or the flow itself emits.
            coEvery { getAllRecentSearchesUseCase() } returns flowOf(initialRecentSearches) andThen flowOf(
                emptyList()
            )
            coEvery { clearAllRecentSearchesUseCase() } just Runs

            viewModel = SearchViewModel(
                getAllRecentSearchesUseCase,
                clearAllRecentSearchesUseCase,
                clearRecentSearchUseCase,
                searchByQueryUseCase,
                getAllCategoriesUseCase,
                filterMediaByRatingUseCase,
                filterMedByListOfCategoriesUseCase
            )
            advanceUntilIdle() // Allow initial collection to complete

            assertThat(viewModel.screenState.value.uiState.recentSearches).isEqualTo(
                initialRecentSearches
            )

            viewModel.onClearAllRecentSearches()
            advanceUntilIdle() // Allow the clear operation and subsequent state updates to complete

            // Then
            coVerify(exactly = 1) { clearAllRecentSearchesUseCase() }

            // The key is that the ViewModel's flow collection should now have processed the `emptyList()`
            // due to the `andThen` and the continuous collection from the `init` block.
            assertThat(viewModel.screenState.value.uiState.recentSearches).isEmpty()
            assertThat(viewModel.screenState.value.errorMessage).isNull()
        }

    @Test
    fun `onClearAllRecentSearches handles error and updates error message`() = runTest {
        val errorMessage = "Failed to clear all recent searches"
        coEvery { clearAllRecentSearchesUseCase.invoke() } throws RuntimeException(errorMessage)
        coEvery { getAllRecentSearchesUseCase.invoke() } returns flowOf(listOf(mockSearchHistory1))

        viewModel.onClearAllRecentSearches()
        advanceUntilIdle()

        coVerify(exactly = 1) { clearAllRecentSearchesUseCase.invoke() }
        coVerify(exactly = 1) { getAllRecentSearchesUseCase.invoke() }
        assertThat(viewModel.screenState.value.errorMessage).isEqualTo(errorMessage)
        assertThat(viewModel.screenState.value.uiState.recentSearches).isEqualTo(
            listOf(
                mockSearchHistory1
            )
        )
    }


    @Test
    fun `onClearRecentSearch calls use case with id and reloads recent searches on success`() =
        runTest {
            val idToClear = mockSearchHistory1.searchTitle
            val initialRecentSearches = listOf(mockSearchHistory1, mockSearchHistory2)
            val afterClearRecentSearches = listOf(mockSearchHistory2)

            coEvery { getAllRecentSearchesUseCase.invoke() } returns flowOf(initialRecentSearches) andThen flowOf(
                afterClearRecentSearches
            )
            coEvery { clearRecentSearchUseCase(idToClear) } just Runs


            viewModel = SearchViewModel(
                getAllRecentSearchesUseCase,
                clearAllRecentSearchesUseCase,
                clearRecentSearchUseCase,
                searchByQueryUseCase,
                getAllCategoriesUseCase,
                filterMediaByRatingUseCase,
                filterMedByListOfCategoriesUseCase
            )
            advanceUntilIdle()

            assertThat(viewModel.screenState.value.uiState.recentSearches).isEqualTo(
                initialRecentSearches
            )


            viewModel.onClearRecentSearch(idToClear)
            advanceUntilIdle()


            coVerify(exactly = 1) { clearRecentSearchUseCase(idToClear) }
            coVerify(exactly = 3) { getAllRecentSearchesUseCase.invoke() }
            assertThat(viewModel.screenState.value.uiState.recentSearches).isEqualTo(
                afterClearRecentSearches
            )
            assertThat(viewModel.screenState.value.errorMessage).isNull()
        }
}