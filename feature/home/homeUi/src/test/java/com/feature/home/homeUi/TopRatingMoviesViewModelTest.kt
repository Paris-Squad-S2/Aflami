package com.feature.home.homeUi

import com.feature.home.homeUi.common.ContentRestriction
import com.feature.home.homeUi.screen.home.MediaTypeUi
import com.feature.home.homeUi.screen.home.MediaUiState
import com.feature.home.homeUi.screen.topRatingMovies.TopRatingMoviesViewModel
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.google.common.truth.Truth.assertThat
import com.paris.domain.media.entity.Category
import com.paris.domain.media.useCase.media.GetTopRatingMediaUseCase
import com.paris.domain.user.usecase.SettingsUseCase
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
import com.paris.domain.media.entity.Media as DomainMedia
import com.paris.domain.media.entity.MediaType as DomainMediaType

private const val CATEGORY_DRAMA = 1
private const val CATEGORY_COMEDY = 2

@OptIn(ExperimentalCoroutinesApi::class)
class TopRatingMoviesViewModelTest {
    private val getTopRatingMediaUseCase: GetTopRatingMediaUseCase = mockk()
    private val settingsUseCase: SettingsUseCase = mockk()
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI = mockk(relaxed = true)
    private lateinit var viewModel: TopRatingMoviesViewModel
    private val testDispatcher = StandardTestDispatcher()

    private val fakeTopRatedList = listOf(
        MediaUiState(
            10,
            "img/a",
            "Top 1",
            MediaTypeUi.MOVIE,
            listOf(CATEGORY_DRAMA),
            LocalDate(2022, 2, 2),
            9.0
        ),
        MediaUiState(
            11,
            "img/b",
            "Top 2",
            MediaTypeUi.TV_SHOW,
            listOf(CATEGORY_COMEDY),
            LocalDate(2021, 7, 8),
            8.4
        )
    )

    private fun MediaUiState.toMedia() = DomainMedia(
        id = id,
        title = title,
        rating = rating,
        imageUri = imageUri,
        yearOfRelease = yearOfRelease,
        categories = listOf(Category.Action),
        type = when (type) {
            MediaTypeUi.MOVIE -> DomainMediaType.Movie
            MediaTypeUi.TV_SHOW -> DomainMediaType.TvShow
        }
    )

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `init loads top rating movies and updates state`() = runTest {
        coEvery { getTopRatingMediaUseCase() } returns fakeTopRatedList.map { it.toMedia() }
        coEvery { settingsUseCase.getRestriction() } returns ContentRestriction.Off.name
        viewModel = TopRatingMoviesViewModel(getTopRatingMediaUseCase, mediaDetailsFeatureAPI, settingsUseCase)
        runCurrent()
        val state = viewModel.screenState.value
        assertThat(state.topRatingMovies.map { it.title }).isEqualTo(fakeTopRatedList.map { it.title })
        assertThat(state.isLoading).isFalse()
        assertThat(state.errorMessage).isNull()
    }

    @Test
    fun `init error updates errorMessage and sets isLoading false`() = runTest {
        coEvery { getTopRatingMediaUseCase() } throws RuntimeException("Failed to load top")
        viewModel = TopRatingMoviesViewModel(getTopRatingMediaUseCase, mediaDetailsFeatureAPI,settingsUseCase)
        runCurrent()
        val state = viewModel.screenState.value
        assertThat(state.errorMessage).isEqualTo("Failed to load top")
        assertThat(state.isLoading).isFalse()
        assertThat(state.topRatingMovies.isEmpty()).isTrue()
    }

    @Test
    fun `onMediaCardClick for movie triggers correct navigation`() = runTest {
        coEvery { getTopRatingMediaUseCase() } returns fakeTopRatedList.map { it.toMedia() }
        viewModel = TopRatingMoviesViewModel(getTopRatingMediaUseCase, mediaDetailsFeatureAPI,settingsUseCase)
        runCurrent()
        val movie = fakeTopRatedList[0]
        viewModel.onMediaCardClick(movie)
        runCurrent()
        coVerify { mediaDetailsFeatureAPI.startMovieDetails(movie.id) }
    }

    @Test
    fun `onMediaCardClick for tvshow triggers correct navigation`() = runTest {
        coEvery { getTopRatingMediaUseCase() } returns fakeTopRatedList.map { it.toMedia() }
        viewModel = TopRatingMoviesViewModel(getTopRatingMediaUseCase, mediaDetailsFeatureAPI,settingsUseCase)
        runCurrent()
        val tv = fakeTopRatedList[1]
        viewModel.onMediaCardClick(tv)
        runCurrent()
        coVerify { mediaDetailsFeatureAPI.startTvShowDetails(tv.id) }
    }

    @Test
    fun `onMediaCardClick error updates errorMessage`() = runTest {
        coEvery { getTopRatingMediaUseCase() } returns fakeTopRatedList.map { it.toMedia() }
        viewModel = TopRatingMoviesViewModel(getTopRatingMediaUseCase, mediaDetailsFeatureAPI,settingsUseCase)
        runCurrent()
        val movie = fakeTopRatedList[0]
        coEvery { mediaDetailsFeatureAPI.startMovieDetails(any()) } throws RuntimeException("nav error")
        viewModel.onMediaCardClick(movie)
        runCurrent()
        val state = viewModel.screenState.value
        assertThat(state.errorMessage).isEqualTo("nav error")
    }

    @Test
    fun `onRetry calls loadContinueWatchingMedia again`() = runTest {
        var callCount = 0
        coEvery { getTopRatingMediaUseCase() } coAnswers {
            callCount += 1
            fakeTopRatedList.map { it.toMedia() }
        }
        viewModel = TopRatingMoviesViewModel(getTopRatingMediaUseCase, mediaDetailsFeatureAPI, settingsUseCase)
        runCurrent()
        viewModel.onRetry()
        runCurrent()
        assertThat(callCount).isEqualTo(2)
    }
}
