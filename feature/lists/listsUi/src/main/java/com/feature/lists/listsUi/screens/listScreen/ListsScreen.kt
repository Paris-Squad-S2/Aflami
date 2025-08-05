@file:OptIn(ExperimentalMaterial3Api::class)

package com.feature.lists.listsUi.screens.listScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.feature.lists.listsUi.R
import com.feature.lists.listsUi.screens.listScreen.components.CreateListDialog
import com.feature.lists.listsUi.screens.listScreen.components.ListCard
import com.paris_2.aflami.designsystem.components.AppTopBar
import com.paris_2.aflami.designsystem.components.NetworkError
import com.paris_2.aflami.designsystem.components.PageLoadingPlaceHolder
import com.paris_2.aflami.designsystem.components.PlaceholderView
import com.paris_2.aflami.designsystem.components.iconItemWithDefaults

@Composable
fun ListsScreen(viewModel: ListsViewModel = hiltViewModel()) {
    val uiState = viewModel.screenState.collectAsStateWithLifecycle().value

    ListsScreenContent(
        state = uiState,
        viewModel
    )
}

@Composable
private fun ListsScreenContent(
    state: ListScreenUIState,
    action: ListsInteractionListener
) {
    Column(
        modifier = Modifier.statusBarsPadding()
    ) {
        AppTopBar(
            title = "Lists",
            trailingIcons = listOf(
                iconItemWithDefaults(icon = Icons.Default.Add, onClick = { action.onAddClicked() })
            )
        )

        val lazyPagingItems = state.lists.collectAsLazyPagingItems()

        when {
            state.errorMessage != null || lazyPagingItems.loadState.hasError -> {
                NetworkError(
                    modifier = Modifier.fillMaxSize(),
                    onRetry = { action.onRetryLists() }
                )
            }

            state.isLoading || lazyPagingItems.loadState.refresh == LoadState.Loading -> {
                PageLoadingPlaceHolder(
                    modifier = Modifier.fillMaxSize()
                )
            }

            lazyPagingItems.itemCount == 0 -> {
                PlaceholderView(
                    modifier = Modifier.fillMaxSize(),
                    image = painterResource(R.drawable.img_empty_brain),
                    title = stringResource(R.string.no_items_yet),
                    subTitle = stringResource(R.string.create_your_first_list),
                    spacer = 24.dp,
                    imageSize = 188.dp
                )
            }

            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 160.dp),
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                ) {
                    items(
                        count = lazyPagingItems.itemCount,
                    ) { index ->
                        val listItem = lazyPagingItems[index]
                        listItem?.let { list ->
                            ListCard(
                                title = list.title,
                                count = list.count,
                                onCardClick = { action.onListClicked(list.id.toString()) }
                            )
                        }
                    }
                }
            }
        }
    }

    CreateListDialog(
        onDismiss = action::onCreateListDismiss,
        onAddClicked = action::onCreateListConfirm,
        onListNameValueChange = action::onCreateListNameChange,
        buttonState = state.createListButtonState,
        listName = state.createListName,
        showDialog = state.showCreateListDialog
    )
}

/*@PreviewLightDark
@Composable
fun ListsScreenContentPreview() {
    BasePreview {
        ListsScreenContent(
            state = ListScreenUIState(
                lists = listOf(
                    ListUiState(id = 1, title = "Favorites", count = 12),
                    ListUiState(id = 2, title = "Watch Later", count = 5),
                    ListUiState(id = 3, title = "Comedies", count = 8),
                    ListUiState(id = 4, title = "Dramas", count = 3)
                ),
                isLoading = false,
                errorMessage = null
            ),
            onListClick = {}
        )
    }
}*/
