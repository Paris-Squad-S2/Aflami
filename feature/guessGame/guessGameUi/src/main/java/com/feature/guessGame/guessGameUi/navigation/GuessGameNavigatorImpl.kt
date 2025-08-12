package com.feature.guessGame.guessGameUi.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class GuessGameNavigatorImpl(override val startGraph: GuessGameGraph) : GuessGameNavigator {
    private val _navigateEvent = Channel<GuessGameNavigationEvent>()
    override val guessGameNavigationEvent = _navigateEvent.receiveAsFlow()
    private val mutex = Mutex()
    private var lastNavigateTime = 0L

    override suspend fun navigate(destination: GuessGameDestination, navOptions: NavOptions?) {
        mutex.withLock {
            val now = System.currentTimeMillis()
            if (now - lastNavigateTime >= 1000) {
                lastNavigateTime = now
                _navigateEvent.send(
                    GuessGameNavigationEvent.Navigate(
                        destination = destination,
                        navOptions = navOptions
                    )
                )
            }
        }
    }

    override suspend fun navigateUp() {
        _navigateEvent.send(GuessGameNavigationEvent.NavigateUp)
    }
}