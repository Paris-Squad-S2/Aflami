package com.feature.lists.listsUi.screens.listScreen

import androidx.annotation.StringRes
import androidx.paging.PagingData
import com.paris.domain.lists.entity.Lists
import com.paris.domain.lists.entity.Response
import com.paris_2.aflami.designsystem.components.ButtonState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class ListUiState(
    val id: Int,
    val title: String,
    val count: Int
)

data class ListScreenUIState(
    val lists: Flow<PagingData<ListUiState>> = flowOf(PagingData.empty()),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val showCreateListDialog: Boolean = false,
    val createListName: String = "",
    val createListButtonState: ButtonState = ButtonState.Disabled,
    val showSnackBar: Boolean = false,
    val snackBarSuccess: Boolean = false
)

fun List<Lists>.toUiState(): List<ListUiState> =
    map { it.toUiState() }


fun Lists.toUiState(): ListUiState =
    ListUiState(
        id = id,
        title = name,
        count = itemCount
    )