package com.feature.lists.listsUi.screens.listDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.filter
import com.feature.lists.listsUi.common.BaseViewModel
import com.feature.lists.listsUi.navigation.ListDestinations
import com.feature.lists.listsUi.pagging.PagingSource
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.paris.domain.lists.useCase.DeleteListUseCase
import com.paris.domain.lists.useCase.GetListDetailsUseCase
import com.paris.domain.lists.useCase.RemoveMovieFromListUseCase
import com.paris.domain.user.usecase.ManageSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class ListDetailsViewModel @Inject constructor(
    private val deleteListUseCase: DeleteListUseCase,
    private val getListDetailsUseCase: GetListDetailsUseCase,
    private val removeMovieFromListUseCase: RemoveMovieFromListUseCase,
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI,
    private val manageSettingsUseCase: ManageSettingsUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ListDetailsScreenState>(ListDetailsScreenState()),
    ListDetailsScreenInteractionListener {
    private val listId = savedStateHandle.toRoute<ListDestinations.ListDetails>().listId
    private val removedIds = MutableStateFlow<Set<Int>>(emptySet())

    init {
        getRestriction()
        getListDetails(listId)
    }

    private fun getRestriction() {
        tryToExecute(
            execute = { manageSettingsUseCase.getRestriction() },
            onSuccess = ::onGetRestrictionSuccess,
            onError = ::onGetRestrictionError
        )
    }
    private fun onGetRestrictionSuccess(restriction: String) {
        updateState(
            screenState.value.copy(
                contentRestriction = ContentRestriction.valueOf(restriction),
            )
        )
        when (screenState.value.contentRestriction) {
            ContentRestriction.Strict -> updateState(
                screenState.value.copy(
                    nsfwThreshold = 0.8f,
                    genderThreshold = 0.6f
                )
            )

            ContentRestriction.Moderate -> updateState(
                screenState.value.copy(
                    nsfwThreshold = 0.4f,
                    genderThreshold = 0.6f
                )
            )

            ContentRestriction.Off -> updateState(
                screenState.value.copy(
                    nsfwThreshold = 0f,
                    genderThreshold = 0f
                )
            )
        }

    }

    private fun onGetRestrictionError(error: String) {
        updateState(
            screenState.value.copy(
                errorMessage = error,
            )
        )
    }

    override fun onMediaCardClick(mediaUiState: MediaUiState) {
        tryToExecute(execute = {
            mediaDetailsFeatureAPI.startMovieDetails(movieId = mediaUiState.id)
        }, onError = { errorMessage ->
            updateState(
                screenState.value.copy(
                    errorMessage = errorMessage
                )
            )
        })
    }
    private fun getListDetails(listId: String) {
        updateState(
            screenState.value.copy(
                isLoading = true,
                errorMessage = null
            )
        )

        tryToExecute(
            execute = {
                Pager(
                    config = PagingConfig(
                        pageSize = 20,
                        enablePlaceholders = false
                    ),
                    pagingSourceFactory = {
                        PagingSource { page: Int ->
                            val listDetails = getListDetailsUseCase.invoke(page, listId)
                            listDetails.items.map { it.toUiState() }
                        }
                    }
                ).flow
                    .combine(removedIds) { paging, removed ->
                        paging.filter { media ->
                            val keep = media.id !in removed
                            keep
                        }
                    }
                    .cachedIn(viewModelScope)
            },
            onSuccess = { filteredFlow ->
                tryToExecuteFlow(
                    flow = filteredFlow,
                    onEachItem = { pagingData ->
                        updateState(
                            screenState.value.copy(
                                mediaItems = kotlinx.coroutines.flow.flowOf(pagingData),
                                isLoading = false,
                                errorMessage = null
                            )
                        )
                    },
                    onError = { errorMessage ->
                        updateState(
                            screenState.value.copy(
                                errorMessage = errorMessage,
                                isLoading = false
                            )
                        )
                    }
                )

                tryToExecute(
                    execute = { getListDetailsUseCase.invoke(1, listId).name },
                    onSuccess = { name ->
                        updateState(
                            screenState.value.copy(listTitle = name)
                        )
                    },
                    onError = { errorMessage ->
                        updateState(
                            screenState.value.copy(
                                errorMessage = errorMessage,
                                isLoading = false
                            )
                        )
                    }
                )
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        errorMessage = errorMessage,
                        isLoading = false
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
            updateState(
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
            updateState(
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
        updateState(
            screenState.value.copy(
                showDeleteDialog = true
            )
        )
    }

    override fun onDeleteDialogDismiss() {
        updateState(
            screenState.value.copy(
                showDeleteDialog = false
            )
        )
    }

    override fun onDeleteDialogConfirm() {
        updateState(
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