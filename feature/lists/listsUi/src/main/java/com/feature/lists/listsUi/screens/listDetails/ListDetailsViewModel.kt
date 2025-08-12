package com.feature.lists.listsUi.screens.listDetails

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.feature.lists.listsUi.common.BaseViewModel
import com.feature.lists.listsUi.navigation.ListDestinations
import com.feature.lists.listsUi.pagging.PagingSource
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.paris.domain.lists.useCase.DeleteListUseCase
import com.paris.domain.lists.useCase.GetListDetailsUseCase
import com.paris.domain.lists.useCase.RemoveMovieFromListUseCase
import com.paris_2.domain.user.usecase.SettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListDetailsViewModel @Inject constructor(
    private val deleteListUseCase: DeleteListUseCase,
    private val getListDetailsUseCase: GetListDetailsUseCase,
    private val removeMovieFromListUseCase: RemoveMovieFromListUseCase,
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI,
    private val settingsUseCase: SettingsUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ListDetailsScreenState>(
    initialState = ListDetailsScreenState(),
), ListDetailsScreenInteractionListener {
    private val listId = savedStateHandle.toRoute<ListDestinations.ListDetails>().listId

    init {
        getRestriction()
        getListDetails(listId)
    }

    private fun getRestriction() {
        tryToExecute(
            execute = { settingsUseCase.getRestriction() },
            onSuccess = ::onGetRestrictionSuccess,
            onError = ::onGetRestrictionError
        )
    }

    private fun onGetRestrictionSuccess(restriction: String) {
        emitState(
            screenState.value.copy(
                contentRestriction = ContentRestriction.valueOf(restriction),
            )
        )
        when (screenState.value.contentRestriction) {
            ContentRestriction.Strict -> emitState(
                screenState.value.copy(
                    nsfwThreshold = 0.8f,
                    genderThreshold = 0.6f
                )
            )

            ContentRestriction.Moderate -> emitState(
                screenState.value.copy(
                    nsfwThreshold = 0.4f,
                    genderThreshold = 0.6f
                )
            )

            ContentRestriction.Off -> emitState(
                screenState.value.copy(
                    nsfwThreshold = 0f,
                    genderThreshold = 0f
                )
            )
        }

    }

    private fun onGetRestrictionError(error: String) {
        emitState(
            screenState.value.copy(
                errorMessage = error,
            )
        )
    }

    override fun onMediaCardClick(mediaUiState: MediaUiState) {
        tryToExecute(execute = {
            mediaDetailsFeatureAPI.startMovieDetails(movieId = mediaUiState.id)
        }, onError = { errorMessage ->
            emitState(
                screenState.value.copy(
                    errorMessage = errorMessage
                )
            )
        })
    }

    private fun getListDetails(listId: String) {
        emitState(
            screenState.value.copy(
                isLoading = true, errorMessage = null
            )
        )

        tryToExecute(
            execute = {
                Pager(
                    config = PagingConfig(
                        pageSize = 20, enablePlaceholders = false
                    ),
                    pagingSourceFactory = {
                        PagingSource { page: Int ->
                            val listDetails = getListDetailsUseCase.invoke(page, listId)
                            listDetails.items.map { it.toUiState() }
                        }
                    }
                ).flow
            },
            onSuccess = { pagingFlow ->
                tryToExecute(
                    execute = {
                        val listDetails = getListDetailsUseCase.invoke(1, listId)
                        listDetails.name
                    },
                    onSuccess = { name ->
                        emitState(
                            screenState.value.copy(
                                listTitle = name,
                                mediaItems = pagingFlow,
                                isLoading = false,
                                errorMessage = null
                            )
                        )
                    },
                    onError = { errorMessage ->
                        emitState(
                            screenState.value.copy(
                                errorMessage = errorMessage, isLoading = false
                            )
                        )
                    }
                )
            },
            onError = { errorMessage ->
                emitState(
                    screenState.value.copy(
                        errorMessage = errorMessage, isLoading = false
                    )
                )
            }
        )
    }

    private fun deleteList(listId: String) {
        tryToExecute(execute = {
            val result = deleteListUseCase.invoke(listId)
            result
        }, onSuccess = {

            navigateUp()
        }, onError = { errorMessage ->
            emitState(
                screenState.value.copy(
                    errorMessage = errorMessage,
                )
            )
        })
    }

    private fun removeMovieFromList(listId: String, movieId: Int) {
        tryToExecute(execute = {
            val result = removeMovieFromListUseCase.invoke(listId, movieId)
            result
        }, onSuccess = {
            getListDetails(listId)
        }, onError = { errorMessage ->
            emitState(
                screenState.value.copy(
                    errorMessage = errorMessage,
                )
            )
        })
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