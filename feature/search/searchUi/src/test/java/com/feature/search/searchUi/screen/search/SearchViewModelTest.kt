package com.feature.search.searchUi.screen.search

import com.domain.search.model.CategoryModel
import com.domain.search.model.Media
import com.domain.search.model.MediaType
import com.domain.search.model.SearchHistoryModel
import com.domain.search.useCases.AddRecentSearchUseCase
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
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SearchViewModelTest {
    private val getAllRecentSearchesUseCase: GetAllRecentSearchesUseCase = mockk()
    private val clearAllRecentSearchesUseCase: ClearAllRecentSearchesUseCase = mockk()
    private val clearRecentSearchUseCase: ClearRecentSearchUseCase = mockk()
    private val searchByQueryUseCase: SearchByQueryUseCase = mockk()
    private val addRecentSearchesUseCase: AddRecentSearchUseCase = mockk()
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
        coEvery { getAllRecentSearchesUseCase.invoke() } returns emptyList()
        coEvery { getAllCategoriesUseCase.invoke() } returns emptyList()

        viewModel = spyk(
            SearchViewModel(
                getAllRecentSearchesUseCase,
                clearAllRecentSearchesUseCase,
                clearRecentSearchUseCase,
                searchByQueryUseCase,
                addRecentSearchesUseCase,
                getAllCategoriesUseCase,
                filterMediaByRatingUseCase,
                filterMedByListOfCategoriesUseCase
            )
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `init should load recent searches and categories`() = runTest {
        val recentSearches = listOf(SearchHistoryModel("1", "query1"))
        val categories = listOf(CategoryModel(1, "Action"), CategoryModel(2, "Comedy"))

        coEvery { getAllRecentSearchesUseCase.invoke() } returns recentSearches
        coEvery { getAllCategoriesUseCase.invoke() } returns categories

        viewModel = SearchViewModel(
            getAllRecentSearchesUseCase,
            clearAllRecentSearchesUseCase,
            clearRecentSearchUseCase,
            searchByQueryUseCase,
            addRecentSearchesUseCase,
            getAllCategoriesUseCase,
            filterMediaByRatingUseCase,
            filterMedByListOfCategoriesUseCase
        )

        advanceUntilIdle()

        assertThat(viewModel.screenState.value.uiState.recentSearches).isEqualTo(recentSearches)
        assertThat(viewModel.screenState.value.uiState.categories).isEqualTo(
            mapOf(
                CategoryModel(1, "Action") to true, CategoryModel(2, "Comedy") to true
            )
        )
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
            addRecentSearchesUseCase,
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
            addRecentSearchesUseCase,
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

            coEvery { searchByQueryUseCase(any()) } returns emptyList() // Mock any search to return empty
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
            coEvery { addRecentSearchesUseCase(any()) } just Runs
            coEvery { getAllRecentSearchesUseCase.invoke() } returns emptyList() // For simplicity in this test

            // First query
            viewModel.onSearchQueryChange(query1)
            assertThat(viewModel.screenState.value.uiState.searchQuery).isEqualTo(query1)
            advanceTimeBy(200) // Advance partially, not enough for debounce

            // Second query before first debounce finishes
            viewModel.onSearchQueryChange(query2)
            assertThat(viewModel.screenState.value.uiState.searchQuery).isEqualTo(query2)
            advanceTimeBy(400) // Advance enough for query2's debounce to pass (200 + 400 = 600 total)

            advanceUntilIdle() // Ensure all coroutines complete

            // Only the second query's searchByQueryUseCase should have been called
            coVerify(exactly = 0) { searchByQueryUseCase(query1) }
            coVerify(exactly = 1) { searchByQueryUseCase(query2) }
            coVerify(exactly = 1) { addRecentSearchesUseCase(query2) }
        }

    @Test
    fun `searchQuery should handle error during search and update error message`() = runTest {
        val query = "error_query"
        val errorMessage = "Network unavailable"

        // Mock searchByQueryUseCase to throw an exception
        coEvery { searchByQueryUseCase(query) } throws RuntimeException(errorMessage)
        coEvery { addRecentSearchesUseCase(query) } just Runs // Mocked but won't be called on error

        // Call onSearchQueryChange to trigger searchQuery via debounce
        viewModel.onSearchQueryChange(query)
        advanceUntilIdle()

        // Verify loading state and error message
        assertThat(viewModel.screenState.value.isLoading).isFalse()
        assertThat(viewModel.screenState.value.errorMessage).isEqualTo(errorMessage)

        // Verify results are empty or unchanged from initial state
        assertThat(viewModel.screenState.value.uiState.moviesResult).isEmpty() // Assuming they start empty
        assertThat(viewModel.screenState.value.uiState.tvShowsResult).isEmpty()
        assertThat(viewModel.screenState.value.uiState.filteredMoviesResult).isEmpty()
        assertThat(viewModel.screenState.value.uiState.filteredTvShowsResult).isEmpty()

        // Verify use cases were called
        coVerify(exactly = 1) { searchByQueryUseCase(query) }
        coVerify(exactly = 0) { addRecentSearchesUseCase(any()) } // Should not be called on error
        // getAllRecentSearchesUseCase is called once in init, but not again after error in searchQuery
        coVerify(exactly = 1) { getAllRecentSearchesUseCase.invoke() }
        coVerify(exactly = 0) { filterMediaByRatingUseCase(any(), any()) }
        coVerify(exactly = 0) { filterMedByListOfCategoriesUseCase(any(), any()) }
    }

}