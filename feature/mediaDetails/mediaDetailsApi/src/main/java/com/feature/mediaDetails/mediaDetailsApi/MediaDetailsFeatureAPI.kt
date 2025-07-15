package com.feature.mediaDetails.mediaDetailsApi

import androidx.compose.runtime.Composable

interface MediaDetailsFeatureAPI {
    operator fun invoke(mediaDetailsDestination: MediaDetailsDestination? = null) : @Composable () -> Unit
}