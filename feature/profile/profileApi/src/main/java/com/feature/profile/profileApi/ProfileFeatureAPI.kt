package com.feature.profile.profileApi

import androidx.compose.runtime.Composable

interface ProfileFeatureAPI {
    operator fun invoke(profileDestination: ProfileDestination? = null) : @Composable () -> Unit
}