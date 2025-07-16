package com.feature.mediaDetails.mediaDetailsUi.ui.navigation

import androidx.navigation.NavOptions
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsDestination
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsGraph
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class MediaDetailsNavigatorImpl(override val startGraph: MediaDetailsGraph) : MediaDetailsNavigator {
    private val _navigateEvent = Channel<MediaDetailsNavigationEvent>()
    override val mediaDetailsNavigationEvent = _navigateEvent.receiveAsFlow()
    private val mutex = Mutex()
    private var lastNavigateTime = 0L

    override suspend fun navigate(destination: MediaDetailsDestination, navOptions: NavOptions?) {
        mutex.withLock {
            val now = System.currentTimeMillis()
            if (now - lastNavigateTime >= 1000) {
                lastNavigateTime = now
                _navigateEvent.send(
                    MediaDetailsNavigationEvent.Navigate(destination = destination, navOptions = navOptions)
                )
            }
        }
    }
    override suspend fun navigateUp() { _navigateEvent.send(MediaDetailsNavigationEvent.NavigateUp) }
}