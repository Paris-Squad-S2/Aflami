package com.feature.lists.listsUi.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class ListNavigatorImpl(override val startGraph: ListGraph) : ListNavigator {
    private val _navigateEvent = Channel<ListNavigationEvent>()
    override val listNavigationEvent = _navigateEvent.receiveAsFlow()
    private val mutex = Mutex()
    private var lastNavigateTime = 0L

    override suspend fun navigate(destination: ListDestination, navOptions: NavOptions?) {
        mutex.withLock {
            val now = System.currentTimeMillis()
            if (now - lastNavigateTime >= 1000) {
                lastNavigateTime = now
                _navigateEvent.send(
                    ListNavigationEvent.Navigate(
                        destination = destination,
                        navOptions = navOptions
                    )
                )
            }
        }
    }
    override suspend fun navigateUp() { _navigateEvent.send(ListNavigationEvent.NavigateUp) }
}