package com.feature.search.searchUi.comon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

open class BaseViewModel<S, UiEffect>(initialState: S) : ViewModel(), KoinComponent {
    interface BaseUiEffect

    private val privateScreenState = MutableStateFlow(initialState)
    val screenState: StateFlow<S> = privateScreenState.asStateFlow()

    private val privateUiEffect = MutableSharedFlow<UiEffect>()
    val uiEffect = privateUiEffect.asSharedFlow()

    fun emitState(newState: S) {
        privateScreenState.update { newState }
    }

    protected fun <T> tryToExecute(
        onSuccess: (suspend (T) -> Unit)? = null,
        onError: (String) -> Unit,
        scope: CoroutineScope = viewModelScope,
        execute: suspend () -> T,
    ): Job {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            onError(throwable.message ?: "Unexpected error")
        }
        return scope.launch(exceptionHandler) {
            try {
                val result = execute()
                onSuccess?.invoke(result)
            } catch (e: Exception) {
                onError(e.message ?: "Unexpected error")
            }
        }
    }

    protected fun sendUiEffect(effect: UiEffect){
        viewModelScope.launch {
            privateUiEffect.emit(effect)
        }
    }
}