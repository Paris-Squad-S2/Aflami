package com.paris_2.aflami

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import com.paris_2.aflami.appnavigation.AppNavigator
import com.paris_2.aflami.designsystem.components.AflamiScafold
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun AppScaffold(appNavigator: AppNavigator = koinInject()) {

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
                    selectedItem = AflamiNavBarItem.destinations[selectedDestinationIndex],
                    onItemClick = { destination ->
                        scope.launch {
                            appNavigator.navigate(
                                destination,
                                navOptions = NavOptions.Builder()
                                    .setPopUpTo(
                                        appNavigator.startGraph,
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