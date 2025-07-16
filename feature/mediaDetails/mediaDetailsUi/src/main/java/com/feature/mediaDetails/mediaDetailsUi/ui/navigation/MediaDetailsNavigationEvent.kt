package com.feature.mediaDetails.mediaDetailsUi.ui.navigation

import androidx.navigation.NavOptions
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsDestination

sealed class MediaDetailsNavigationEvent {
    data class Navigate(val destination: MediaDetailsDestination, val navOptions: NavOptions? = null): MediaDetailsNavigationEvent()
    data object NavigateUp: MediaDetailsNavigationEvent()
}