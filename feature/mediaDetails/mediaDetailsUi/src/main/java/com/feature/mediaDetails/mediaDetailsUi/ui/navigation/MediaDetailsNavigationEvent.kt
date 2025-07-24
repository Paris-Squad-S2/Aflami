package com.feature.mediaDetails.mediaDetailsUi.ui.navigation

import androidx.navigation.NavOptions

sealed class MediaDetailsNavigationEvent {
    data class Navigate(val destination: MediaDetailsDestination, val navOptions: NavOptions? = null): MediaDetailsNavigationEvent()
    data object NavigateUp: MediaDetailsNavigationEvent()
}