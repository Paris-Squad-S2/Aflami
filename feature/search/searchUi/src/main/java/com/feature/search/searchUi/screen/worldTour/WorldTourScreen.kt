package com.feature.search.searchUi.screen.worldTour

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
fun WorldTourScreen(viewModel: WorldTourViewModel = koinViewModel()) {
    WorldTourScreenContent(onNavigate = viewModel::onNavigateToSearchScreen)
}

@Composable
fun WorldTourScreenContent(onNavigate: () -> Unit = {}) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("WorldTourScreen")
        Button(
            onClick = { onNavigate() },
        ) {
            Text("SearchScreen")
        }
    }
}