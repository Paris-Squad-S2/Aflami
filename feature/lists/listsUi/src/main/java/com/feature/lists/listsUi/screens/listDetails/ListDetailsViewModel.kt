package com.feature.lists.listsUi.screens.listDetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.feature.lists.listsUi.common.BaseViewModel
import com.feature.lists.listsUi.navigation.ListDestinations
import com.feature.lists.listsUi.pagging.PagingSourceFactory
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.paris.domain.lists.useCase.AddMovieToListUseCase
import com.paris.domain.lists.useCase.DeleteListUseCase
import com.paris.domain.lists.useCase.GetListDetailsUseCase
import com.paris.domain.lists.useCase.RemoveMovieFromListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ListDetailsViewModel @Inject constructor(
    private val deleteListUseCase: DeleteListUseCase,
    private val getListDetailsUseCase: GetListDetailsUseCase,
    private val removeMovieFromListUseCase: RemoveMovieFromListUseCase,
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI,
    private val addMovieToListUseCase: AddMovieToListUseCase,
    savedStateHandle: SavedStateHandle
): BaseViewModel<ListDetailsScreenState>(
    initialState = ListDetailsScreenState(),
), ListDetailsScreenInteractionListener {
    private val listId = savedStateHandle.toRoute<ListDestinations.ListDetails>().listId

    init {
        getListDetails(listId)
    }

    override fun onMediaCardClick(mediaUiState: MediaUiState) {
        tryToExecute(
            execute = {
                mediaDetailsFeatureAPI.startMovieDetails(movieId = mediaUiState.id)
            },
            onError = { errorMessage ->
                emitState(
                    screenState.value.copy(
                        errorMessage = errorMessage
                    )
                )
            }
        )
    }

    private fun getListDetails(listId: String) {
        tryToExecute(
            execute = {
                Pager(
                    config = PagingConfig(
                        pageSize = 20,
                        enablePlaceholders = false
                    ),
                    pagingSourceFactory = {
                        PagingSourceFactory.createForListDetails(
                            listId = listId,
                            getListDetailsUseCase = getListDetailsUseCase::invoke
                        )
                    }
                ).flow.map { pagingData ->
                    pagingData.map { media -> media.toUiState() }
                }
            },
            onSuccess = { pagingDataFlow ->
                emitState(
                    screenState.value.copy(
                        mediaItems = pagingDataFlow,
                        isLoading = false,
                        errorMessage = null
                    )
                )
            },
            onError = { errorMessage ->
                emitState(
                    screenState.value.copy(
                        errorMessage = errorMessage,
                        isLoading = false
                    )
                )
            }
        )
    }

    private fun deleteList(listId: String) {
        tryToExecute(
            execute = {
                deleteListUseCase.invoke(listId)
            },
            onSuccess = {
                Log.i("TAG", "deleteItemFromList: $it")
                navigateUp()
            },
            onError = { errorMessage ->
                emitState(
                    screenState.value.copy(
                        errorMessage = errorMessage,
                    )
                )
            }
        )
    }

    private fun removeMovieFromList(listId: String, movieId: Int) {
        tryToExecute(
            execute = {
                removeMovieFromListUseCase.invoke(listId, movieId)
            },
            onSuccess = {
                Log.i("TAG", "removeMovieFromList: $it")
                // Refresh the list details to update the UI
                getListDetails(listId)
            },
            onError = { errorMessage ->
                emitState(
                    screenState.value.copy(
                        errorMessage = errorMessage,
                    )
                )
            }
        )
    }

    override fun onRemoveClick(mediaUiState: MediaUiState) {
        removeMovieFromList(listId, mediaUiState.id)
    }

    override fun onDeleteListClick() {
        emitState(
            screenState.value.copy(
                showDeleteDialog = true
            )
        )
    }

    override fun onDeleteDialogDismiss() {
        emitState(
            screenState.value.copy(
                showDeleteDialog = false
            )
        )
    }

    override fun onDeleteDialogConfirm() {
        emitState(
            screenState.value.copy(
                showDeleteDialog = false
            )
        )
        deleteList(listId)
    }

    override fun onRetryListsDetails() {
        getListDetails(listId)
    }

    override fun onBackClick() {
        navigateUp()
    }

}