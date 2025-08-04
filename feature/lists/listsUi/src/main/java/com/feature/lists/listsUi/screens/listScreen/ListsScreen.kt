@file:OptIn(ExperimentalMaterial3Api::class)

package com.feature.lists.listsUi.screens.listScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.feature.lists.listsUi.screens.listScreen.components.ListCard
import com.paris_2.aflami.designsystem.components.AppTopBar
import com.paris_2.aflami.designsystem.components.iconItemWithDefaults
import com.paris_2.aflami.designsystem.utils.BasePreview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ListsScreen(viewModel: ListsViewModel = hiltViewModel()) {
    val uiState = viewModel.screenState.collectAsStateWithLifecycle().value

    ListsScreenContent(
        state = uiState,
        {}
    )
}

@Composable
private fun ListsScreenContent(
    state: ListScreenUIState,
    onListClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier.statusBarsPadding()
    ) {
        AppTopBar(
            title = "Lists",
            trailingIcons = listOf(
                iconItemWithDefaults(Icons.Default.Add)
            )
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 160.dp),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
        ) {
            items(state.lists) { list ->
                ListCard(
                    title = list.title,
                    count = list.count,
                    onCardClick = { onListClick(list.id) }
                )
            }
        }

    }
}

@PreviewLightDark
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
}
