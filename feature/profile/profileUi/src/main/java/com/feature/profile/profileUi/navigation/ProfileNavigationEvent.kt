package com.feature.profile.profileUi.navigation

import androidx.navigation.NavOptions

sealed class ProfileNavigationEvent {
    data class Navigate(
        val destination: ProfileDestination,
        val navOptions: NavOptions? = null
    ) : ProfileNavigationEvent()

    data object NavigateUp : ProfileNavigationEvent()
}