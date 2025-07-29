package com.feature.authentication.authenticationUi.comon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import com.feature.authentication.authenticationUi.navigation.AuthenticationDestination
import com.feature.authentication.authenticationUi.navigation.AuthenticationNavigator
import com.paris_2.domain.authentication.exception.InvalidCredentialsException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class BaseViewModel<S>(
    initialState: S, protected val navigator: AuthenticationNavigator,
) : ViewModel() {

    private val _screenState = MutableStateFlow(initialState)
    val screenState: StateFlow<S> = _screenState.asStateFlow()

    protected fun navigate(destination: AuthenticationDestination, navOptions: NavOptions? = null) =
        viewModelScope.launch {
            navigator.navigate(destination = destination, navOptions = navOptions)
        }

    protected fun navigateUp() = viewModelScope.launch { navigator.navigateUp() }

    fun updateState(newState: S) {
        _screenState.update { newState }
    }

    protected fun <T> tryToExecute(
        onSuccess: (suspend (T) -> Unit)? = null,
        onError: (String) -> Unit ,
        onInvalidCredentials: ((String) -> Unit)? = null,
        scope: CoroutineScope = viewModelScope,
        execute: suspend () -> T,
    ): Job {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            when (throwable) {
                is InvalidCredentialsException -> onInvalidCredentials?.invoke(throwable.message ?: "Invalid username or password")
                else -> onError(throwable.message ?: "Unexpected error")
            }
        }
        return scope.launch(exceptionHandler) {
            try {
                val result = execute()
                onSuccess?.invoke(result)
            } catch (e: Exception) {
                when (e) {
                    is InvalidCredentialsException -> onInvalidCredentials?.invoke(e.message ?: "Invalid username or password")
                    else -> onError(e.message ?: "Unexpected error")
                }
            }
        }
    }
}