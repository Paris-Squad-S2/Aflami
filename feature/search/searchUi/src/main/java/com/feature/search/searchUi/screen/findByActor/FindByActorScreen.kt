package com.feature.search.searchUi.screen.findByActor

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
fun FindByActorScreen(viewModel: FindByActorViewModel = koinViewModel()) {

    /**
     * use this method to navigate to different screens
     */
    FindByActorScreenContent(onNavigate = viewModel::onNavigateToSearchScreen)
}

@Composable
fun FindByActorScreenContent(onNavigate: () -> Unit = {}) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("FindByActorScreen")
        Button(
            onClick = { onNavigate() },
        ) {
            Text("SearchScreen")
        }
    }
}