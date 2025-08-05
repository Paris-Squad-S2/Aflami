package com.feature.lists.listsUi.screens.listScreen

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.feature.lists.listsUi.common.BaseViewModel
import com.feature.lists.listsUi.navigation.ListDestinations
import com.feature.lists.listsUi.pagging.PagingSource
import com.paris.domain.lists.useCase.CreateListUseCase
import com.paris.domain.lists.useCase.GetListUseCase
import com.paris_2.aflami.designsystem.components.ButtonState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ListsViewModel @Inject constructor(
    private val getListsUseCase: GetListUseCase,
    private val createListUseCase: CreateListUseCase,
) : BaseViewModel<ListScreenUIState>(
    initialState = ListScreenUIState()
), ListsInteractionListener {
    init {
        getLists()
    }

    private fun getLists() {
        tryToExecute(
            execute = {
                Pager(
                    config = PagingConfig(
                        pageSize = 20,
                        enablePlaceholders = false
                    ),
                    pagingSourceFactory = {
                        PagingSource { page: Int ->
                            getListsUseCase.invoke(page)
                        }
                    }
                ).flow.map { pagingData ->
                    pagingData.map { list -> list.toUiState() }
                }
            },
            onSuccess = { pagingDataFlow ->
                emitState(
                    screenState.value.copy(
                        lists = pagingDataFlow,
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

    private fun createList(name: String) {
        tryToExecute(
            execute = {
                createListUseCase.invoke(name)
            },
            onSuccess = {
                emitState(
                    screenState.value.copy(
                        showCreateListDialog = false,
                        createListName = "",
                        createListButtonState = ButtonState.Normal
                    )
                )
                getLists()
            },
            onError = { errorMessage ->
                emitState(
                    screenState.value.copy(
                        errorMessage = errorMessage,
                        createListButtonState = ButtonState.Normal
                    )
                )
            }
        )
    }


    override fun onListClicked(listId: String) {
        navigate(ListDestinations.ListDetails(listId))
    }

    override fun onAddClicked() {
        emitState(
            screenState.value.copy(
                showCreateListDialog = true
            )
        )
    }

    override fun onCreateListDismiss() {
        emitState(
            screenState.value.copy(
                showCreateListDialog = false,
                createListName = "",
                createListButtonState = ButtonState.Normal
            )
        )
    }

    override fun onCreateListConfirm() {
        val listName = screenState.value.createListName.trim()
        if (listName.isNotEmpty()) {
            emitState(
                screenState.value.copy(
                    createListButtonState = ButtonState.Loading
                )
            )
            createList(listName)
        }
    }

    override fun onCreateListNameChange(name: String) {
        emitState(
            screenState.value.copy(
                createListName = name,
                createListButtonState = if (name.isBlank()) ButtonState.Disabled else ButtonState.Normal
            )
        )
    }

    override fun onRetryLists() {
        getLists()
    }
}