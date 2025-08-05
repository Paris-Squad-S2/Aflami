package com.feature.profile.profileUi.navigation


import androidx.navigation.NavOptions
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class ProfileNavigatorImpl(override val startGraph: ProfileGraph): ProfileNavigator {
    private val _navigateEvent = Channel<ProfileNavigationEvent>()
    override val profileNavigationEvent = _navigateEvent.receiveAsFlow()
    private val mutex = Mutex()
    private var lastNavigateTime = 0L

    override suspend fun navigate(destination: ProfileDestination, navOptions: NavOptions?) {
        mutex.withLock {
            val now = System.currentTimeMillis()
            if (now - lastNavigateTime >= 1000) {
                lastNavigateTime = now
                _navigateEvent.send(
                    ProfileNavigationEvent.Navigate(
                        destination = destination,
                        navOptions = navOptions
                    )
                )
            }
        }
    }
    override suspend fun navigateUp() { _navigateEvent.send(ProfileNavigationEvent.NavigateUp) }
}