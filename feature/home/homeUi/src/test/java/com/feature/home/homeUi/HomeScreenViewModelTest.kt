package com.feature.home.homeUi

import com.paris_2.domain.media.entity.Category
import com.paris_2.domain.media.useCase.AddMediaToLocalUseCase
import com.paris_2.domain.media.useCase.FilterUpComingMediaByCategoriesUseCase
import com.paris_2.domain.media.useCase.GetMediaFromLocalUseCase
import com.paris_2.domain.media.useCase.GetMoviesCategoriesUseCase
import com.paris_2.domain.media.useCase.GetPopularMediaUseCase
import com.paris_2.domain.media.useCase.GetTopRatingMediaUseCase
import com.paris_2.domain.media.useCase.GetUpComingMediaUseCase
import com.feature.home.homeUi.navigation.HomeNavigator
import com.feature.home.homeUi.screen.home.CategoryUiState
import com.feature.home.homeUi.screen.home.HomeScreenViewModel
import com.feature.home.homeUi.screen.home.MediaTypeUi.MOVIE
import com.feature.home.homeUi.screen.home.MediaTypeUi.TVSHOW
import com.feature.home.homeUi.screen.home.MediaUiState
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.search.searchApi.SearchFeatureAPI
import com.google.common.truth.Truth.assertThat
import com.paris_2.aflami.designsystem.components.SliderMedia
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.paris_2.domain.media.entity.Media as DomainMedia
import com.paris_2.domain.media.entity.MediaType as DomainMediaType

@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenViewModelTest {
    private val getPopularMediaUseCase: GetPopularMediaUseCase = mockk()
    private val getTopRatingMediaUseCase: GetTopRatingMediaUseCase = mockk()
    private val getMoviesCategoriesUseCase: GetMoviesCategoriesUseCase = mockk()
    private val filterUpComingMediaByCategoriesUseCase: FilterUpComingMediaByCategoriesUseCase =
        mockk()
    private val getUpcomingMediaUseCase: GetUpComingMediaUseCase = mockk()
    private val addMediaToLocalDatabaseUseCase: AddMediaToLocalUseCase = mockk()
    private val getMediaFromLocalUseCase: GetMediaFromLocalUseCase = mockk()
    private val searchFeatureAPI: SearchFeatureAPI = mockk(relaxed = true)
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI = mockk(relaxed = true)
    private val navigator: HomeNavigator = mockk(relaxed = true)

    private lateinit var viewModel: HomeScreenViewModel
    private val testDispatcher = StandardTestDispatcher()


    private val categoryMap = mapOf("Action" to 28, "Comedy" to 35, "Drama" to 18)
    private val fakeCategories = listOf(
        CategoryUiState(28, "Action"),
        CategoryUiState(35, "Comedy"),
        CategoryUiState(18, "Drama")
    )
    private val fakePopularList = listOf(
        MediaUiState(1, "img/1", "Popular 1", MOVIE, listOf("Action"), LocalDate(2022, 1, 1), 7.5),
        MediaUiState(2, "img/2", "Popular 2", TVSHOW, listOf("Comedy"), LocalDate(2023, 1, 1), 8.5)
    )
    private val fakeTopRatedList = listOf(
        MediaUiState(10, "img/a", "Top 1", MOVIE, listOf("Drama"), LocalDate(2022, 2, 2), 9.0)
    )
    private val fakeUpcomingList = listOf(
        MediaUiState(
            20,
            "img/u1",
            "Upcoming 1",
            MOVIE,
            listOf("Action"),
            LocalDate(2024, 4, 1),
            6.5
        ),
        MediaUiState(
            21,
            "img/u2",
            "Upcoming 2",
            TVSHOW,
            listOf("Comedy"),
            LocalDate(2024, 5, 1),
            7.9
        )
    )
    private val fakeContinueWatchingList = listOf(
        MediaUiState(
            200,
            "img/c1",
            "Continue 1",
            TVSHOW,
            listOf("Drama"),
            LocalDate(2023, 9, 9),
            5.0
        )
    )

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        coEvery { getPopularMediaUseCase() } returns fakePopularList.map { it.toMedia() }
        coEvery { getTopRatingMediaUseCase() } returns fakeTopRatedList.map { it.toMedia() }
        coEvery { getMoviesCategoriesUseCase() } returns fakeCategories.map { it.toCategory() }
        coEvery { getUpcomingMediaUseCase() } returns fakeUpcomingList.map { it.toMedia() }
        coEvery { getMediaFromLocalUseCase() } returns fakeContinueWatchingList.map { it.toMedia() }
        coEvery { addMediaToLocalDatabaseUseCase.invoke(any()) } returns Unit
        coEvery { filterUpComingMediaByCategoriesUseCase.invoke(any()) } returns fakeUpcomingList.map { it.toMedia() }
        viewModel = HomeScreenViewModel(
            getPopularMediaUseCase,
            getTopRatingMediaUseCase,
            getMoviesCategoriesUseCase,
            filterUpComingMediaByCategoriesUseCase,
            getUpcomingMediaUseCase,
            addMediaToLocalDatabaseUseCase,
            getMediaFromLocalUseCase,
            searchFeatureAPI,
            mediaDetailsFeatureAPI,
            navigator
        )
    }

    private fun MediaUiState.toMedia() = DomainMedia(
        id = id,
        title = title,
        rating = rating,
        imageUri = imageUri,
        yearOfRelease = yearOfRelease,
        categoryIds = categories.mapNotNull { categoryMap[it] },
        type = when (type) {
            MOVIE -> DomainMediaType.MOVIE
            TVSHOW -> DomainMediaType.TVSHOW
        }
    )

    private fun CategoryUiState.toCategory() = Category(id, name)

    @Test
    fun `init loads popular, topRated, continueWatching, categories, and all categories`() =
        runTest {
            assertThat(viewModel.screenState.value.homeUIState.popularMediaList.map { it.title }).isEqualTo(
                fakePopularList.map { it.title })
            assertThat(viewModel.screenState.value.homeUIState.topRatedMediaList.map { it.title }).isEqualTo(
                fakeTopRatedList.map { it.title })
            assertThat(viewModel.screenState.value.homeUIState.categories.keys.map { it.name }).isEqualTo(
                fakeCategories.map { it.name })
            assertThat(viewModel.screenState.value.homeUIState.continueWatchingMediaList.map { it.title }).isEqualTo(
                fakeContinueWatchingList.map { it.title })
            assertThat(viewModel.screenState.value.homeUIState.upComingMediaList.map { it.title }).isEqualTo(
                fakeUpcomingList.map { it.title })
        }

    @Test
    fun `loadCategories handles error`() = runTest {
        coEvery { getMoviesCategoriesUseCase() } throws RuntimeException("Failed categories")
        viewModel = HomeScreenViewModel(
            getPopularMediaUseCase,
            getTopRatingMediaUseCase,
            getMoviesCategoriesUseCase,
            filterUpComingMediaByCategoriesUseCase,
            getUpcomingMediaUseCase,
            addMediaToLocalDatabaseUseCase,
            getMediaFromLocalUseCase,
            searchFeatureAPI,
            mediaDetailsFeatureAPI,
            navigator
        )
        runCurrent()
        assertThat(viewModel.screenState.value.homeUIState.categories).isEqualTo(emptyMap<CategoryUiState, Boolean>())
    }

    @Test
    fun `loadPopularMedia handles error`() = runTest {
        coEvery { getPopularMediaUseCase() } throws RuntimeException("Popular error")
        viewModel = HomeScreenViewModel(
            getPopularMediaUseCase,
            getTopRatingMediaUseCase,
            getMoviesCategoriesUseCase,
            filterUpComingMediaByCategoriesUseCase,
            getUpcomingMediaUseCase,
            addMediaToLocalDatabaseUseCase,
            getMediaFromLocalUseCase,
            searchFeatureAPI,
            mediaDetailsFeatureAPI,
            navigator
        )
        runCurrent()
        assertThat(viewModel.screenState.value.homeUIState.popularMediaList).isEqualTo(emptyList<SliderMedia>())
    }

    @Test
    fun `loadTopRatingMedia handles error`() = runTest {
        coEvery { getTopRatingMediaUseCase() } throws RuntimeException("TopRating error")
        viewModel = HomeScreenViewModel(
            getPopularMediaUseCase,
            getTopRatingMediaUseCase,
            getMoviesCategoriesUseCase,
            filterUpComingMediaByCategoriesUseCase,
            getUpcomingMediaUseCase,
            addMediaToLocalDatabaseUseCase,
            getMediaFromLocalUseCase,
            searchFeatureAPI,
            mediaDetailsFeatureAPI,
            navigator
        )
        runCurrent()
        assertThat(viewModel.screenState.value.homeUIState.topRatedMediaList).isEqualTo(emptyList<MediaUiState>())
    }

    @Test
    fun `loadContinueWatchingMedia does not affect errorMessage`() = runTest {
        val oldError = "Something else before"
        viewModel.emitState(viewModel.screenState.value.copy(errorMessage = oldError))
        viewModel.emitState(
            viewModel.screenState.value.copy(
                homeUIState = viewModel.screenState.value.homeUIState.copy(
                    continueWatchingMediaList = emptyList()
                )
            )
        )
        coEvery { getMediaFromLocalUseCase() } returns fakeContinueWatchingList.map { it.toMedia() }
        viewModel.apply {
            this.javaClass.getDeclaredMethod("loadContinueWatchingMedia")
                .apply { isAccessible = true }.invoke(this)
        }
        runCurrent()
        assertThat(viewModel.screenState.value.homeUIState.continueWatchingMediaList.map { it.title }).isEqualTo(
            fakeContinueWatchingList.map { it.title })
    }

    @Test
    fun `onAllCategoriesSelect fetches upcoming media and resets all categories selection`() =
        runTest {
            val catMap = fakeCategories.associateWith { true }.toMutableMap()
            viewModel.emitState(
                viewModel.screenState.value.copy(
                    homeUIState = viewModel.screenState.value.homeUIState.copy(
                        categories = catMap
                    )
                )
            )
            coEvery { getUpcomingMediaUseCase() } returns fakeUpcomingList.map { it.toMedia() }
            viewModel.onAllCategoriesSelect()
            runCurrent()
            assertThat(viewModel.screenState.value.homeUIState.categories.values.all { !it }).isTrue()
            assertThat(viewModel.screenState.value.homeUIState.upComingMediaList).isNotEmpty()
        }

    @Test
    fun `onAllCategoriesSelect handles error`() = runTest {
        coEvery { getUpcomingMediaUseCase() } throws RuntimeException("Upcoming error")
        viewModel.onAllCategoriesSelect()
        runCurrent()
        assertThat(viewModel.screenState.value.errorMessage).isEqualTo("Upcoming error")
    }

    @Test
    fun `onSearchIconClick triggers navigation`() = runTest {
        viewModel.onSearchIconClick()
        runCurrent()
        coVerify { searchFeatureAPI() }
    }

    @Test
    fun `onMediaCardClick adds to local, updates continue watching, and navigates to movie or tv details`() =
        runTest {
            val movie = fakePopularList.first().copy(type = MOVIE)
            val tv = fakePopularList.last().copy(type = TVSHOW)
            coEvery { addMediaToLocalDatabaseUseCase.invoke(movie.toMedia()) } returns Unit
            coEvery { addMediaToLocalDatabaseUseCase.invoke(tv.toMedia()) } returns Unit
            viewModel.onMediaCardClick(movie)
            runCurrent()
            coVerify { addMediaToLocalDatabaseUseCase.invoke(movie.toMedia()) }
            coVerify { mediaDetailsFeatureAPI.startMovieDetails(movie.id) }
            viewModel.onMediaCardClick(tv)
            runCurrent()
            coVerify { addMediaToLocalDatabaseUseCase.invoke(tv.toMedia()) }
            coVerify { mediaDetailsFeatureAPI.startTvShowDetails(tv.id) }
        }

    @Test
    fun `onMediaCardClick handles error`() = runTest {
        val movie = fakePopularList.first()
        coEvery { addMediaToLocalDatabaseUseCase.invoke(movie.toMedia()) } throws RuntimeException("add error")
        viewModel.onMediaCardClick(movie)
        runCurrent()
        assertThat(viewModel.screenState.value.errorMessage).isEqualTo("add error")
    }

    @Test
    fun `getRandomMoodPickerMovie picks a random movie from upComingMediaList`() = runTest {
        viewModel.emitState(
            viewModel.screenState.value.copy(
                homeUIState = viewModel.screenState.value.homeUIState.copy(
                    upComingMediaList = fakeUpcomingList
                )
            )
        )
        viewModel.getRandomMoodPickerMovie()
        runCurrent()
        val picked = viewModel.screenState.value.homeUIState.moodPickerMovie
        assertThat(fakeUpcomingList).contains(picked)
    }

    @Test
    fun `moodPickerSelected updates upcoming list, moodPickerMovie, and dialog flag`() = runTest {
        val mood = listOf("Drama")
        val filteredMovies = fakeTopRatedList.filter { it.categories.contains("Drama") }
        coEvery { getTopRatingMediaUseCase.invoke() } returns filteredMovies.map { it.toMedia() }
        viewModel.emitState(
            viewModel.screenState.value.copy(
                homeUIState = viewModel.screenState.value.homeUIState.copy(
                    moodPickerMovie = filteredMovies.random()
                )
            )
        )
        viewModel.moodPickerSelected(mood)
        runCurrent()
        val updated = viewModel.screenState.value.homeUIState
        assertThat(filteredMovies).contains(updated.moodPickerMovie)
        assertThat(updated.showMoodPickerDialog).isTrue()
    }

    @Test
    fun `moodPickerSelected handles error`() = runTest {
        coEvery { getTopRatingMediaUseCase.invoke() } throws RuntimeException(
            "Mood error"
        )
        viewModel.moodPickerSelected(listOf("Action"))
        runCurrent()
        assertThat(viewModel.screenState.value.errorMessage).isEqualTo("Mood error")
    }

    @Test
    fun `onCategorySelect toggles selection and filters upcoming list`() = runTest {
        val cat = fakeCategories.first()
        val filteredMovies = fakeUpcomingList.filter { it.categories.contains(cat.name) }
        coEvery { filterUpComingMediaByCategoriesUseCase.invoke(listOf(cat.id)) } returns filteredMovies.map { it.toMedia() }
        viewModel.emitState(
            viewModel.screenState.value.copy(
                homeUIState = viewModel.screenState.value.homeUIState.copy(
                    categories = fakeCategories.associateWith { false }.toMutableMap()
                )
            )
        )
        viewModel.onCategorySelect(cat)
        runCurrent()
        assertThat(viewModel.screenState.value.homeUIState.categories[cat]).isTrue()
        assertThat(viewModel.screenState.value.homeUIState.upComingMediaList).isEqualTo(
            filteredMovies
        )
    }

    @Test
    fun `onCategorySelect handles error`() = runTest {
        val cat = fakeCategories.first()
        coEvery { filterUpComingMediaByCategoriesUseCase.invoke(listOf(cat.id)) } throws RuntimeException(
            "Category error"
        )
        viewModel.emitState(
            viewModel.screenState.value.copy(
                homeUIState = viewModel.screenState.value.homeUIState.copy(
                    categories = fakeCategories.associateWith { false }.toMutableMap()
                )
            )
        )
        viewModel.onCategorySelect(cat)
        runCurrent()
        assertThat(viewModel.screenState.value.errorMessage).isEqualTo("Category error")
    }

    @Test
    fun `onDismissMoodPicker hides dialog and resets moodPickerMovie`() = runTest {
        viewModel.emitState(
            viewModel.screenState.value.copy(
                homeUIState = viewModel.screenState.value.homeUIState.copy(
                    showMoodPickerDialog = true,
                    moodPickerMovie = fakeUpcomingList.first()
                )
            )
        )
        viewModel.onDismissMoodPicker()
        runCurrent()
        val updated = viewModel.screenState.value.homeUIState
        assertThat(updated.showMoodPickerDialog).isFalse()
        assertThat(updated.moodPickerMovie?.id).isEqualTo(0)
        assertThat(updated.moodPickerMovie?.title).isEmpty()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `onRetry loads all media and categories again`() = runTest {


        coEvery { getPopularMediaUseCase() } returns listOf()


        viewModel = HomeScreenViewModel(
            getPopularMediaUseCase,
            getTopRatingMediaUseCase,
            getMoviesCategoriesUseCase,
            filterUpComingMediaByCategoriesUseCase,
            getUpcomingMediaUseCase,
            addMediaToLocalDatabaseUseCase = mockk(relaxed = true),
            getMediaFromLocalUseCase,
            searchFeatureAPI = mockk(relaxed = true),
            mediaDetailsFeatureAPI = mockk(relaxed = true),
            navigator
        )

        viewModel.onRetry()
        runCurrent()

        coVerify { getPopularMediaUseCase() }
    }


}
