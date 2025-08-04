package com.feature.lists.listsUi.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import com.feature.lists.listsUi.navigation.ListDestination
import com.feature.lists.listsUi.navigation.ListNavigator
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class BaseViewModel<S>(
    initialState: S , private val navigator: ListNavigator
) : ViewModel() {

    private val privateScreenState = MutableStateFlow(initialState)
    val screenState: StateFlow<S> = privateScreenState.asStateFlow()

    protected fun navigate(destination: ListDestination, navOptions: NavOptions? = null) =
        viewModelScope.launch {
            navigator.navigate(destination = destination, navOptions = navOptions)
        }

    protected fun navigateUp() = viewModelScope.launch { navigator.navigateUp() }

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
}