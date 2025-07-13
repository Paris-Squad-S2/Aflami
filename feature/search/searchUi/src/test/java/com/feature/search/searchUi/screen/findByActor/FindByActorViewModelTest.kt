package com.feature.search.searchUi.screen.findByActor

import com.domain.search.model.Media
import com.domain.search.useCases.GetMediaByActorNameUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FindByActorViewModelTest {
    private lateinit var viewModel: FindByActorViewModel
    private lateinit var getMediaByActorNameUseCase: GetMediaByActorNameUseCase


    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getMediaByActorNameUseCase = mockk(relaxed = true)
        viewModel = FindByActorViewModel(
            savedStateHandle = mockk(relaxed = true),
            getMediaByActorNameUseCase = getMediaByActorNameUseCase
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be correct`() {
        val initialState = viewModel.screenState.value

        assertThat(initialState.uiState.searchQuery).isEqualTo("")
        assertThat(initialState.uiState.searchResult).isEmpty()
        assertThat(initialState.isLoading).isFalse()
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

        coVerify(exactly = 0) { getMediaByActorNameUseCase(any()) }
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

        coVerify(exactly = 1) { getMediaByActorNameUseCase(query2) }
        coVerify(exactly = 0) { getMediaByActorNameUseCase(query1) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `searchQueryChange should call use case and update state on success`() = runTest {
        val testQuery = "Tom Hanks"
        val mockMediaUiStateLists = listOf(
            mockk<Media> { every { title } returns "Cast Away" },
            mockk<Media> { every { title } returns "Forrest Gump" }
        )
        coEvery { getMediaByActorNameUseCase(testQuery) } returns mockMediaUiStateLists

        viewModel.onSearchQueryChange(testQuery)
        advanceUntilIdle()

        val currentState = viewModel.screenState.value
        assertThat(currentState.uiState.searchResult).isEqualTo(mockMediaUiStateLists)
        assertThat(currentState.isLoading).isFalse()
        assertThat(currentState.errorMessage).isNull()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `searchQuery should handle error and update state`() = runTest {
        val testQuery = "Tom Hanks"
        val errorMessage = "Network error occurred"
        coEvery { getMediaByActorNameUseCase(testQuery) } throws Exception(errorMessage)

        viewModel.onSearchQueryChange(testQuery)
        advanceUntilIdle()

        val currentState = viewModel.screenState.value
        assertThat(currentState.uiState.searchResult).isEmpty()
        assertThat(currentState.isLoading).isFalse()
        assertThat(currentState.errorMessage).isEqualTo(errorMessage)
    }

}