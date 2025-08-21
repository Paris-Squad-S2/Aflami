package com.feature.search.searchUi.screen.worldTour

import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.search.searchUi.screen.search.MediaTypeUi
import com.feature.search.searchUi.screen.search.MediaUiState
import com.google.common.truth.Truth.assertThat
import com.paris.domain.media.entity.Category
import com.paris.domain.media.entity.Country
import com.paris.domain.media.useCase.media.GetMediaByActorNameUseCase
import com.paris.domain.media.useCase.media.SortingMediaByCategoriesInteractionUseCase
import com.paris.domain.media.useCase.movie.GetMoviesOnlyByCountryNameUseCase
import com.paris.domain.media.useCase.search.AutoCompleteCountryUseCase
import com.paris.domain.media.useCase.search.GetCountryCodeByNameUseCase
import com.paris.domain.media.useCase.search.IncrementCategoryInteractionUseCase
import com.paris.domain.user.usecase.SettingsUseCase
import io.mockk.clearAllMocks
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
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WorldTourViewModelTest {
    private lateinit var viewModel: WorldTourViewModel
    private lateinit var getMediaByActorNameUseCase: GetMediaByActorNameUseCase
    private lateinit var autoCompleteCountryUseCase: AutoCompleteCountryUseCase
    private lateinit var getCountryCodeByNameUseCase: GetCountryCodeByNameUseCase
    private lateinit var getMoviesByCountryUseCase: GetMoviesOnlyByCountryNameUseCase
    private lateinit var incrementCategoryInteractionUseCase: IncrementCategoryInteractionUseCase
    private lateinit var sortingMediaByCategoriesInteractionUseCase: SortingMediaByCategoriesInteractionUseCase

    private val testDispatcher = StandardTestDispatcher()

    private val settingsUseCase: SettingsUseCase = mockk()
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI = mockk(relaxed = true)

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getMediaByActorNameUseCase = mockk(relaxed = true)
        autoCompleteCountryUseCase = mockk(relaxed = true)
        getMoviesByCountryUseCase = mockk(relaxed = true)
        getCountryCodeByNameUseCase = mockk(relaxed = true)
        incrementCategoryInteractionUseCase = mockk(relaxed = true)
        sortingMediaByCategoriesInteractionUseCase = mockk(relaxed = true)
        coEvery { settingsUseCase.getRestriction() } returns "Strict"

        viewModel = WorldTourViewModel(
            autoCompleteCountryUseCase = autoCompleteCountryUseCase,
            getCountryCodeByNameUseCase = getCountryCodeByNameUseCase,
            getMoviesByCountryUseCase = getMoviesByCountryUseCase,
            incrementCategoryInteractionUseCase = incrementCategoryInteractionUseCase,
            sortingMediaByCategoriesInteractionUseCase = sortingMediaByCategoriesInteractionUseCase,
            savedStateHandle = mockk(relaxed = true),
            mediaDetailsFeatureAPI = mediaDetailsFeatureAPI,
            settingsUseCase = settingsUseCase,
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `onSearchQueryChange should update search query immediately`() {
        val query = "United States"

        viewModel.onSearchQueryChange(query)

        assertEquals(query, viewModel.screenState.value.uiState.searchQuery)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `onSearchQueryChange with empty query should not trigger autocomplete`() = runTest {
        val emptyQuery = ""

        viewModel.onSearchQueryChange(emptyQuery)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify(exactly = 0) { autoCompleteCountryUseCase(any()) }
        coVerify(exactly = 0) { getCountryCodeByNameUseCase(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
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

    @OptIn(ExperimentalCoroutinesApi::class)
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
        val mediaUiState = MediaUiState(
            id = 12,
            imageUri = "",
            title = "Test Movie",
            type = MediaTypeUi.Movie,
            categories = listOf(Category.Action, Category.Adventure),
            yearOfRelease = LocalDate(2023, 1, 1),
            rating = 4.5
        )
        viewModel.onMediaCardClick(mediaUiState)
        advanceUntilIdle()
        coVerify { mediaDetailsFeatureAPI.startMovieDetails(any()) }
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