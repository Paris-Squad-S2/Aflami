package com.paris_2.aflami

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.paris_2.aflami.appnavigation.AppDestinations
import com.paris_2.aflami.appnavigation.AppNavigator
import com.paris_2.aflami.designsystem.components.AflamiScafold
import org.koin.compose.koinInject

@Composable
fun AppScaffold(appNavigator: AppNavigator = koinInject()) {
    var currentDestination by remember { mutableIntStateOf(0) }

    LaunchedEffect(currentDestination) {
        when (currentDestination) {
            0 -> appNavigator.navigate(
                AppDestinations.HomeFeature(),
                navOptions = NavOptions.Builder()
                    .setPopUpTo(AppDestinations.AppGraph1, inclusive = true)
                    .build()
            )

            1 -> appNavigator.navigate(
                AppDestinations.ListsFeature(),
                navOptions = NavOptions.Builder()
                    .setPopUpTo(AppDestinations.AppGraph1, inclusive = true)
                    .build()
            )

            2 -> appNavigator.navigate(
                AppDestinations.CategoriesFeature(),
                navOptions = NavOptions.Builder()
                    .setPopUpTo(AppDestinations.AppGraph1, inclusive = true)
                    .build()
            )

            3 -> appNavigator.navigate(
                AppDestinations.LetsPlayFeature(),
                navOptions = NavOptions.Builder()
                    .setPopUpTo(AppDestinations.AppGraph1, inclusive = true)
                    .build()
            )

            4 -> appNavigator.navigate(
                AppDestinations.ProfileFeature(),
                navOptions = NavOptions.Builder()
                    .setPopUpTo(AppDestinations.AppGraph1, inclusive = true)
                    .build()
            )
        }
    }

    val navController = rememberNavController()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    val selectedDestinationIndex by remember(currentBackStackEntry) {
        derivedStateOf {
            AflamiNavBarItem.destinations.indexOfFirst { item ->
                currentBackStackEntry?.destination?.hasRoute(item.destination::class) == true
            }.coerceAtLeast(0)
        }
    }

    val isVisible by remember {
        derivedStateOf {
            AflamiNavBarItem.destinations.any {
                currentBackStackEntry?.destination?.hasRoute(it.destination::class) == true
            }
        }
    }


    AflamiScafold(
        content = {
            AppNavGraph(navController = navController)
        },
        bottomBar = {
            AnimatedVisibility(
                visible = isVisible,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
            ) {
                AflamiNavBar(
                    selectedIndex = selectedDestinationIndex,
                    onItemClick = {
                        currentDestination = it
                    }
                )
            }
        }
    )
}