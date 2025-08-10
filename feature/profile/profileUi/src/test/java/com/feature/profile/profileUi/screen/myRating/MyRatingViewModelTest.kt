package com.feature.profile.profileUi.screen.myRating

import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.profile.profileUi.navigation.ProfileNavigator
import com.feature.profile.profileUi.screen.watchHistory.MediaTypeUi
import com.feature.profile.profileUi.screen.watchHistory.MediaUiState
import com.paris_2.domain.media.entity.Category
import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.entity.MediaType
import com.paris_2.domain.media.useCase.FilterRatedMediaUseCase
import com.paris_2.domain.media.useCase.movie.DeleteMovieRatingUseCase
import com.paris_2.domain.media.useCase.tvShows.DeleteTvShowRatingUseCase
import com.paris_2.domain.user.usecase.GetAccountIdUseCase
import com.paris_2.domain.user.usecase.GetSessionIdUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class MyRatingViewModelTest {

    private val getRatedMediaUseCase: FilterRatedMediaUseCase = mockk()
    private val getSessionIdUseCase: GetSessionIdUseCase = mockk()
    private val deleteMovieRatingUseCase: DeleteMovieRatingUseCase = mockk()
    private val deleteTvShowRatingUseCase: DeleteTvShowRatingUseCase = mockk()
    private val getAccountIdUseCase: GetAccountIdUseCase = mockk()
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI = mockk(relaxed = true)
    private val profileNavigator: ProfileNavigator = mockk(relaxed = true)
    private lateinit var viewModel: MyRatingViewModel

    @BeforeEach
    fun setUp() {
        coEvery { getSessionIdUseCase() } returns "session123"
        coEvery { getAccountIdUseCase() } returns 1
        coEvery {
            getRatedMediaUseCase(
                accountId = any<Int>(),
                mediaType = any<MediaType>()
            )
        } returns fakeMediaList

        viewModel = MyRatingViewModel(
            getRatedMediaUseCase = getRatedMediaUseCase,
            getAccountIdUseCase = getAccountIdUseCase,
            deleteMovieRatingUseCase = deleteMovieRatingUseCase,
            deleteTvShowRatingUseCase = deleteTvShowRatingUseCase,
            mediaDetailsFeatureAPI = mediaDetailsFeatureAPI
        )
    }

    @Test
    fun `onTabSelected should trigger media load for selected type`() = runTest {
        coEvery {
            getRatedMediaUseCase(any(),MediaType.TvShow)
        } returns fakeMediaList

        viewModel.onTabSelected(MediaTypeUi.TVSHOW)

        val state = viewModel.screenState.value
        assertEquals(1, state.myRatingMedia.size)
    }

    @Test
    fun `onMediaCardClick starts movie details`() = runTest {
        val media = MediaUiState(
            id = 123,
            title = "Sample Movie",
            type = MediaTypeUi.MOVIE,
            imageUri = "",
            rating = 5.2,
            yearOfRelease = LocalDate(2022, 1, 1)
        )
        advanceUntilIdle()

        viewModel.onMediaCardClick(media)

        coVerify { mediaDetailsFeatureAPI.startMovieDetails(123) }
    }

    @Test
    fun `onMediaCardClick starts tv show details`() = runTest {
        val media = MediaUiState(
            id = 456,
            title = "Sample TV Show",
            type = MediaTypeUi.TVSHOW,
            imageUri = "",
            rating = 7.3,
            yearOfRelease = LocalDate(2023, 5, 1)
        )
        advanceUntilIdle()

        viewModel.onMediaCardClick(media)

        coVerify { mediaDetailsFeatureAPI.startTvShowDetails(456) }
    }

    @Test
    fun `onFavouriteIconClick should delete movie rating and reload data`() = runTest {
        val media = MediaUiState(
            id = 1,
            title = "Movie 1",
            type = MediaTypeUi.MOVIE,
            imageUri = "",
            rating = 8.5,
            yearOfRelease = LocalDate(2022, 1, 1)
        )

        coEvery { deleteMovieRatingUseCase(1) } returns Unit

        advanceUntilIdle()
        viewModel.onFavouriteIconClick(media)

        coVerify { getRatedMediaUseCase(any(), MediaType.Movie) }
    }

    @Test
    fun `onFavouriteIconClick should delete tv show rating and reload data`() = runTest {
        val media = MediaUiState(
            id = 2,
            title = "TV Show 1",
            type = MediaTypeUi.TVSHOW,
            imageUri = "",
            rating = 7.1,
            yearOfRelease = LocalDate(2021, 3, 15)
        )

        coEvery { deleteTvShowRatingUseCase(2) } returns Unit
        coEvery { getRatedMediaUseCase(any(), MediaType.TvShow) } returns fakeMediaList

        advanceUntilIdle()
        viewModel.onFavouriteIconClick(media)

        coVerify(exactly = 1) { deleteTvShowRatingUseCase(2) }
    }

    @Test
    fun `onBackClick should call navigateUp`() = runTest {
        val field = viewModel::class.java.superclass!!.getDeclaredField("navigator")
        field.isAccessible = true
        field.set(viewModel, profileNavigator)

        viewModel.onBackClick()

        coVerify { profileNavigator.navigateUp() }
    }

    @Test
    fun `onRetry should reload data for selected media type`() = runTest {
        viewModel.onRetry()

        coVerify { getRatedMediaUseCase(any(), MediaType.Movie) }
    }

    @Test
    fun `onTabSelected should not reload when selecting same tab`() = runTest {
        coEvery { getRatedMediaUseCase(any(), any()) } returns fakeMediaList

        advanceUntilIdle()
        viewModel.onTabSelected(MediaTypeUi.MOVIE)

        coVerify(exactly = 1) { getRatedMediaUseCase(any(), any()) }
    }

    @Test
    fun `onFavouriteIconClick should update errorMessage if delete fails`() = runTest {
        // Given
        val media = MediaUiState(
            id = 5,
            title = "Broken TV Show",
            type = MediaTypeUi.TVSHOW,
            imageUri = "",
            rating = 7.5,
            yearOfRelease = LocalDate(2019, 5, 20)
        )

        coEvery { deleteTvShowRatingUseCase(5) } throws RuntimeException("Delete failed")

        // When
        advanceUntilIdle()
        viewModel.onFavouriteIconClick(media)


        println("Final state: ${viewModel.screenState.value}")

        // Then
        assertEquals("Delete failed", viewModel.screenState.value.errorMessage)
    }

    @Test
    fun `viewModel should load movies by default on initialization`() = runTest {
        advanceUntilIdle()

        coVerify { getRatedMediaUseCase(1, MediaType.Movie) }
    }


    private val fakeMediaList = listOf(
        Media(
            id = 1,
            title = "Movie 1",
            type = MediaType.Movie,
            imageUri = "/abc.jpg",
            categories = listOf(Category.Action),
            yearOfRelease = LocalDate(2022, 1, 1),
            rating = 8.5
        )
    )
}