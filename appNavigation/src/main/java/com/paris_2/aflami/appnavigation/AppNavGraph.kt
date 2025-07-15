package com.paris_2.aflami.appnavigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.mediaDetails.mediaDetailsApi.fromJsonToMediaDetailsDestination
import com.feature.search.searchApi.SearchFeatureAPI
import com.feature.search.searchApi.fromJsonToSearchDestination
import org.koin.compose.koinInject

@Composable
fun AppNavGraph(navigator: AppNavigator = koinInject()) {
    val navController = rememberNavController()
    val searchFeature: SearchFeatureAPI = koinInject()
    val mediaDetailsFeature: MediaDetailsFeatureAPI = koinInject()

    ObserveAsEvents(navigator.navigationEvent) { event ->
        when (event) {
            is AppNavigationEvent.Navigate -> {
                navController.navigate(
                    route = event.destination, navOptions = event.navOptions
                )
            }

            AppNavigationEvent.NavigateUp -> navController.navigateUp()
        }
    }

    NavHost(
        navController = navController,
        startDestination = navigator.startGraph
    ) {
        navigation<AppDestinations.AppGraph1>(startDestination = AppDestinations.SearchFeature()) {
            composable<AppDestinations.SearchFeature> {
                val searchDestination =
                    it.toRoute<AppDestinations.SearchFeature>().searchDestination
                searchFeature(searchDestination?.fromJsonToSearchDestination())()
            }
            composable<AppDestinations.MediaDetailsFeature> {
                val mediaDetailsDestination =
                    it.toRoute<AppDestinations.MediaDetailsFeature>().mediaDetailsDestination
                mediaDetailsFeature(mediaDetailsDestination?.fromJsonToMediaDetailsDestination())()
            }
        }
    }
}
