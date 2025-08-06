package com.feature.profile.profileUi.screen.watchHistory

import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.profile.profileUi.navigation.ProfileNavigator
import com.google.common.truth.Truth.assertThat
import com.paris_2.domain.media.useCase.FilterWatchHistoryUseCase
import com.paris_2.domain.media.entity.Media as DomainMedia
import com.paris_2.domain.media.entity.MediaType as DomainMediaType
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlinx.coroutines.test.runCurrent


class WatchHistoryViewModelTest {
    private val filterWatchHistoryUseCase: FilterWatchHistoryUseCase = mockk()
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI = mockk(relaxed = true)
    private val navigator: ProfileNavigator = mockk(relaxed = true)
    private lateinit var viewModel: WatchHistoryViewModel
    private val testDispatcher = StandardTestDispatcher()

    private val fakeMediaList = listOf(
        MediaUiState(
            id = 1,
            imageUri = "img/1",
            title = "Test Movie",
            type = MediaTypeUi.MOVIE,
            yearOfRelease = LocalDate(2022, 1, 1),
            rating = 7.0
        ),
        MediaUiState(
            id = 2,
            imageUri = "img/2",
            title = "Test TV",
            type = MediaTypeUi.TVSHOW,
            yearOfRelease = LocalDate(2023, 2, 2),
            rating = 8.0
        )
    )

    private fun MediaUiState.toDomain() = DomainMedia(
        id = id,
        title = title,
        imageUri = imageUri,
        yearOfRelease = yearOfRelease,
        rating = rating,
        categoryIds = listOf(1),
        type = when (type) {
            MediaTypeUi.MOVIE -> DomainMediaType.MOVIE
            MediaTypeUi.TVSHOW -> DomainMediaType.TVSHOW
        }
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `onTabSelected should update watch history in state`() = runTest {
        val tvShows = listOf(fakeMediaList[1].toDomain())
        coEvery { filterWatchHistoryUseCase(DomainMediaType.TVSHOW) } returns tvShows

        viewModel = WatchHistoryViewModel(filterWatchHistoryUseCase, mediaDetailsFeatureAPI, navigator)
        runCurrent()

        viewModel.onTabSelected(MediaTypeUi.TVSHOW)
        runCurrent()

        assertThat(viewModel.screenState.value.watchHistoryMedia.map { it.title })
            .containsExactly("Test TV")
    }


}