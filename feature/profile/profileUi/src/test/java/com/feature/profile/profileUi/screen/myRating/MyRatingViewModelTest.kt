package com.feature.profile.profileUi.screen.myRating

import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.profile.profileUi.navigation.ProfileNavigator
import com.feature.profile.profileUi.screen.watchHistory.MediaTypeUi
import com.feature.profile.profileUi.screen.watchHistory.MediaUiState
import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.entity.MediaType
import com.paris_2.domain.media.useCase.FilterRatedMediaUseCase
import com.paris_2.domain.user.usecase.GetAccountIdUseCase
import com.paris_2.domain.user.usecase.GetSessionIdUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.Before
import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class MyRatingViewModelTest {

    private val getRatedMediaUseCase: FilterRatedMediaUseCase = mockk()
    private val getSessionIdUseCase: GetSessionIdUseCase = mockk()
    private val getAccountIdUseCase: GetAccountIdUseCase = mockk()
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI = mockk(relaxed = true)
    private val profileNavigator: ProfileNavigator = mockk(relaxed = true)
    private lateinit var viewModel: MyRatingViewModel


    private val fakeMediaList = listOf(
        Media(
            id = 1,
            title = "Movie 1",
            type = MediaType.MOVIE,
            imageUri = "/abc.jpg",
            categoryIds = listOf(28),
            yearOfRelease = LocalDate(2022, 1, 1),
            rating = 8.5
        )
    )

    @Before
    fun setUp() {
        coEvery { getSessionIdUseCase() } returns "session123"
        coEvery { getAccountIdUseCase() } returns 1
        coEvery {
            getRatedMediaUseCase(
                accountId = any<Int>(),
                sessionId = any<String>(),
                mediaType = any<MediaType>()
            )
        } returns fakeMediaList

        viewModel = MyRatingViewModel(
            getRatedMediaUseCase = getRatedMediaUseCase,
            getSessionIdUseCase = getSessionIdUseCase,
            getAccountIdUseCase = getAccountIdUseCase,
            mediaDetailsFeatureAPI = mediaDetailsFeatureAPI,
            navigator = profileNavigator
        )
    }

    @Test
    fun `init loads my rated movies successfully`() = runTest {
        val state = viewModel.screenState.value
        assertFalse(state.isLoading)
        assertEquals(1, state.myRatingMedia.size)
    }

    @Test
    fun `onTabSelected should trigger media load for selected type`() = runTest {
        coEvery {
            getRatedMediaUseCase(any(), any(),MediaType.TVSHOW)
        } returns fakeMediaList

        viewModel.onTabSelected(MediaTypeUi.TVSHOW)

        val state = viewModel.screenState.value
        assertEquals(1, state.myRatingMedia.size)
    }

    @Test
    fun `onTabClick should update selectedTab and fetch media`() = runTest {
        val newTab = MediaTypeUi.TVSHOW

        viewModel.onTabSelected(newTab)

        val state = viewModel.screenState.value
        assertFalse(state.isLoading)
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

        viewModel.onMediaCardClick(media)

        coVerify { mediaDetailsFeatureAPI.startMovieDetails(123) }
    }

    @Test
    fun `onBackClick should navigate up`() = runTest {
        viewModel.onBackClick()

        coVerify { profileNavigator.navigateUp() }
    }

}