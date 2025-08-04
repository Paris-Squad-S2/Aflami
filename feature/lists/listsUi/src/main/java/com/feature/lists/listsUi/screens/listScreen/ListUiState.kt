package com.feature.lists.listsUi.screens.listScreen

import com.paris.domain.lists.entity.Lists

data class ListUiState(
    val id: Int  ,
    val title: String  ,
    val count: Int
)

data class ListScreenUIState(
    val lists: List<ListUiState> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)

fun List<Lists>.toUiState(): List<ListUiState> =
    map { it.toUiState() }

fun Lists.toUiState(): ListUiState =
    ListUiState(
        id = id,
        title = name,
        count = itemCount
    )