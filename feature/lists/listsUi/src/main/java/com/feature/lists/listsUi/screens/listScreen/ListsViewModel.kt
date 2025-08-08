package com.feature.lists.listsUi.screens.listScreen

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.feature.authentication.authenticationApi.AuthenticationFeatureAPI
import com.feature.lists.listsUi.common.BaseViewModel
import com.feature.lists.listsUi.navigation.ListDestinations
import com.feature.lists.listsUi.pagging.PagingSource
import com.paris.domain.lists.useCase.CreateListUseCase
import com.paris.domain.lists.useCase.GetListUseCase
import com.paris_2.aflami.designsystem.components.ButtonState
import com.paris_2.domain.user.usecase.IsLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ListsViewModel @Inject constructor(
    private val getListsUseCase: GetListUseCase,
    private val createListUseCase: CreateListUseCase,
    private val isLoggedInUseCase: IsLoggedInUseCase,
    private val authenticationFeatureAPI: AuthenticationFeatureAPI,
    ) : BaseViewModel<ListScreenUIState>(
    initialState = ListScreenUIState()
), ListsInteractionListener {
    init {
        getLists()
        getIsUserLoggedIn()
    }

    private fun getIsUserLoggedIn() {
        tryToExecute(
            execute = {
                isLoggedInUseCase.invoke()
            },
            onSuccess = {
                emitState(
                    screenState.value.copy(
                       isLoggedIn = it
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

    private fun getLists() {
        tryToExecute(
            execute = {
                Pager(
                    config = PagingConfig(
                        pageSize = 20,
                        enablePlaceholders = false
                    ),
                    pagingSourceFactory = {
                        PagingSource { page ->
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
            onSuccess = { result ->
                emitState(
                    screenState.value.copy(
                        showCreateListDialog = false,
                        createListName = "",
                        createListButtonState = ButtonState.Normal,
                        showSnackBar = true,
                        snackBarSuccess = result.success
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

    override fun onHideSnackBar() {
        emitState(
            screenState.value.copy(showSnackBar = false)
        )    }

    override fun onShowSnackBar() {
        emitState(
            screenState.value.copy(showSnackBar = true)
        )
    }

    override fun onLogoutApplyClicked() {
        authenticationFeatureAPI()
    }

}