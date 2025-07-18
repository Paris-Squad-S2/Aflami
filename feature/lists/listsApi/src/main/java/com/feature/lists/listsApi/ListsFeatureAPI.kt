package com.feature.lists.listsApi

import androidx.compose.runtime.Composable

interface ListsFeatureAPI {
    operator fun invoke(listsDestination: ListsDestination? = null) : @Composable () -> Unit
}