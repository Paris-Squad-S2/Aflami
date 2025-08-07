package com.paris_2.aflami.bottomNavBar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.feature.categories.categoriesApi.CategoriesFeatureAPI
import com.feature.guessGame.guessGameApi.GuessGameFeatureAPI
import com.feature.home.homeApi.HomeFeatureAPI
import com.feature.lists.listsApi.ListsFeatureAPI
import com.feature.onboarding.onboardingApi.OnBoardingFeatureAPI
import com.feature.profile.profileApi.ProfileFeatureAPI

@Composable
internal fun AppNavGraph(
    navigator: AppNavigator,
    navController: NavHostController,
    onboardingFeature: OnBoardingFeatureAPI,
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

        navigation<AppDestinations.AppGraph1>(startDestination = AppDestinations.OnBoardingFeature) {
            composable<AppDestinations.OnBoardingFeature> {
                onboardingFeature()()
            }

            composable<AppDestinations.HomeFeature> {
                homeFeature()()
            }

            composable<AppDestinations.ListsFeature> {
                listsFeature()()
            }
            composable<AppDestinations.CategoriesFeature> {
                categoriesFeature()()
            }

            composable<AppDestinations.LetsPlayFeature> {
                letsPlayFeature()()
            }
            composable<AppDestinations.ProfileFeature> {
                profileFeature()()
            }
        }
    }
}
