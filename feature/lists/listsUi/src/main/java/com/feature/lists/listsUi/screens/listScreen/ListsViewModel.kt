package com.feature.lists.listsUi.screens.listScreen

import com.feature.lists.listsUi.common.BaseViewModel
import com.feature.lists.listsUi.navigation.ListNavigator
import com.paris.domain.lists.useCase.GetListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListsViewModel @Inject constructor(
    private val getListsUseCase: GetListUseCase, navigator: ListNavigator
) : BaseViewModel<ListScreenUIState>(
    initialState = ListScreenUIState(), navigator
), ListsInteractionListener {
    init {
        getLists()
    }

    private fun getLists() {
        tryToExecute(
            execute = {
                getListsUseCase.invoke(1)
            },
            onSuccess = {
                emitState(
                    screenState.value.copy(
                        lists = it.toUiState()
                    )
                )
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

    override fun onListClicked(listId: String) {
        TODO("Not yet implemented")

    }

    override fun onAddClicked() {
        TODO("Not yet implemented")
    }

}
