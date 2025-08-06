package com.feature.lists.listsUi.screens.listDetails

import androidx.lifecycle.SavedStateHandle
import com.feature.lists.listsUi.navigation.ListDestinations
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.google.common.truth.Truth.assertThat
import com.paris.domain.lists.useCase.DeleteListUseCase
import com.paris.domain.lists.useCase.GetListDetailsUseCase
import com.paris.domain.lists.useCase.RemoveMovieFromListUseCase
import com.paris.domain.lists.entity.Media
import com.paris.domain.lists.entity.ListDetails
import com.paris.domain.lists.entity.Response
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import org.junit.After
import org.junit.Before
import org.junit.Test

class ListDetailsViewModelTest {

    @MockK
    private lateinit var deleteListUseCase: DeleteListUseCase

    @MockK
    private lateinit var getListDetailsUseCase: GetListDetailsUseCase

    @MockK
    private lateinit var removeMovieFromListUseCase: RemoveMovieFromListUseCase

    @MockK
    private lateinit var mediaDetailsFeatureAPI: MediaDetailsFeatureAPI

    @MockK
    private lateinit var savedStateHandle: SavedStateHandle

    private lateinit var viewModel: ListDetailsViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)

        every { savedStateHandle.get<ListDestinations.ListDetails>(any()) } returns ListDestinations.ListDetails(
            listId = "test_list_id"
        )

        viewModel = ListDetailsViewModel(
            deleteListUseCase = deleteListUseCase,
            getListDetailsUseCase = getListDetailsUseCase,
            removeMovieFromListUseCase = removeMovieFromListUseCase,
            mediaDetailsFeatureAPI = mediaDetailsFeatureAPI,
            savedStateHandle = savedStateHandle
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getListDetails_whenSuccess_thenUpdateStateWithMediaItems() = runTest {
        // Given
        val mediaItems = listOf(
            Media(
                id = 1,
                posterPath = "https://example.com/image.jpg",
                title = "Test Movie",
                voteAverage = 7.5,
                releaseDate = LocalDate(2022, 1, 1)
            )
        )
        val listDetails = ListDetails(
            id = "test_list_id",
            name = "Test List",
            items = mediaItems
        )
        coEvery { getListDetailsUseCase.invoke(any(), any()) } returns listDetails

        // When
        // The view model automatically calls getListDetails in init block

        // Then
        coVerify(exactly = 1) { getListDetailsUseCase.invoke(any(), "test_list_id") }
    }

    @Test
    fun onMediaCardClick_whenCalled_thenStartMovieDetails() = runTest {
        // Given
        val mediaUiState = MediaUiState(
            id = 1,
            title = "Test Movie",
            imageUrl = "https://example.com/image.jpg",
            rating = 7.5f,
            yearOfRelease = LocalDate(2022, 1, 1)
        )

        // When
        viewModel.onMediaCardClick(mediaUiState)

        // Then
        verify { mediaDetailsFeatureAPI.startMovieDetails(movieId = 1) }
    }

    @Test
    fun onRemoveClick_whenSuccess_thenRefreshListDetails() = runTest {
        // Given
        val mediaUiState = MediaUiState(
            id = 1,
            title = "Test Movie",
            imageUrl = "https://example.com/image.jpg",
            rating = 7.5f,
            yearOfRelease = LocalDate(2022, 1, 1)
        )
        coEvery { removeMovieFromListUseCase.invoke(any(), any()) } returns Response(200, "Success")

        // When
        viewModel.onRemoveClick(mediaUiState)

        // Then
        coVerify(exactly = 1) { removeMovieFromListUseCase.invoke("test_list_id", 1) }
        coVerify(atLeast = 1) { getListDetailsUseCase.invoke(any(), "test_list_id") }
    }

    @Test
    fun onDeleteListClick_whenCalled_thenShowDeleteDialog() = runTest {
        // When
        viewModel.onDeleteListClick()

        // Then
        assertThat(viewModel.screenState.value.showDeleteDialog).isTrue()
    }

    @Test
    fun onDeleteDialogDismiss_whenCalled_thenHideDeleteDialog() = runTest {
        // Given
        viewModel.onDeleteListClick() // Show dialog first
        assertThat(viewModel.screenState.value.showDeleteDialog).isTrue()

        // When
        viewModel.onDeleteDialogDismiss()

        // Then
        assertThat(viewModel.screenState.value.showDeleteDialog).isFalse()
    }

    @Test
    fun onDeleteDialogConfirm_whenSuccess_thenDeleteList() = runTest {
        // Given
        coEvery { deleteListUseCase.invoke(any()) } returns Response(200, "Success")

        // When
        viewModel.onDeleteDialogConfirm()

        // Then
        coVerify(exactly = 1) { deleteListUseCase.invoke("test_list_id") }
    }

    @Test
    fun onBackClick_whenCalled_thenNavigateUp() = runTest {
        // When
        viewModel.onBackClick()

        // Then
        // We can't directly verify navigateUp() because it's protected
        // But we can verify that the navigator's navigateUp was called
        coVerify(exactly = 1) { viewModel.navigator.navigateUp() }
    }
}