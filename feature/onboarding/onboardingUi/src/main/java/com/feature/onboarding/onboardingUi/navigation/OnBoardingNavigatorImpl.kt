package com.feature.onboarding.onboardingUi.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class OnBoardingNavigatorImpl(
    override val startGraph: OnBoardingGraph
) : OnBoardingNavigator {

    private val _navigationEvents = Channel<OnBoardingNavigationEvent>()
    override val onboardingNavigationEvent = _navigationEvents.receiveAsFlow()

    private val mutex = Mutex()
    private var lastNavigateTime = 0L

    override suspend fun navigate(destination: OnBoardingDestination, navOptions: NavOptions?) {
        mutex.withLock {
            val now = System.currentTimeMillis()
            if (now - lastNavigateTime >= 1000) {
                lastNavigateTime = now
                _navigationEvents.send(OnBoardingNavigationEvent.Navigate(destination, navOptions))
            }
        }
    }

    override suspend fun navigateUp() {
        _navigationEvents.send(OnBoardingNavigationEvent.NavigateUp)
    }
}
