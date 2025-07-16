package com.paris_2.aflami

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.mediaDetails.mediaDetailsApi.fromJsonToMediaDetailsDestination
import com.feature.search.searchApi.SearchFeatureAPI
import com.feature.search.searchApi.fromJsonToSearchDestination
import com.paris_2.aflami.appnavigation.AppDestinations
import com.paris_2.aflami.appnavigation.AppNavigationEvent
import com.paris_2.aflami.appnavigation.AppNavigator
import org.koin.compose.koinInject

@Composable
fun AppNavGraph(navigator: AppNavigator = koinInject(), navController: NavHostController ) {
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
        navigation<AppDestinations.AppGraph1>(startDestination = AppDestinations.HomeFeature()) {
            composable<AppDestinations.HomeFeature> {
                val homeDestination =
                    it.toRoute<AppDestinations.HomeFeature>().homeDestination
                searchFeature(homeDestination?.fromJsonToSearchDestination())()
            }

            composable<AppDestinations.ListsFeature> {
                val listsDestination =
                    it.toRoute<AppDestinations.ListsFeature>().listsDestination
                searchFeature(listsDestination?.fromJsonToSearchDestination())()
            }
            composable<AppDestinations.CategoriesFeature> {
                val categoriesDestination =
                    it.toRoute<AppDestinations.CategoriesFeature>().categoriesDestination
                searchFeature(categoriesDestination?.fromJsonToSearchDestination())()
            }

            composable<AppDestinations.LetsPlayFeature> {
                val letsPlayDestination =
                    it.toRoute<AppDestinations.LetsPlayFeature>().letsPlayDestination
                searchFeature(letsPlayDestination?.fromJsonToSearchDestination())()
            }
            composable<AppDestinations.ProfileFeature> {
                val profileDestination =
                    it.toRoute<AppDestinations.ProfileFeature>().profileDestination
                searchFeature(profileDestination?.fromJsonToSearchDestination())()
            }

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
