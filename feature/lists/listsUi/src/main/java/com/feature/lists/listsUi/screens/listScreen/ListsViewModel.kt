package com.feature.lists.listsUi.screens.listScreen

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.feature.authentication.authenticationApi.AuthenticationFeatureAPI
import com.feature.lists.listsUi.common.BaseViewModel
import com.feature.lists.listsUi.navigation.ListDestinations
import com.feature.lists.listsUi.pagging.PagingSource
import com.paris.domain.lists.useCase.CreateListUseCase
import com.paris.domain.lists.useCase.GetListUseCase
import com.paris.aflami.designsystem.components.ButtonState
import com.paris.domain.user.usecase.IsLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListsViewModel @Inject constructor(
    private val getListsUseCase: GetListUseCase,
    private val createListUseCase: CreateListUseCase,
    private val isLoggedInUseCase: IsLoggedInUseCase,
    private val authenticationFeatureAPI: AuthenticationFeatureAPI,
) : BaseViewModel<ListScreenUIState>(ListScreenUIState()), ListsInteractionListener {
    init {
        getLists()
        getIsUserLoggedIn()
    }
    private var currentPagingSource: PagingSource<ListUiState>? = null

    private fun getIsUserLoggedIn() {
        tryToExecute(
            execute = {
                isLoggedInUseCase.invoke()
            },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        isLoggedIn = it
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
                            getListsUseCase.invoke(page).map { it.toUiState() }
                        }.also { currentPagingSource = it }
                    }
                ).flow
            },
            onSuccess = { pagingDataFlow ->
                updateState(
                    screenState.value.copy(
                        lists = pagingDataFlow,
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
    }

    private fun createList(name: String) {
        tryToExecute(
            execute = {
                createListUseCase.invoke(name)
            },
            onSuccess = { result ->
                updateState(
                    screenState.value.copy(
                        showCreateListDialog = false,
                        createListName = "",
                        createListButtonState = ButtonState.Normal,
                        showSnackBar = true,
                        snackBarSuccess = result
                    )
                )
                hideSnackBar()
                currentPagingSource?.invalidate()
            },
            onError = { errorMessage ->
                updateState(
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
        updateState(
            screenState.value.copy(
                showCreateListDialog = true
            )
        )
    }

    override fun onCreateListDismiss() {
        updateState(
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
            updateState(
                screenState.value.copy(
                    createListButtonState = ButtonState.Loading
                )
            )
            createList(listName)
        }
    }

    override fun onCreateListNameChange(name: String) {
        updateState(
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
        updateState(
            screenState.value.copy(showSnackBar = false)
        )
    }

    override fun onShowSnackBar() {
        updateState(
            screenState.value.copy(showSnackBar = true)
        )
        hideSnackBar()
    }

    override fun onLogoutApplyClicked() {
        authenticationFeatureAPI()
    }

    private fun hideSnackBar() {
        viewModelScope.launch {
            if (screenState.value.showSnackBar) {
                delay(3000)
                updateState(screenState.value.copy(showSnackBar = false))
            }
        }
    }

}