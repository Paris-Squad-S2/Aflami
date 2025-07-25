package com.feature.home.homeUi.navigation

import androidx.navigation.NavOptions
import com.feature.home.homeApi.HomeDestination
import com.feature.home.homeApi.HomeGraph
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class HomeNavigatorImpl(override val startGraph: HomeGraph) : HomeNavigator {
    private val _navigateEvent = Channel<HomeNavigationEvent>()
    override val homeNavigationEvent = _navigateEvent.receiveAsFlow()
    private val mutex = Mutex()
    private var lastNavigateTime = 0L

    override suspend fun navigate(destination: HomeDestination, navOptions: NavOptions?) {
        mutex.withLock {
            val now = System.currentTimeMillis()
            if (now - lastNavigateTime >= 1000) {
                lastNavigateTime = now
                _navigateEvent.send(
                    HomeNavigationEvent.Navigate(
                        destination = destination,
                        navOptions = navOptions
                    )
                )
            }
        }
    }
    override suspend fun navigateUp() { _navigateEvent.send(HomeNavigationEvent.NavigateUp) }
}