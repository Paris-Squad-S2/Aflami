package com.feature.search.searchUi.screen.findByActor

import androidx.paging.PagingSource
import com.domain.search.model.Media
import com.domain.search.model.MediaType
import com.domain.search.useCases.GetMediaByActorNameUseCase
import com.domain.search.useCases.IncrementCategoryInteractionUseCase
import com.domain.search.useCases.SortingMediaByCategoriesInteractionUseCase
import com.feature.search.searchUi.mapper.toMediaUiList
import com.feature.search.searchUi.pagging.FindByActorPagingSource
import com.feature.search.searchUi.screen.search.MediaTypeUi
import com.feature.search.searchUi.screen.utils.collectAllItems
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FindByActorViewModelTest {
    private lateinit var viewModel: FindByActorViewModel
    private lateinit var getMediaByActorNameUseCase: GetMediaByActorNameUseCase
    private lateinit var incrementCategoryInteractionUseCase: IncrementCategoryInteractionUseCase
    private lateinit var sortingMediaByCategoriesInteractionUseCase: SortingMediaByCategoriesInteractionUseCase

    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getMediaByActorNameUseCase = mockk(relaxed = true)
        incrementCategoryInteractionUseCase = mockk(relaxed = true)
        sortingMediaByCategoriesInteractionUseCase = mockk(relaxed = true)
        viewModel = FindByActorViewModel(
            savedStateHandle = mockk(relaxed = true),
            getMediaByActorNameUseCase = getMediaByActorNameUseCase,
            incrementCategoryInteractionUseCase = incrementCategoryInteractionUseCase,
            sortingMediaByCategoriesInteractionUseCase = sortingMediaByCategoriesInteractionUseCase,
            appNavigator = mockk(relaxed = true)
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be correct`() = runTest {
        val initialState = viewModel.screenState.value

        assertThat(initialState.uiState.searchQuery).isEqualTo("")
        assertThat(initialState.uiState.searchResult.collectAllItems()).isEmpty()
        assertThat(initialState.errorMessage).isNull()
    }

    @Test
    fun `onSearchQueryChange should update search query in state`() {
        val testQuery = "Tom Hanks"

        viewModel.onSearchQueryChange(testQuery)

        val currentState = viewModel.screenState.value
        assertThat(currentState.uiState.searchQuery).isEqualTo(testQuery)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `onSearchQueryChange with empty query should not trigger search`() = runTest {
        val emptyQuery = ""

        viewModel.onSearchQueryChange(emptyQuery)
        advanceUntilIdle()

        coVerify(exactly = 0) { getMediaByActorNameUseCase(any(), any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `onSearchQueryChange should debounce search calls`() = runTest {
        val query1 = "Tom"
        val query2 = "Tom Hanks"

        viewModel.onSearchQueryChange(query1)
        advanceTimeBy(200)
        viewModel.onSearchQueryChange(query2)
        advanceTimeBy(1100)

        coVerify(exactly = 0) { getMediaByActorNameUseCase(query2, any()) }
        coVerify(exactly = 0) { getMediaByActorNameUseCase(query1, any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `searchQueryChange should call use case and update state on success`() = runTest {
        val testQuery = "Tom Hanks"
        val mockMediaLists = listOf(
            Media(
                id = 1,
                imageUri = "",
                title = "Cast Away",
                type = MediaType.MOVIE,
                categories = listOf(12, 11),
                yearOfRelease = LocalDate(2023, 1, 1),
                rating = 2.3,
            ),
            Media(
                id = 2,
                imageUri = "",
                title = "Forrest Gump",
                type = MediaType.MOVIE,
                categories = listOf(12, 11),
                yearOfRelease = LocalDate(2023, 1, 1),
                rating = 2.7,
            ),
        )
        coEvery { getMediaByActorNameUseCase(testQuery, any()) } returns mockMediaLists
        coEvery { sortingMediaByCategoriesInteractionUseCase(mockMediaLists) } returns mockMediaLists

        viewModel.onSearchQueryChange(testQuery)
        advanceUntilIdle()

        val currentState = viewModel.screenState.value
        assertEquals(
            mockMediaLists.toMediaUiList().map { it.title },
            currentState.uiState.searchResult.collectAllItems().map { it.title })
        assertThat(currentState.errorMessage).isNull()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `searchQuery should handle error and update state`() = runTest {
        val testQuery = "Tom Hanks"
        val errorMessage = "Network error occurred"
        coEvery { getMediaByActorNameUseCase(testQuery, any()) } throws Exception(errorMessage)
        val pagingSource = FindByActorPagingSource(testQuery, getMediaByActorNameUseCase,sortingMediaByCategoriesInteractionUseCase)
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        viewModel.onSearchQueryChange(testQuery)
        advanceUntilIdle()

        val currentState = viewModel.screenState.value
        assertThat(currentState.uiState.searchResult.collectAllItems()).isEmpty()
        assertTrue(result is PagingSource.LoadResult.Error)
        assertEquals(errorMessage, (result).throwable.message)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `onMediaCardClick navigates to detailsScreen`() = runTest {
        val navMock = mockk<com.paris_2.aflami.appnavigation.AppNavigator>(relaxed = true)
        val viewModel = FindByActorViewModel(
            savedStateHandle = mockk(relaxed = true),
            getMediaByActorNameUseCase = getMediaByActorNameUseCase,
            incrementCategoryInteractionUseCase = incrementCategoryInteractionUseCase,
            sortingMediaByCategoriesInteractionUseCase = sortingMediaByCategoriesInteractionUseCase,
            appNavigator = navMock
        )
        val mediaUiState = com.feature.search.searchUi.screen.search.MediaUiState(
            id = 42,
            imageUri = "",
            title = "Test Movie",
            type = MediaTypeUi.MOVIE,
            categories = listOf(1, 2),
            yearOfRelease = LocalDate(2023, 1, 1),
            rating = 4.5,
        )
        viewModel.onMediaCardClick(mediaUiState)
        advanceUntilIdle()
        coVerify { navMock.navigate(any()) }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `onSearchQueryChange cancels previous debounce if new query is typed`() = runTest {
        val query1 = "Leonardo"
        val query2 = "DiCaprio"
        viewModel.onSearchQueryChange(query1)
        advanceTimeBy(200)
        viewModel.onSearchQueryChange(query2)
        advanceUntilIdle()
        coVerify(exactly = 0) { getMediaByActorNameUseCase(query1, any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `onSearchQueryChange with only spaces resets results to empty`() = runTest {
        viewModel.onSearchQueryChange("   ")
        advanceUntilIdle()
        assertThat(viewModel.screenState.value.uiState.searchResult.collectAllItems()).isEmpty()
    }


}