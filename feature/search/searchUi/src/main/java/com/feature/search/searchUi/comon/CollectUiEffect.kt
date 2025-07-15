package com.feature.search.searchUi.comon

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun CollectUiEffect(
    effect: SharedFlow<BaseViewModel.BaseUiEffect>,
    effectHandler: (effect: BaseViewModel.BaseUiEffect) -> Unit
) {
    val throttle = effect.throttleFirst(1000)
    LaunchedEffect(Unit) {
        throttle.collectLatest {
            effectHandler(it)
        }
    }
}

private fun <T> Flow<T>.throttleFirst(periodMillis: Long): Flow<T> {
    if (periodMillis < 0) return this
    return flow {
        conflate().collect { value ->
            emit(value)
            delay(periodMillis)
        }
    }
}