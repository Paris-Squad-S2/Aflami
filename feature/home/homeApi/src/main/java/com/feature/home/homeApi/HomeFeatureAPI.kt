package com.feature.home.homeApi

import androidx.compose.runtime.Composable

interface HomeFeatureAPI {
    operator fun invoke() : @Composable () -> Unit
}