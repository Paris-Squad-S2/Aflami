package com.feature.authentication.authenticationUi.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class AuthenticationNavigatorImpl(override val startGraph: AuthenticationGraph) : AuthenticationNavigator {
    private val _navigateEvent = Channel<AuthenticationNavigationEvent>()
    override val authenticationNavigationEvent = _navigateEvent.receiveAsFlow()
    private val mutex = Mutex()
    private var lastNavigateTime = 0L

    override suspend fun navigate(destination: AuthenticationDestination, navOptions: NavOptions?) {
        mutex.withLock {
            val now = System.currentTimeMillis()
            if (now - lastNavigateTime >= 1000) {
                lastNavigateTime = now
                _navigateEvent.send(
                    AuthenticationNavigationEvent.Navigate(destination = destination, navOptions = navOptions)
                )
            }
        }
    }
    override suspend fun navigateUp() { _navigateEvent.send(AuthenticationNavigationEvent.NavigateUp) }
}