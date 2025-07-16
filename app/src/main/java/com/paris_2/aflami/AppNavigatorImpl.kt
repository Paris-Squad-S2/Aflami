package com.paris_2.aflami

import androidx.navigation.NavOptions
import com.paris_2.aflami.appnavigation.AppDestination
import com.paris_2.aflami.appnavigation.AppGraph
import com.paris_2.aflami.appnavigation.AppNavigationEvent
import com.paris_2.aflami.appnavigation.AppNavigator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class AppNavigatorImpl(override val startGraph: AppGraph) : AppNavigator {
    private val _navigateEvent = Channel<AppNavigationEvent>()
    override val navigationEvent = _navigateEvent.receiveAsFlow()
    private val mutex = Mutex()
    private var lastNavigateTime = 0L

    override suspend fun navigate(destination: AppDestination, navOptions: NavOptions?) {
        mutex.withLock {
            val now = System.currentTimeMillis()
            if (now - lastNavigateTime >= 500) {
                lastNavigateTime = now
                _navigateEvent.send(
                    AppNavigationEvent.Navigate(destination = destination, navOptions = navOptions)
                )
            }
        }
    }
    override suspend fun navigateUp() { _navigateEvent.send(AppNavigationEvent.NavigateUp) }
}