package com.paris_2.aflami.bottomNavBar.bottomNavBarUI.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.feature.categories.categoriesApi.CategoriesFeatureAPI
import com.feature.guessGame.guessGameApi.GuessGameFeatureAPI
import com.feature.home.homeApi.HomeFeatureAPI
import com.feature.lists.listsApi.ListsFeatureAPI
import com.feature.profile.profileApi.ProfileFeatureAPI
import com.paris_2.aflami.bottomNavBar.bottomNavBarUI.navigation.Navigator
import com.paris_2.aflami.designsystem.components.AppScaffold
import kotlinx.coroutines.launch

@Composable
internal fun BottomNavBarScaffold(
    navigator: Navigator,
    homeFeature: HomeFeatureAPI,
    listsFeature: ListsFeatureAPI,
    categoriesFeature: CategoriesFeatureAPI,
    letsPlayFeature: GuessGameFeatureAPI,
    profileFeature: ProfileFeatureAPI
) {
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

    val scope = rememberCoroutineScope()

    AppScaffold(
        content = {
            BottomNavBarNavGraph(
                navigator = navigator,
                navController = navController,
                homeFeature = homeFeature,
                listsFeature = listsFeature,
                categoriesFeature = categoriesFeature,
                letsPlayFeature = letsPlayFeature,
                profileFeature = profileFeature,
            )
        },
        bottomBar = {
            AnimatedVisibility(
                visible = isVisible,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),
            ) {
                BottomNavBar(
                    selectedItem = AflamiNavBarItem.destinations[selectedDestinationIndex],
                    onItemClick = { destination ->
                        scope.launch {
                            navigator.navigate(
                                destination,
                                navOptions = NavOptions.Builder()
                                    .setPopUpTo(
                                        navigator.startGraph,
                                        inclusive = false,
                                    )
                                    .build()
                            )
                        }
                    }
                )
            }
        }
    )
}