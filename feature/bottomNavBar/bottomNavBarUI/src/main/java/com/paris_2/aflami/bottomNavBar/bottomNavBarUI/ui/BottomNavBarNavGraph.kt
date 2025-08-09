package com.paris_2.aflami.bottomNavBar.bottomNavBarUI.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.feature.categories.categoriesApi.CategoriesFeatureAPI
import com.feature.guessGame.guessGameApi.GuessGameFeatureAPI
import com.feature.home.homeApi.HomeFeatureAPI
import com.feature.lists.listsApi.ListsFeatureAPI
import com.feature.profile.profileApi.ProfileFeatureAPI
import com.paris_2.aflami.bottomNavBar.bottomNavBarUI.navigation.Destinations
import com.paris_2.aflami.bottomNavBar.bottomNavBarUI.navigation.Event
import com.paris_2.aflami.bottomNavBar.bottomNavBarUI.navigation.Navigator
import com.paris_2.aflami.bottomNavBar.bottomNavBarUI.navigation.ObserveAsEvents

@Composable
internal fun BottomNavBarNavGraph(
    navigator: Navigator,
    navController: NavHostController,
    homeFeature: HomeFeatureAPI,
    listsFeature: ListsFeatureAPI,
    categoriesFeature: CategoriesFeatureAPI,
    letsPlayFeature: GuessGameFeatureAPI,
    profileFeature: ProfileFeatureAPI
) {
    ObserveAsEvents(navigator.navigationEvent) { event ->
        when (event) {
            is Event.Navigate -> {
                navController.navigate(
                    route = event.destination, navOptions = event.navOptions
                )
            }

            Event.NavigateUp -> navController.navigateUp()
        }
    }

    NavHost(
        navController = navController,
        startDestination = navigator.startGraph
    ) {
        navigation<Destinations.MainGraph>(startDestination = Destinations.HomeFeature) {
            composable<Destinations.HomeFeature> {
                homeFeature()()
            }

            composable<Destinations.ListsFeature> {
                listsFeature()()
            }
            composable<Destinations.CategoriesFeature> {
                categoriesFeature()()
            }

            composable<Destinations.LetsPlayFeature> {
                letsPlayFeature()()
            }
            composable<Destinations.ProfileFeature> {
                profileFeature()()
            }
        }
    }
}
