package com.feature.search.searchUi.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

val LocalNavController = compositionLocalOf<NavHostController> { error("No Nav Controller Found") }

@Composable
fun SearchNavGraph() {
    val navController = rememberNavController()
    CompositionLocalProvider(
        LocalNavController provides navController,
    ){
        SearchNavHost(navController)
    }
}