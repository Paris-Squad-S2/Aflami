package com.feature.onboarding.onboardingUi.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.feature.home.homeUi.screen.home.HomeScreen
import com.feature.onboarding.onboardingUi.ui.OnBoardingViewModel
import com.feature.onboarding.onboardingUi.ui.OnboardingScreen

@Composable
fun OnBoardingNavGraph(
    viewModel: OnBoardingViewModel = hiltViewModel()
) {
    val navigator = viewModel.navigator
    val navController = rememberNavController()

    ObserveAsEvents(navigator.onboardingNavigationEvent) { event ->
        when (event) {
            is OnBoardingNavigationEvent.Navigate -> navController.navigate(
                route = event.destination,
                navOptions = event.navOptions
            )
            OnBoardingNavigationEvent.NavigateUp -> navController.navigateUp()
        }
    }

    NavHost(
        navController = navController,
        startDestination = navigator.startGraph
    ) {
        buildOnBoardingNavGraph()
    }
}

fun NavGraphBuilder.buildOnBoardingNavGraph() {
    navigation<OnBoardingDestinations.OnBoardingGraph1>(
        startDestination = OnBoardingDestinations.OnBoardingScreen
    ) {
        composable<OnBoardingDestinations.OnBoardingScreen> {
            OnboardingScreen()
        }

        composable<OnBoardingDestinations.HomeScreen> {
           HomeScreen()
        }
    }
}


