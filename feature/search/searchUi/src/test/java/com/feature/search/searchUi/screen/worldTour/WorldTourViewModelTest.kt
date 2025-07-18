package com.feature.search.searchUi.screen.worldTour

import com.domain.search.model.Country
import com.domain.search.useCases.AutoCompleteCountryUseCase
import com.domain.search.useCases.GetCountryCodeByNameUseCase
import com.domain.search.useCases.GetMediaByActorNameUseCase
import com.domain.search.useCases.GetMoviesOnlyByCountryNameUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.paris_2.aflami.appnavigation.AppNavigator as AppNavigator1

class WorldTourViewModelTest {
    private lateinit var viewModel: WorldTourViewModel
    private lateinit var getMediaByActorNameUseCase: GetMediaByActorNameUseCase
    private lateinit var autoCompleteCountryUseCase: AutoCompleteCountryUseCase
    private lateinit var getCountryCodeByNameUseCase: GetCountryCodeByNameUseCase
    private lateinit var getMoviesByCountryUseCase: GetMoviesOnlyByCountryNameUseCase

    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getMediaByActorNameUseCase = mockk(relaxed = true)
        autoCompleteCountryUseCase = mockk(relaxed = true)
        getMoviesByCountryUseCase = mockk(relaxed = true)
        getCountryCodeByNameUseCase = mockk(relaxed = true)

        viewModel = WorldTourViewModel(
            autoCompleteCountryUseCase = autoCompleteCountryUseCase,
            getCountryCodeByNameUseCase = getCountryCodeByNameUseCase,
            getMoviesByCountryUseCase = getMoviesByCountryUseCase,
            savedStateHandle = mockk(relaxed = true),
            appNavigator = mockk(relaxed = true)
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onSearchQueryChange should update search query immediately`() {
        val query = "United States"

        viewModel.onSearchQueryChange(query)

        assertEquals(query, viewModel.screenState.value.uiState.searchQuery)
    }

    @Test
    fun `onSearchQueryChange with empty query should not trigger autocomplete`() = runTest {
        val emptyQuery = ""

        viewModel.onSearchQueryChange(emptyQuery)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify(exactly = 0) { autoCompleteCountryUseCase(any()) }
        coVerify(exactly = 0) { getCountryCodeByNameUseCase(any()) }
    }

    @Test
    fun `onSearchQueryChange should trigger autocomplete and update hints`() = runTest {
        val query = "United"
        val mockHints = listOf(
            Country(
                englishName = "United States",
                countryCode = "US",
                arabicName = "الولايات المتحدة"
            ),
            Country(
                englishName = "United Kingdom",
                countryCode = "UK",
                arabicName = "المملكة المتحدة"
            )
        )
        coEvery { autoCompleteCountryUseCase(query) } returns mockHints

        viewModel.onSearchQueryChange(query)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { autoCompleteCountryUseCase(query) }
        assertEquals(
            mockHints,
            viewModel.screenState.value.uiState.hints
        )
    }

    @Test
    fun `onSearchQueryChange should cancel previous debounce job`() = runTest {
        val query1 = "United"
        val query2 = "France"
        coEvery { autoCompleteCountryUseCase(any()) } returns emptyList()
        coEvery { getCountryCodeByNameUseCase(any()) } returns null

        viewModel.onSearchQueryChange(query1)
        viewModel.onSearchQueryChange(query2)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify(exactly = 1) { autoCompleteCountryUseCase(query2) }
        coVerify(exactly = 0) { autoCompleteCountryUseCase(query1) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `onMediaCardClick triggers navigation`() = runTest {
        val navMock = mockk<AppNavigator1>(relaxed = true)
        val viewModel = WorldTourViewModel(
            savedStateHandle = mockk(relaxed = true),
            autoCompleteCountryUseCase = autoCompleteCountryUseCase,
            getCountryCodeByNameUseCase = getCountryCodeByNameUseCase,
            getMoviesByCountryUseCase = getMoviesByCountryUseCase,
            appNavigator = navMock
        )
        viewModel.onMediaCardClick(12, com.feature.search.searchUi.screen.search.MediaTypeUi.MOVIE)
        advanceUntilIdle()
        coVerify { navMock.navigate(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `onSearchQueryChange with no hints and no country code returns empty search results`() =
        runTest {
            coEvery { autoCompleteCountryUseCase("x") } returns emptyList()
            coEvery { getCountryCodeByNameUseCase("x") } returns null
            viewModel.onSearchQueryChange("x")
            advanceUntilIdle()
            assertThat(viewModel.screenState.value.uiState.searchResult).isNotNull()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `onSearchQueryChange with only spaces does not trigger search and clears hints`() =
        runTest {
            viewModel.onSearchQueryChange("   ")
            advanceUntilIdle()
            assertThat(viewModel.screenState.value.uiState.searchQuery).isEqualTo("   ")
            assertThat(viewModel.screenState.value.uiState.hints).isEmpty()
        }
}