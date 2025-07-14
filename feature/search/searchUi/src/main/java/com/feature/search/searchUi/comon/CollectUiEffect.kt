package com.feature.search.searchUi.comon

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CollectUiEffect(
    effect: SharedFlow<BaseViewModel.BaseUiEffect>,
    effectHandler: (effect: BaseViewModel.BaseUiEffect) -> Unit
) {
    LaunchedEffect(Unit) {
        effect.collectLatest {
            effectHandler(it)
        }
    }
}