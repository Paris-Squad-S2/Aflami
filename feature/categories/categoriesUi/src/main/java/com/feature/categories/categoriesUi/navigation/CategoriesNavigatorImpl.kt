package com.feature.categories.categoriesUi.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class CategoriesNavigatorImpl(override val startGraph: CategoriesGraph) : CategoriesNavigator  {
    private val _navigateEvent = Channel<CategoriesNavigationEvent>()
    override val categoriesNavigationEvent = _navigateEvent.receiveAsFlow()
    private val mutex = Mutex()
    private var lastNavigateTime = 0L

    override suspend fun navigate(destination: CategoriesDestination, navOptions: NavOptions?) {
        mutex.withLock {
            val now = System.currentTimeMillis()
            if (now - lastNavigateTime >= 1000) {
                lastNavigateTime = now
                _navigateEvent.send(
                    CategoriesNavigationEvent.Navigate(
                        destination = destination,
                        navOptions = navOptions
                    )
                )
            }
        }
    }
    override suspend fun navigateUp() { _navigateEvent.send(CategoriesNavigationEvent.NavigateUp) }
}