package com.feature.search.searchApi

import androidx.compose.runtime.Composable

interface SearchFeatureAPI {
    operator fun invoke(searchDestination: SearchDestination? = null) : @Composable () -> Unit
}