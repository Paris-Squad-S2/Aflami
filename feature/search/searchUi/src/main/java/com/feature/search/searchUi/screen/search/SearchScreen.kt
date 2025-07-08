package com.feature.search.searchUi.screen.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun SearchScreen(viewModel: SearchViewModel = koinViewModel()) {
    SearchScreenContent(onNavigateToFilterScreen = viewModel::onNavigateToFilterScreen,
        onNavigateToWorldTourScreen = viewModel::onNavigateToWorldTourScreen,
        onNavigateToFindByActorScreen = viewModel::onNavigateToFindByActorScreen
    )
}

@Composable
fun SearchScreenContent(
    onNavigateToFilterScreen: (String) -> Unit,
    onNavigateToWorldTourScreen: (String) -> Unit,
    onNavigateToFindByActorScreen: (String) -> Unit
) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("SearchScreen")
        Button(
            onClick = { onNavigateToFilterScreen("Filter") },
        ) {
            Text("Filter")
        }
        Button(
            onClick = { onNavigateToWorldTourScreen("worldTour") },
        ) {
            Text("World Tour")
        }
        Button(
            onClick = { onNavigateToFindByActorScreen("findByActor") },
        ) {
            Text("Find By Actor")
        }
    }
}