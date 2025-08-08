package com.feature.lists.listsUi.screens.listScreen

interface ListsInteractionListener {
    fun onListClicked(listId: String)
    fun onAddClicked()
    fun onCreateListDismiss()
    fun onCreateListConfirm()
    fun onCreateListNameChange(name: String)
    fun onRetryLists()
    fun onHideSnackBar()
    fun onShowSnackBar()
}