package com.feature.search.searchUi.screen.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.feature.search.searchUi.navigation.Destinations
import com.feature.search.searchUi.navigation.LocalNavController
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun SearchScreen(viewModel: SearchViewModel = koinViewModel()){
    val navController = LocalNavController.current

    /**
     * use this method to navigate to different screens
     */
    SearchScreenContent(onNavigate = { navController.navigate(Destinations.FilterScreen(it)) })

}

@Composable
fun SearchScreenContent(onNavigate: (String) -> Unit = {}){
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("SearchScreen")
        Button(
            onClick = { onNavigate("") },
        ) {
            Text("next")
        }
    }
}