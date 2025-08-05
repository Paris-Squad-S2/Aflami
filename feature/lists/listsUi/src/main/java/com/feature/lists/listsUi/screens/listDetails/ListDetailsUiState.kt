package com.feature.lists.listsUi.screens.listDetails

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate

data class ListDetailsScreenState(
    val searchUiState: ListDetailsUiState = ListDetailsUiState(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val showDeleteDialog: Boolean = false
)

data class ListDetailsUiState(
    val listName: String = "",
    val moviesResult: Flow<PagingData<MediaUiState>> = flowOf(PagingData.empty()),
    )

data class MediaUiState(
    val id: Int,
    val imageUri: String,
    val title: String,
    val yearOfRelease: LocalDate,
    val rating: Double?,
)
