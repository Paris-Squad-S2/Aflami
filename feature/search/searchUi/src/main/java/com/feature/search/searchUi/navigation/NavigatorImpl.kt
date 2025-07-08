package com.feature.search.searchUi.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class NavigatorImpl(override val startGraph: Graph) : Navigator {
    private val _navigateEvent = Channel<NavigationEvent>()
    override val navigationEvent = _navigateEvent.receiveAsFlow()
    override suspend fun navigate(destination: Destination, navOptions: NavOptions?) {
        _navigateEvent.send(
            NavigationEvent.Navigate(destination = destination, navOptions = navOptions)
        )
    }
    override suspend fun navigateUp() { _navigateEvent.send(NavigationEvent.NavigateUp) }
}