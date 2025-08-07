package com.feature.lists.listsUi.screens.listDetails

interface ListDetailsScreenInteractionListener {
    fun onMediaCardClick(mediaUiState: MediaUiState)
    fun onRemoveClick(mediaUiState: MediaUiState)
    fun onRetryListsDetails()
    fun onBackClick()
    fun onDeleteListClick()
    fun onDeleteDialogDismiss()
    fun onDeleteDialogConfirm()
}