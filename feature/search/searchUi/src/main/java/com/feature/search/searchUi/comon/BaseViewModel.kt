package com.feature.search.searchUi.comon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import com.feature.search.searchUi.navigation.SearchDestination
import com.feature.search.searchUi.navigation.SearchNavigator
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class BaseViewModel<S> @Inject constructor(
    initialState: S,  val navigator: SearchNavigator
) : ViewModel() {

    private val privateScreenState = MutableStateFlow(initialState)
    val screenState: StateFlow<S> = privateScreenState.asStateFlow()

    protected fun navigate(destination: SearchDestination, navOptions: NavOptions? = null) =
        viewModelScope.launch {
            navigator.navigate(destination = destination, navOptions = navOptions)
        }

    protected fun navigateUp() = viewModelScope.launch { navigator.navigateUp() }

    fun updateState(newState: S) {
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