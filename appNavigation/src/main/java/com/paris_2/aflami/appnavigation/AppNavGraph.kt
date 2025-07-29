package com.paris_2.aflami.appnavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.feature.categories.categoriesApi.CategoriesFeatureAPI
import com.feature.categories.categoriesApi.fromJsonToCategoriesDestination
import com.feature.guessGame.guessGameApi.GuessGameFeatureAPI
import com.feature.guessGame.guessGameApi.fromJsonToGuessGameDestination
import com.feature.home.homeApi.HomeFeatureAPI
import com.feature.home.homeApi.fromJsonToHomeDestination
import com.feature.lists.listsApi.ListsFeatureAPI
import com.feature.lists.listsApi.fromJsonToListsDestination
import com.feature.profile.profileApi.ProfileFeatureAPI
import com.feature.profile.profileApi.fromJsonToProfileDestination

@Composable
internal fun AppNavGraph(
    navigator: AppNavigator,
    navController: NavHostController,
    homeFeature: HomeFeatureAPI,
    listsFeature: ListsFeatureAPI,
    categoriesFeature: CategoriesFeatureAPI,
    letsPlayFeature: GuessGameFeatureAPI,
    profileFeature: ProfileFeatureAPI
) {
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
                homeFeature(homeDestination?.fromJsonToHomeDestination())()
            }

            composable<AppDestinations.ListsFeature> {
                val listsDestination =
                    it.toRoute<AppDestinations.ListsFeature>().listsDestination
                listsFeature(listsDestination?.fromJsonToListsDestination())()
            }
            composable<AppDestinations.CategoriesFeature> {
                val categoriesDestination =
                    it.toRoute<AppDestinations.CategoriesFeature>().categoriesDestination
                categoriesFeature(categoriesDestination?.fromJsonToCategoriesDestination())()
            }

            composable<AppDestinations.LetsPlayFeature> {
                val letsPlayDestination =
                    it.toRoute<AppDestinations.LetsPlayFeature>().letsPlayDestination
                letsPlayFeature(letsPlayDestination?.fromJsonToGuessGameDestination())()
            }
            composable<AppDestinations.ProfileFeature> {
                val profileDestination =
                    it.toRoute<AppDestinations.ProfileFeature>().profileDestination
                profileFeature(profileDestination?.fromJsonToProfileDestination())()
            }
        }
    }
}
